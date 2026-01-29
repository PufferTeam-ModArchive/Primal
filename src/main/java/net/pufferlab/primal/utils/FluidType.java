package net.pufferlab.primal.utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class FluidType {

    public boolean existingFluid;
    public String name;
    public Material material;
    public boolean hotLiquid;
    public Block block;
    public Fluid fluid;
    public int density;
    public int viscosity;
    public int temperature;

    public FluidType(String name) {
        this(name, Material.water);
    }

    public FluidType(String name, Material material) {
        this.name = name;
        this.material = material;
        if (material == Material.lava) {
            hotLiquid = true;
            this.temperature = 1300;
        } else {
            hotLiquid = false;
        }
        this.density = 1000;
        this.viscosity = 1000;
        this.existingFluid = false;
    }

    public FluidType(String name, Material material, boolean existingFluid) {
        this.name = name;
        this.material = material;
        if (material == Material.lava) {
            hotLiquid = true;
            this.temperature = 1300;
        } else {
            hotLiquid = false;
        }
        this.density = 1000;
        this.viscosity = 1000;
        this.existingFluid = existingFluid;
    }

    public FluidType setBlock(Block block) {
        this.block = block;
        return this;
    }

    public FluidType setFluid(Fluid block) {
        this.fluid = block;
        return this;
    }

    public static String[] getNames(FluidType[] fluids) {
        String[] names = new String[fluids.length];
        for (int i = 0; i < fluids.length; i++) {
            names[i] = fluids[i].name;
        }
        return names;
    }

    public static String[] getExistingNames(FluidType[] fluids) {
        String[] names = new String[fluids.length];
        for (int i = 0; i < fluids.length; i++) {
            if (fluids[i].existingFluid) {
                names[i] = fluids[i].name;
            }
        }
        return names;
    }

    public static boolean[] getBreaks(FluidType[] fluids) {
        boolean[] names = new boolean[fluids.length];
        for (int i = 0; i < fluids.length; i++) {
            names[i] = fluids[i].hotLiquid;
        }
        return names;
    }

}
