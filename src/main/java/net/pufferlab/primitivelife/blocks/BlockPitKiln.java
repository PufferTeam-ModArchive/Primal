package net.pufferlab.primitivelife.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.Utils;
import net.pufferlab.primitivelife.tileentities.TileEntityPitKiln;

public class BlockPitKiln extends BlockContainer {

    public IIcon[] icons = new IIcon[6];

    public BlockPitKiln() {
        super(Material.clay);

        this.setHardness(0.2F);
        super.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        if (meta >= 6) {
            super.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            super.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F * meta, 1.0F);
        }
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask,
        List<AxisAlignedBB> list, Entity collider) {
        this.setBlockBoundsBasedOnState(worldIn, x, y, z);
        super.addCollisionBoxesToList(worldIn, x, y, z, mask, list, collider);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        int meta = worldIn.getBlockMetadata(x, y, z);
        if (te instanceof TileEntityPitKiln tef) {
            ItemStack heldItem = player.getHeldItem();
            if ((Utils.containsOreDict(heldItem, "straw") && meta >= 0 && meta <= 4)
                || (Utils.containsOreDict(heldItem, "logWood") && meta >= 5 && meta <= 7)) {
                player.getCurrentEquippedItem().stackSize--;
                if (meta <= 4) {
                    playSound(player, x, y, z, Blocks.grass);
                } else {
                    playSound(player, x, y, z, Blocks.log);
                }
                worldIn.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
                return true;
            }
            boolean top = false;
            boolean left = false;
            if (subZ < 0.5F) {
                top = true;
            }
            if (subX > 0.5F) {
                left = true;
            }

            if (!top && left) {
                return tef.addInventorySlotContentsUpdate(0, player);
            }
            if (!top && !left) {
                return tef.addInventorySlotContentsUpdate(1, player);
            }
            if (top && left) {
                return tef.addInventorySlotContentsUpdate(2, player);
            }
            if (top && !left) {
                return tef.addInventorySlotContentsUpdate(3, player);
            }
        }
        return false;
    }

    public void playSound(EntityPlayer player, int x, int y, int z, Block toPlace) {
        player.worldObj.playSoundEffect(
            x + 0.5f,
            y + 0.5f,
            z + 0.5f,
            toPlace.stepSound.func_150496_b(),
            (toPlace.stepSound.getVolume() + 1.0F) / 2.0F,
            toPlace.stepSound.getPitch() * 0.8F);
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);
        boolean hasWalls = Utils.hasSolidWalls(worldIn, x, y, z);
        if (!hasWalls) {
            worldIn.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(PrimitiveLife.MODID + ":pit_kiln");
        icons[1] = reg.registerIcon(PrimitiveLife.MODID + ":pit_kiln_top_1");
        icons[2] = reg.registerIcon(PrimitiveLife.MODID + ":pit_kiln_top_2");
        icons[3] = reg.registerIcon(PrimitiveLife.MODID + ":pit_kiln_top_3");
        icons[4] = reg.registerIcon(PrimitiveLife.MODID + ":thatch_grass");
        icons[5] = reg.registerIcon(PrimitiveLife.MODID + ":pit_kiln_2");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 1) {
            if (meta == 6) {
                return icons[1];
            } else if (meta == 7) {
                return icons[2];
            } else if (meta == 8) {
                return icons[3];
            } else {
                return icons[4];
            }
        }
        if (side == 0) {
            return icons[4];
        }
        if (side == 4 || side == 5) {
            return icons[5];
        }
        return icons[0];
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        dropItems(worldIn, x, y, z);
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 8) {
            return true;
        }
        return false;
    }

    private void dropItems(World world, int i, int j, int k) {
        Random rando = world.rand;
        TileEntity tileEntity = world.getTileEntity(i, j, k);
        if (!(tileEntity instanceof IInventory)) return;
        IInventory inventory = (IInventory) tileEntity;
        for (int x = 0; x < inventory.getSizeInventory(); x++) {
            ItemStack item = inventory.getStackInSlot(x);
            if (item != null && item.stackSize > 0) {
                float ri = rando.nextFloat() * 0.8F + 0.1F;
                float rj = rando.nextFloat() * 0.8F + 0.1F;
                float rk = rando.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(
                    world,
                    (i + ri),
                    (j + rj),
                    (k + rk),
                    new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
                if (item.hasTagCompound()) entityItem.getEntityItem()
                    .setTagCompound(
                        (NBTTagCompound) item.getTagCompound()
                            .copy());
                float factor = 0.05F;
                entityItem.motionX = rando.nextGaussian() * factor;
                entityItem.motionY = rando.nextGaussian() * factor + 0.20000000298023224D;
                entityItem.motionZ = rando.nextGaussian() * factor;
                spawnEntity(world, entityItem);
                item.stackSize = 0;
            }
        }
    }

    public void spawnEntity(World world, Entity entityItem) {
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity) entityItem);
        }
    }

    @Override
    public Item getItem(World worldIn, int x, int y, int z) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        Item item = null;
        if (te instanceof TileEntityPitKiln tef) {
            item = tef.getLastItem();
        }
        return item;
    }

    @Override
    public int getDamageValue(World worldIn, int x, int y, int z) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        int itemMeta = 0;
        if (te instanceof TileEntityPitKiln tef) {
            itemMeta = tef.getLastItemMeta();
        }
        return itemMeta;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + PrimitiveLife.MODID + ".pit_kiln";
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPitKiln();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return PrimitiveLife.proxy.getPitKilnRenderID();
    }
}
