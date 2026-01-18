package net.pufferlab.primal.utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;

public class MetalType {

    public String name;
    public Fluid fluid;
    public boolean isAlloy;
    public int weldingTemperature;
    public int meltingTemperature;
    public int level;
    public FluidStack fluidStack;
    public FluidStack ingotFluid;
    public FluidStack doubleIngotFluid;
    public FluidStack tripleIngotFluid;
    public FluidStack nuggetFluid;

    public MetalType(String name, boolean isAlloy, int welding, int melting, FluidType fluid, int level) {
        this.name = name;
        this.isAlloy = isAlloy;
        this.weldingTemperature = welding;
        this.meltingTemperature = melting;
        this.fluid = fluid.fluid;
        this.level = level;
    }

    public static String[] getNames(MetalType[] metals) {
        String[] names = new String[metals.length];
        for (int i = 0; i < metals.length; i++) {
            names[i] = metals[i].name;
        }
        return names;
    }

    public MetalType setFluid(Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    public MetalType setMeltingTemperature(int temp) {
        this.meltingTemperature = temp;
        return this;
    }

    public static void setFluids(MetalType[] metals) {
        for (int i = 0; i < metals.length; i++) {
            metals[i].fluidStack = new FluidStack(metals[i].fluid, 1);
            metals[i].ingotFluid = new FluidStack(metals[i].fluid, Config.metalIngotValue.getInt());
            metals[i].doubleIngotFluid = new FluidStack(metals[i].fluid, Config.metalIngotValue.getInt() * 2);
            metals[i].tripleIngotFluid = new FluidStack(metals[i].fluid, Config.metalIngotValue.getInt() * 3);
            metals[i].nuggetFluid = new FluidStack(metals[i].fluid, Config.metalNuggetValue.getInt());
        }
    }

    public static Map<Fluid, MetalType> fluidMap;

    public static MetalType getMetalFromFluid(FluidStack stack) {
        if (fluidMap == null) {
            fluidMap = new HashMap<>();
            for (MetalType type : Constants.metalTypes) {
                Fluid fluid = type.fluid;
                fluidMap.put(fluid, type);
            }
        }
        if (stack != null) {
            Fluid fluid = stack.getFluid();
            if (fluidMap.containsKey(fluid)) {
                return fluidMap.get(fluid);
            }
        }
        return null;
    }

    public static String[] getTools(MetalType[] metalTypes) {
        String[] mat = new String[metalTypes.length];
        for (int i = 0; i < metalTypes.length; i++) {
            mat[i] = "pickaxe";
        }
        return mat;
    }

    public static int[] getLevels(MetalType[] metalTypes) {
        int[] lvl = new int[metalTypes.length];
        for (int i = 0; i < metalTypes.length; i++) {
            lvl[i] = metalTypes[i].level;
        }
        return lvl;
    }
}
