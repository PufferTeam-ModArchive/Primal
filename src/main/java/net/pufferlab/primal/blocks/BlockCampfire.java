package net.pufferlab.primal.blocks;

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
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.TileEntityCampfire;

public class BlockCampfire extends BlockContainer {

    public IIcon[] icons = new IIcon[4];

    public BlockCampfire() {
        super(Material.wood);
        this.setHardness(0.4F);
        this.setTickRandomly(true);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":campfire");
        icons[1] = reg.registerIcon(Primal.MODID + ":empty");
        icons[2] = reg.registerIcon(Primal.MODID + ":campfire_lit");
        icons[3] = reg.registerIcon(Primal.MODID + ":campfire_fire");
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        ItemStack heldItem = player.getHeldItem();
        int meta = worldIn.getBlockMetadata(x, y, z);
        if (heldItem != null) {
            if ((Utils.containsOreDict(heldItem, "firewood") && meta > 0 && meta < 5)
                || (Utils.containsOreDict(heldItem, "kindling") && meta == 0)) {
                worldIn.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
                worldIn.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
                worldIn.markBlockForUpdate(x, y, z);
                TileEntity te = worldIn.getTileEntity(x, y, z);
                if (meta == 0) {
                    Utils.playSound(worldIn, x, y, z, Registry.thatch);
                } else {
                    Utils.playSound(worldIn, x, y, z, Registry.log_pile);
                }
                if (te instanceof TileEntityCampfire tef) {
                    tef.addInventorySlotContentsUpdate(meta + 1, player);
                    if (meta == 4) {
                        tef.isBuilt = true;
                        worldIn.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
                        worldIn.markBlockForUpdate(x, y, z);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCampfire tef) {
            if (tef.isFired) {
                return 15;
            }
        }
        return 0;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);

        Block block = worldIn.getBlock(x, y, z);
        if (!worldIn.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
            worldIn.setBlockToAir(x, y, z);
            block.onBlockPreDestroy(worldIn, x, y, z, worldIn.getBlockMetadata(x, y, z));
        }
    }

    @Override
    public void randomDisplayTick(World worldIn, int x, int y, int z, Random random) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityCampfire tef) {
            if (tef.isFired) {
                if (random.nextInt(24) == 0) {
                    worldIn.playSound(
                        (double) ((float) x + 0.5F),
                        (double) ((float) y + 0.5F),
                        (double) ((float) z + 0.5F),
                        "fire.fire",
                        1.0F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.3F,
                        false);
                }
                int l;
                float f;
                float f1;
                float f2;

                for (l = 0; l < 3; ++l) {
                    f = (float) x + random.nextFloat();
                    f1 = (float) y + random.nextFloat() * 0.5F + 0.5F;
                    f2 = (float) z + random.nextFloat();
                    worldIn.spawnParticle("largesmoke", (double) f, (double) f1, (double) f2, 0.0D, 0.0D, 0.0D);
                }
            }
        }

    }

    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityCampfire tef) {
            if (tef.isFired) {
                if (side == 98) {
                    return icons[3];
                }
                if (side == 99) {
                    return icons[2];
                }
            }
        }
        return super.getIcon(worldIn, x, y, z, side);
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);
        dropItems(worldIn, x, y, z, 0);
    }

    private void dropItems(World world, int i, int j, int k, int start) {
        Random rando = world.rand;
        TileEntity tileEntity = world.getTileEntity(i, j, k);
        if (!(tileEntity instanceof IInventory)) return;
        IInventory inventory = (IInventory) tileEntity;
        for (int x = start; x < inventory.getSizeInventory(); x++) {
            ItemStack item = inventory.getStackInSlot(x);
            inventory.setInventorySlotContents(x, null);
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
    public IIcon getIcon(int side, int meta) {
        if (side == 99) {
            return icons[0];
        }
        return icons[1];
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCampfire();
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getCampfireRenderID();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return false;
    }
}
