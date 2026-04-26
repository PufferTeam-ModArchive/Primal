package net.pufferlab.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.pufferlab.primal.Primal;

public class BlockFluidPrimal extends BlockFluidClassic implements IPrimalBlock {

    public String name;

    public BlockFluidPrimal(Fluid fluid, Material mat, String name) {
        super(fluid, mat);
        this.name = name;
        if (mat == Material.lava) {
            this.setLightLevel(1.0F);
        }
    }

    protected IIcon stillIcon;
    protected IIcon flowingIcon;

    @Override
    public void registerBlockIcons(IIconRegister register) {
        stillIcon = register.registerIcon(Primal.MODID + ":" + this.name + "_still");
        flowingIcon = register.registerIcon(Primal.MODID + ":" + this.name + "_flow");
        this.getFluid()
            .setIcons(stillIcon, flowingIcon);
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z)
            .isAir(world, x, y, z)) return true;

        Block block = world.getBlock(x, y, z);

        if (block == this) {
            return false;
        }

        if (displacements.containsKey(block)) {
            return displacements.get(block);
        }

        Material material = block.getMaterial();
        if (material.blocksMovement() || material == Material.portal
            || material == Material.water
            || material == Material.lava) {
            return false;
        }

        int density = getDensity(world, x, y, z);
        if (density == Integer.MAX_VALUE) {
            return true;
        }

        if (this.density > density) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }

    @Override
    public String getUnlocalizedName() {
        return "fluid." + Primal.MODID + "." + this.name;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return null;
    }
}
