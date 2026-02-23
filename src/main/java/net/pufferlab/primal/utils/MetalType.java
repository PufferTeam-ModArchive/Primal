package net.pufferlab.primal.utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;

public class MetalType {

    public Item.ToolMaterial toolMaterial;
    public ItemArmor.ArmorMaterial armorMaterial;

    public String name;
    private String langKey;
    public String fluidName;
    public Fluid fluid;
    public boolean isAlloy;
    public int forgingTemperature;
    public int weldingTemperature;
    public int meltingTemperature;
    public int level;
    public FluidStack fluidStack;
    public FluidStack ingotFluid;
    public FluidStack doubleToolFluid;
    public FluidStack tripleToolFluid;
    public FluidStack nuggetFluid;
    public FluidStack oreFluid;
    public FluidStack smallOreFluid;

    public MetalType(String name, boolean isAlloy, int forging, int welding, int melting, FluidType fluid, int level) {
        this.name = name;
        this.fluidName = "molten_" + name;
        this.langKey = "metal." + Primal.MODID + "." + name + ".name";
        this.isAlloy = isAlloy;
        this.forgingTemperature = forging;
        this.weldingTemperature = welding;
        this.meltingTemperature = melting;
        this.fluid = fluid.fluid;
        this.level = level;
    }

    public String getTranslatedName() {
        return Utils.translate(this.langKey);
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

    public MetalType setToolMaterial(Item.ToolMaterial toolMaterial) {
        this.toolMaterial = toolMaterial;
        return this;
    }

    public MetalType setArmorMaterial(ItemArmor.ArmorMaterial armorMaterial) {
        this.armorMaterial = armorMaterial;
        return this;
    }

    public static void setFluids(MetalType[] metals) {
        for (int i = 0; i < metals.length; i++) {
            metals[i].fluidStack = new FluidStack(metals[i].fluid, 1);
            metals[i].ingotFluid = new FluidStack(metals[i].fluid, Config.metalIngotValue.getInt());
            if (Config.metalVanillaToolValue.getBoolean()) {
                metals[i].doubleToolFluid = new FluidStack(metals[i].fluid, Config.metalIngotValue.getInt() * 2);
                metals[i].tripleToolFluid = new FluidStack(metals[i].fluid, Config.metalIngotValue.getInt() * 3);
            } else {
                metals[i].doubleToolFluid = metals[i].ingotFluid;
                metals[i].tripleToolFluid = metals[i].ingotFluid;
            }
            metals[i].nuggetFluid = new FluidStack(metals[i].fluid, Config.metalNuggetValue.getInt());
            metals[i].oreFluid = new FluidStack(metals[i].fluid, Config.metalOreValue.getInt());
            metals[i].smallOreFluid = new FluidStack(metals[i].fluid, Config.metalSmallOreValue.getInt());
        }
    }

    public static Map<Fluid, MetalType> fluidMap;

    public static MetalType getMetalFromFluid(FluidStack stack) {
        if (fluidMap == null) {
            fluidMap = new HashMap<>();
            for (MetalType type : Constants.metalTypesAll) {
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
