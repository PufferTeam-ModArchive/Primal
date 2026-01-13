package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;

public class BlockTorchPrimitive extends BlockTorch implements IScheduledBlock {

    public static final int updateFired = 0;
    public static int burnTime = Config.torchBurnTime.getDefaultInt();
    public IIcon[] icons = new IIcon[1];
    public String name;

    public BlockTorchPrimitive(String name) {
        super();
        this.name = name;
        this.setHardness(0.0F);

        burnTime = Config.torchBurnTime.getInt();

        this.setStepSound(soundTypeWood);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        super.onBlockPlacedBy(worldIn, x, y, z, placer, itemIn);

        if (this == Registry.lit_torch) {
            addSchedule(worldIn, x, y, z, burnTime, updateFired);
        }
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        if (this == Registry.lit_torch) {
            removeSchedule(worldIn, x, y, z);
        }
    }

    @Override
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        if (this == Registry.lit_torch && type == updateFired) {
            world.setBlock(x, y, z, Registry.unlit_torch);
        }
    }

    @Override
    public void randomDisplayTick(World worldIn, int x, int y, int z, Random random) {
        if (this == Registry.lit_torch) {
            super.randomDisplayTick(worldIn, x, y, z, random);
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":" + name);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icons[0];
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + "." + name;
    }
}
