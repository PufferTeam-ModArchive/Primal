package net.pufferlab.primal.items;

import net.pufferlab.primal.blocks.FluidType;

public class MetalType {

    public String name;
    public boolean isAlloy;
    public int weldingTemperature;
    public int meltingTemperature;

    public MetalType(String name, boolean isAlloy, int welding, int melting) {
        this.name = name;
        this.isAlloy = isAlloy;
        this.weldingTemperature = welding;
        this.meltingTemperature = melting;
    }

    public static String[] getNames(MetalType[] metals) {
        String[] names = new String[metals.length];
        for (int i = 0; i < metals.length; i++) {
            names[i] = metals[i].name;
        }
        return names;
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
