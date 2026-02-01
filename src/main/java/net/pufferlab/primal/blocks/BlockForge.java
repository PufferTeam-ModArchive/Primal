package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.TileEntityForge;
import net.pufferlab.primal.tileentities.TileEntityInventory;
import net.pufferlab.primal.utils.TemperatureUtils;

import com.falsepattern.rple.api.common.block.RPLECustomBlockBrightness;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "com.falsepattern.rple.api.common.block.RPLECustomBlockBrightness", modid = "rple")
public class BlockForge extends BlockContainerPrimal implements RPLECustomBlockBrightness {

    public IIcon[] icons = new IIcon[2];
    private IIcon[] heatingIcons = new IIcon[8];

    public static final int iconForge = 99;

    public BlockForge() {
        super(Material.rock);
        this.setHardness(1.0F);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
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
        if (te instanceof TileEntityForge tef) {
            return TemperatureUtils.getHeatingColor(tef.getTemperature());
        }
        return Constants.lightNone;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        ItemStack heldItem = player.getHeldItem();
        if (Utils.canBeLit(heldItem)) return true;
        TileEntity te = worldIn.getTileEntity(x, y, z);
        int meta = worldIn.getBlockMetadata(x, y, z);
        if (te instanceof TileEntityForge tef) {
            if (Utils.containsOreDict(heldItem, Constants.charcoalPileOreDicts) && meta < 5) {
                Utils.playSound(worldIn, x, y, z, Registry.charcoal_pile);
                tef.addInventorySlotContentsUpdate(meta, player);
                worldIn.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
                return true;
            }
        }

        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":forge");
        icons[1] = reg.registerIcon(Primal.MODID + ":ceramic");
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
            if (te instanceof TileEntityForge tef) {
                int heatingLevel = TemperatureUtils.getHeatingLevel(tef.temperature);
                return heatingIcons[heatingLevel];
            }
        }
        return super.getIcon(worldIn, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconForge) {
            return icons[0];
        }
        return icons[1];
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".forge";
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);
        dropItems(worldIn, x, y, z, 0);
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
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityForge();
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getForgeRenderID();
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
