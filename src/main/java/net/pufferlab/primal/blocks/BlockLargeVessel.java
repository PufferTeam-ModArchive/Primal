package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityLargeVessel;
import net.pufferlab.primal.utils.FluidUtils;

public class BlockLargeVessel extends BlockContainerPrimal {

    public IIcon largeVessel;
    public IIcon particle;

    public static final int iconLargeVessel = 99;

    public BlockLargeVessel() {
        super(Material.rock);
        this.setHardness(1.0F);
        this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.8125F, 0.8125F);
        this.canBlockGrass = false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityLargeVessel tef) {
            if (player.isSneaking()) {
                tef.setOpen(!tef.isOpen);
            } else {
                if (!FluidUtils.isFluidContainer(player.getHeldItem())) {
                    Primal.proxy.openLargeVesselGui(player, worldIn, x, y, z);
                }
            }
        }
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        largeVessel = reg.registerIcon(Primal.MODID + ":large_vessel");
        particle = reg.registerIcon(Primal.MODID + ":ceramic");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconLargeVessel) {
            return largeVessel;
        }
        return particle;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".large_vessel";
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        ItemStack heldItem = placer.getHeldItem();
        NBTTagCompound tagCompound = heldItem.getTagCompound();
        if (tagCompound != null) {
            TileEntity te = worldIn.getTileEntity(x, y, z);
            if (te instanceof TileEntityLargeVessel tef) {
                tef.readFromNBTInventory(tagCompound);
            }
        }
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        ItemStack item = new ItemStack(this, 1, 0);
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityLargeVessel tef) {
            if (!tef.isOpen) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tef.writeToNBTInventory(tagCompound);
                item.setTagCompound(tagCompound);
            } else {
                dropItems(worldIn, x, y, z);
            }
        }
        dropItemStack(worldIn, x, y, z, item);
    }

    @Override
    protected void dropBlockAsItem(World worldIn, int x, int y, int z, ItemStack itemIn) {}

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityLargeVessel();
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getLargeVesselRenderID();
    }

    @Override
    public boolean isOpaqueCube() {
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
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }
}
