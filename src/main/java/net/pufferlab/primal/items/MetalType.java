package net.pufferlab.primal.items;

import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.blocks.FluidType;

public class MetalType {

    public String name;
    public FluidType fluid;
    public boolean isAlloy;
    public int weldingTemperature;
    public int meltingTemperature;
    public FluidStack fluidStack;
    public FluidStack ingotFluid;
    public FluidStack nuggetFluid;

    public MetalType(String name, boolean isAlloy, int welding, int melting, FluidType fluid) {
        this.name = name;
        this.isAlloy = isAlloy;
        this.weldingTemperature = welding;
        this.meltingTemperature = melting;
        this.fluid = fluid;
    }

    public static String[] getNames(MetalType[] metals) {
        String[] names = new String[metals.length];
        for (int i = 0; i < metals.length; i++) {
            names[i] = metals[i].name;
        }
        return names;
    }

    public static void setFluids(MetalType[] metals) {
        for (int i = 0; i < metals.length; i++) {
            metals[i].fluidStack = new FluidStack(metals[i].fluid.fluid, 1);
            metals[i].ingotFluid = new FluidStack(metals[i].fluid.fluid, Config.metalIngotValue.getInt());
            metals[i].nuggetFluid = new FluidStack(metals[i].fluid.fluid, Config.metalNuggetValue.getInt());
        }
    }

    public static MetalType getMetalType(MetalType[] metals, String name) {
        for (int i = 0; i < metals.length; i++) {
            if (metals[i].name.equals(name)) {
                return metals[i];
            }
        }
        return null;
    }

    public static FluidType getFluidType(FluidType[] fluids, String name) {
        for (int i = 0; i < fluids.length; i++) {
            if (("molten_" + fluids[i].name).equals(name)) {
                return fluids[i];
            }
        }
        return null;
    }
}
