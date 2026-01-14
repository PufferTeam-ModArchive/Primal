package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.TileEntityCast;
import net.pufferlab.primal.tileentities.TileEntityInventory;
import net.pufferlab.primal.utils.FluidUtils;

public class BlockCast extends BlockPrimal {

    public IIcon[] icons = new IIcon[2];

    public static final int iconCast = 99;

    public BlockCast() {
        super(Material.clay);

        this.setHardness(0.2F);
        super.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.125F, 0.875F);
        this.canBlockGrass = false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        ItemStack heldItem = player.getHeldItem();
        if (te instanceof TileEntityCast tef) {
            if (heldItem != null) {
                if (heldItem.getItem() == Item.getItemFromBlock(Registry.crucible)) {
                    FluidStack fluid = FluidUtils.drainFluidFromNBT(heldItem.getTagCompound(), 144);
                    if (tef.getFluidStack() == null && fluid != null) {
                        tef.fill(ForgeDirection.getOrientation(side), fluid, true);
                    }
                    return true;
                }
            }
            if (FluidUtils.isFluidContainer(heldItem)) {
                return true;
            } else {
                return addOrRemoveItem(worldIn, x, y, z, player, tef, 0, heldItem);
            }
        }
        return false;
    }

    public boolean addOrRemoveItem(World world, int x, int y, int z, EntityPlayer player, TileEntityInventory tef,
        int index, ItemStack heldItem) {
        if (tef.getInventoryStack(index) == null) {
            if (!Utils.containsOreDict(heldItem, "mold")) return false;
            return tef.addInventorySlotContentsUpdate(index, player);
        } else {
            dropItem(world, x, y, z, index);
            tef.setInventorySlotContentsUpdate(index);
            if (tef.getInventoryStack(index) == null) {
                world.setBlockToAir(x, y, z);
            }
            return true;
        }
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
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":ceramic");
        icons[1] = reg.registerIcon(Primal.MODID + ":mold");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconCast) {
            return icons[1];
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
    public Item getItem(World worldIn, int x, int y, int z) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityCast tef) {
            return tef.getLastItem();
        }
        return super.getItem(worldIn, x, y, z);
    }

    @Override
    public int getDamageValue(World worldIn, int x, int y, int z) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityCast tef) {
            return tef.getLastItemMeta();
        }
        return super.getDamageValue(worldIn, x, y, z);
    }

    @Override
    protected void dropBlockAsItem(World worldIn, int x, int y, int z, ItemStack itemIn) {}

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCast();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        return true;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
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
        return Primal.proxy.getCastRenderID();
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return null;
    }
}
