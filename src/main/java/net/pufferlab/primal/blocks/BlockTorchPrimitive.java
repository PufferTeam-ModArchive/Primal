package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;

import com.falsepattern.rple.api.common.block.RPLECustomBlockBrightness;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "com.falsepattern.rple.api.common.block.RPLECustomBlockBrightness", modid = "rple")
public class BlockTorchPrimitive extends BlockTorch
    implements IPrimalBlock, IScheduledBlock, RPLECustomBlockBrightness {

    public static final int updateFired = 0;
    public static int burnTime = Config.torchBurnTime.getDefaultInt();
    public IIcon torch;
    public String name;
    public boolean isLit;

    public BlockTorchPrimitive(String name, boolean isLit) {
        super();
        this.name = name;
        this.setHardness(0.0F);
        this.isLit = isLit;
        if (isLit) {
            this.setLightLevel(0.9375F);
        } else {
            this.setLightLevel(0.0F);
        }

        burnTime = Config.torchBurnTime.getInt();

        this.setStepSound(soundTypeWood);
    }

    @Override
    public short rple$getCustomBrightnessColor() {
        if (this.isLit) {
            return Constants.lightTorch;
        }
        return Constants.lightNone;
    }

    @Override
    public short rple$getCustomBrightnessColor(int blockMeta) {
        if (this.isLit) {
            return Constants.lightTorch;
        }
        return Constants.lightNone;
    }

    @Override
    public short rple$getCustomBrightnessColor(IBlockAccess world, int blockMeta, int posX, int posY, int posZ) {
        if (this.isLit) {
            return Constants.lightTorch;
        }
        return Constants.lightNone;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        super.onBlockPlacedBy(worldIn, x, y, z, placer, itemIn);

        if (this.isLit) {
            addSchedule(worldIn, x, y, z, burnTime, updateFired);
        }
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        if (this.isLit) {
            removeSchedule(worldIn, x, y, z);
        }
    }

    @Override
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        if (this.isLit && type == updateFired) {
            world.setBlock(x, y, z, Registry.unlit_torch);
        }
    }

    @Override
    public void randomDisplayTick(World worldIn, int x, int y, int z, Random random) {
        if (this.isLit) {
            super.randomDisplayTick(worldIn, x, y, z, random);
        }
    }

    @Override
    public Block getBlock() {
        return this;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        torch = reg.registerIcon(Primal.MODID + ":" + name);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return torch;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + "." + name;
    }

    @Override
    public boolean canRegister() {
        return Config.litTorches.getBoolean();
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTab;
    }
}
