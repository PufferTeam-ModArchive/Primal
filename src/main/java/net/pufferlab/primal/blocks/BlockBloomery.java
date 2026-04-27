package net.pufferlab.primal.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
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
import net.pufferlab.primal.client.models.ModelBloomery;
import net.pufferlab.primal.tileentities.TileEntityBloomery;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockBloomery extends BlockContainerPrimal {

    private IIcon[] heatingIcons = new IIcon[8];
    public IIcon bloomery;
    public IIcon particle;

    public static final int iconBloomery = 99;

    public BlockBloomery() {
        super(Material.rock);
        this.setStepSound(soundTypeStone);
        this.setHardness(0.8F);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        super.setBlockBoundsBasedOnState(worldIn, x, y, z);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1 - 0.1875F, 1.0F);
    }

    ModelBloomery modelBloomery = new ModelBloomery();

    @Override
    public List<AxisAlignedBB> getBounds(World world, int x, int y, int z, EntityPlayer player, BoundsType type) {
        List<AxisAlignedBB> bounds = new ArrayList<>();
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityBloomery tef) {
            modelBloomery.setFacing(tef.facingMeta);
            bounds.addAll(modelBloomery.getBounds());
        }
        return bounds;
    }

    @Override
    public int getMaxMeta() {
        return 5;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        ItemStack heldItem = player.getHeldItem();
        if (ItemUtils.canBeLit(heldItem)) return true;
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityBloomery tef) {
            if (Utils.containsOreDict(heldItem, Constants.charcoalPileOreDicts) && tef.canBeFueled()) {
                BlockUtils.playSound(worldIn, x, y, z, Registry.charcoal_pile);
                tef.addFuel();
                tef.addInventorySlotContentsUpdate(tef.getCurrentFuelStages(), player);
                tef.sendFuelUpdate();
                return true;
            }
        }

        return false;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".bloomery";
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        bloomery = reg.registerIcon(Primal.MODID + ":bloomery");
        particle = reg.registerIcon(Primal.MODID + ":fire_bricks");
        heatingIcons[0] = reg.registerIcon(Primal.MODID + ":charcoal_pile");
        heatingIcons[1] = reg.registerIcon(Primal.MODID + ":charcoal_pile_1");
        heatingIcons[2] = reg.registerIcon(Primal.MODID + ":charcoal_pile_2");
        heatingIcons[3] = reg.registerIcon(Primal.MODID + ":charcoal_pile_3");
        heatingIcons[4] = reg.registerIcon(Primal.MODID + ":charcoal_pile_4");
        heatingIcons[5] = reg.registerIcon(Primal.MODID + ":charcoal_pile_5");
        heatingIcons[6] = reg.registerIcon(Primal.MODID + ":charcoal_pile_6");
        heatingIcons[7] = reg.registerIcon(Primal.MODID + ":charcoal_pile_7");
    }

    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        if (side < 6) {
            TileEntity te = worldIn.getTileEntity(x, y, z);
            if (te instanceof TileEntityBloomery tef) {
                int heatingLevel = HeatUtils.getHeatingLevel(tef.temperature);
                return heatingIcons[heatingLevel];
            }
        }
        return super.getIcon(worldIn, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconBloomery) {
            return bloomery;
        }
        return particle;
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);
        dropItems(worldIn, x, y, z, 0);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBloomery();
    }

    @Override
    public ISimpleBlockRenderingHandler getRenderer() {
        return Primal.proxy.getBloomeryRenderer();
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
