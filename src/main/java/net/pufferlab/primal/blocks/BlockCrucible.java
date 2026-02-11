package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.items.itemblocks.ItemBlockCrucible;
import net.pufferlab.primal.tileentities.TileEntityCrucible;
import net.pufferlab.primal.utils.FluidUtils;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.world.GlobalTickingData;

import com.falsepattern.rple.api.common.block.RPLECustomBlockBrightness;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "com.falsepattern.rple.api.common.block.RPLECustomBlockBrightness", modid = "rple")
public class BlockCrucible extends BlockContainerPrimal implements RPLECustomBlockBrightness {

    public IIcon[] icons = new IIcon[2];

    public static final int iconCrucible = 99;

    public BlockCrucible() {
        super(Material.rock);
        this.setHardness(1.0F);
        this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
        this.canBlockGrass = false;
    }

    @Override
    public short rple$getCustomBrightnessColor() {
        return Constants.lightNone;
    }

    @Override
    public short rple$getCustomBrightnessColor(int blockMeta) {
        return Constants.lightNone;
    }

    @Override
    public short rple$getCustomBrightnessColor(IBlockAccess world, int blockMeta, int posX, int posY, int posZ) {
        TileEntity te = world.getTileEntity(posX, posY, posZ);
        if (te instanceof TileEntityCrucible tef) {
            return HeatUtils.getHeatingColor(tef.getTemperature());
        }
        return Constants.lightNone;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        if (!FluidUtils.isFluidContainer(player.getHeldItem())) {
            Primal.proxy.openCrucibleGui(player, worldIn, x, y, z);
        }
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":crucible");
        icons[1] = reg.registerIcon(Primal.MODID + ":ceramic");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconCrucible) {
            return icons[0];
        }
        return icons[1];
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".crucible";
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        ItemStack heldItem = placer.getHeldItem();
        NBTTagCompound tagCompound = heldItem.getTagCompound();
        if (tagCompound != null) {
            TileEntity te = worldIn.getTileEntity(x, y, z);
            if (te instanceof TileEntityCrucible tef) {
                tef.readFromNBTInventory(tagCompound);
                tef.temperature = HeatUtils
                    .getInterpolatedTemperature(GlobalTickingData.getTickTime(worldIn), tagCompound);
                tef.scheduleInventoryUpdate();
            }
        }
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        ItemStack item = new ItemStack(this, 1, 0);
        NBTTagCompound tagCompound = new NBTTagCompound();
        item.setTagCompound(tagCompound);
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityCrucible tef) {
            tef.updateHeatInventory(-1.0F, tef.maxTemperature);
            tef.writeToNBTInventory(tagCompound);
            if (HeatUtils.hasImpl(item)) {
                IHeatableItem item2 = HeatUtils.getImpl(item);
                item2.updateHeat(item, worldIn, -1.0F, tef.temperature, tef.maxTemperature);
            }
        }
        dropItemStack(worldIn, x, y, z, item);
    }

    @Override
    protected void dropBlockAsItem(World worldIn, int x, int y, int z, ItemStack itemIn) {}

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCrucible();
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getCrucibleRenderID();
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

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockCrucible.class;
    }
}
