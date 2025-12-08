package net.pufferlab.primal.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
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

public class BlockLargeVessel extends BlockContainer {

    public IIcon[] icons = new IIcon[2];

    public BlockLargeVessel() {
        super(Material.rock);
        this.setHardness(1.0F);
        this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.8125F, 0.8125F);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        player.openGui(Primal.instance, Primal.proxy.largeVesselContainerID, worldIn, x, y, z);
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":large_vessel");
        icons[1] = reg.registerIcon(Primal.MODID + ":ceramic");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 99) {
            return icons[0];
        }
        return icons[1];
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
        NBTTagCompound tagCompound = new NBTTagCompound();
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityLargeVessel tef) {
            tef.writeToNBTInventory(tagCompound);
            item.setTagCompound(tagCompound);
        }
        dropItemStack(worldIn, x, y, z, item);
    }

    @Override
    protected void dropBlockAsItem(World worldIn, int x, int y, int z, ItemStack itemIn) {}

    public void dropItemStack(World world, int x, int y, int z, ItemStack item) {
        if (item != null && item.stackSize > 0) {
            EntityItem entityItem = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, item.copy());
            entityItem.motionX = 0.0D;
            entityItem.motionY = 0.0D;
            entityItem.motionZ = 0.0D;
            spawnEntity(world, entityItem);
            item.stackSize = 0;
        }
    }

    public void spawnEntity(World world, Entity entityItem) {
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity) entityItem);
        }
    }

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
