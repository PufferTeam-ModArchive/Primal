package net.pufferlab.primal.blocks;

import net.minecraftforge.fluids.Fluid;
import net.pufferlab.primal.Primal;

public class FluidPrimal extends Fluid {

    public FluidPrimal(String fluidName) {
        super(Primal.MODID + "." + fluidName);
        setUnlocalizedName(fluidName);
    }

    @Override
    public String getUnlocalizedName() {
        return "fluid." + Primal.MODID + "." + this.unlocalizedName;
    }
}
