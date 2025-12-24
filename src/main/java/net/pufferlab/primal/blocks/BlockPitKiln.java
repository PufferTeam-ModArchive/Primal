package net.pufferlab.primal.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.PitKilnRecipe;
import net.pufferlab.primal.tileentities.TileEntityInventory;
import net.pufferlab.primal.tileentities.TileEntityPitKiln;

public class BlockPitKiln extends BlockContainer {

    public IIcon[] icons = new IIcon[7];

    public BlockPitKiln() {
        super(Material.clay);

        this.setHardness(0.2F);
        super.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        if (meta != 0) {
            if (meta >= 6) {
                super.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
                super.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F * meta, 1.0F);
            }
        } else {
            super.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.05F, 1.0F);
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
            if (Utils.hasSolidWalls(worldIn, x, y, z)) {
                if ((Utils.containsOreDict(heldItem, "straw") && meta >= 0 && meta <= 4)
                    || (Utils.containsOreDict(heldItem, Constants.logPileOreDicts) && meta >= 5 && meta <= 7)) {
                    if (meta <= 4) {
                        Utils.playSound(worldIn, x, y, z, Registry.thatch);
                    } else {
                        Utils.playSound(worldIn, x, y, z, Registry.log_pile);
                    }
                    tef.addInventorySlotContentsUpdate(5 + meta, player);
                    worldIn.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
                    return true;
                }
            }

            if (meta == 0) {
                boolean top = false;
                boolean left = false;
                if (subZ < 0.5F) {
                    top = true;
                }
                if (subX > 0.5F) {
                    left = true;
                }

                if (Utils.containsOreDict(heldItem, "itemLarge") || (tef.getInventoryStack(4) != null)) {
                    if (tef.getInventoryStack(0) == null && tef.getInventoryStack(1) == null
                        && tef.getInventoryStack(2) == null
                        && tef.getInventoryStack(3) == null) {
                        return addOrRemoveItem(worldIn, x, y, z, player, tef, 4, heldItem);
                    }
                } else if (tef.getInventoryStack(4) == null) {
                    if (!top && left) {
                        return addOrRemoveItem(worldIn, x, y, z, player, tef, 0, heldItem);
                    }
                    if (!top && !left) {
                        return addOrRemoveItem(worldIn, x, y, z, player, tef, 1, heldItem);
                    }
                    if (top && left) {
                        return addOrRemoveItem(worldIn, x, y, z, player, tef, 2, heldItem);
                    }
                    if (top && !left) {
                        return addOrRemoveItem(worldIn, x, y, z, player, tef, 3, heldItem);
                    }
                }

            }

        }
        return false;
    }

    public boolean addOrRemoveItem(World world, int x, int y, int z, EntityPlayer player, TileEntityInventory tef,
        int index, ItemStack heldItem) {
        if (tef.getInventoryStack(index) == null) {
            if (!PitKilnRecipe.hasRecipe(heldItem)) return false;
            return tef.addInventorySlotContentsUpdate(index, player);
        } else {
            dropItem(world, x, y, z, index);
            tef.setInventorySlotContentsUpdate(index);
            if (tef.getInventoryStack(0) == null && tef.getInventoryStack(1) == null
                && tef.getInventoryStack(2) == null
                && tef.getInventoryStack(3) == null) {
                world.setBlockToAir(x, y, z);
            }
            return true;
        }
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);
        boolean hasWalls = Utils.hasSolidWalls(worldIn, x, y, z);
        if (!hasWalls) {
            clearLayers(worldIn, x, y, z);
        }
        Block block = worldIn.getBlock(x, y, z);
        if (!worldIn.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
            worldIn.setBlockToAir(x, y, z);
            block.onBlockPreDestroy(worldIn, x, y, z, worldIn.getBlockMetadata(x, y, z));
        }
    }

    public void clearLayers(World worldIn, int x, int y, int z) {
        worldIn.setBlockMetadataWithNotify(x, y, z, 0, 2);
        dropItems(worldIn, x, y, z, 5);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":pit_kiln");
        icons[1] = reg.registerIcon(Primal.MODID + ":pit_kiln_top_1");
        icons[2] = reg.registerIcon(Primal.MODID + ":pit_kiln_top_2");
        icons[3] = reg.registerIcon(Primal.MODID + ":log_pile");
        icons[4] = reg.registerIcon(Primal.MODID + ":thatch_grass");
        icons[5] = reg.registerIcon(Primal.MODID + ":pit_kiln_2");
        icons[6] = reg.registerIcon(Primal.MODID + ":empty");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta == 0) {
            return icons[0];
        }
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

        dropItems(worldIn, x, y, z, 0);
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 8) {
            return true;
        }
        return false;
    }

    private boolean dropItem(World world, int x, int y, int z, int index) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) return false;
        TileEntityInventory pile = (TileEntityInventory) tileEntity;
        ItemStack item = null;
        if ((index < pile.getSizeInventory()) && (index >= 0)) {
            item = pile.getInventoryStack(index);
        }
        if (item != null && item.stackSize > 0) {
            EntityItem entityItem = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, item.copy());
            entityItem.motionX = 0.0D;
            entityItem.motionY = 0.0D;
            entityItem.motionZ = 0.0D;
            spawnEntity(world, entityItem);
            item.stackSize = 0;
            return true;
        }
        return false;
    }

    private void dropItems(World world, int i, int j, int k, int start) {
        Random rando = world.rand;
        TileEntity tileEntity = world.getTileEntity(i, j, k);
        if (!(tileEntity instanceof TileEntityInventory)) return;
        TileEntityInventory inventory = (TileEntityInventory) tileEntity;
        for (int x = start; x < inventory.getSizeInventory(); x++) {
            ItemStack item = inventory.getStackInSlot(x);
            inventory.setInventorySlotContentsUpdate(x, null);
            if (item != null && item.stackSize > 0) {
                float ri = rando.nextFloat() * 0.8F + 0.1F;
                float rj = rando.nextFloat() * 0.8F + 0.1F;
                float rk = rando.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, (i + ri), (j + rj), (k + rk), item.copy());
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
    protected void dropBlockAsItem(World worldIn, int x, int y, int z, ItemStack itemIn) {}

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".pit_kiln";
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
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getPitKilnRenderID();
    }
}
