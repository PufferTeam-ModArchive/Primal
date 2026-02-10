package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
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
