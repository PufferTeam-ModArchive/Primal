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

public class MetalType implements IPrimalType {

    public Item.ToolMaterial toolMaterial;
    public ItemArmor.ArmorMaterial armorMaterial;

    public String name;
    private final String langKey;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getString(Config.Value.Type index) {
        if (index == Config.Value.Type.fluid) {
            return fluidName;
        }
        return null;
    }

    @Override
    public void setString(Config.Value.Type index, String string) {
        if (index == Config.Value.Type.fluid) {
            Fluid fluid = ItemUtils.getStaticFluid(string);
            if (fluid != null) {
                this.fluid = fluid;
                this.fluidStack = new FluidStack(this.fluid, 1);
                this.ingotFluid = new FluidStack(this.fluid, Config.metalIngotValue.getInt());
                if (Config.metalVanillaToolValue.getBoolean()) {
                    this.doubleToolFluid = new FluidStack(this.fluid, Config.metalIngotValue.getInt() * 2);
                    this.tripleToolFluid = new FluidStack(this.fluid, Config.metalIngotValue.getInt() * 3);
                } else {
                    this.doubleToolFluid = this.ingotFluid;
                    this.tripleToolFluid = this.ingotFluid;
                }
                this.nuggetFluid = new FluidStack(this.fluid, Config.metalNuggetValue.getInt());
                this.oreFluid = new FluidStack(this.fluid, Config.metalOreValue.getInt());
                this.smallOreFluid = new FluidStack(this.fluid, Config.metalSmallOreValue.getInt());
            }
        }
    }

    @Override
    public int getInt(Config.Value.Type index) {
        if (index == Config.Value.Type.melting) {
            return this.meltingTemperature;
        }
        if (index == Config.Value.Type.forging) {
            return this.forgingTemperature;
        }
        if (index == Config.Value.Type.welding) {
            return this.weldingTemperature;
        }
        return 0;
    }

    @Override
    public void setInt(Config.Value.Type index, int primary) {
        if (index == Config.Value.Type.melting) {
            this.meltingTemperature = primary;
        }
        if (index == Config.Value.Type.forging) {
            this.forgingTemperature = primary;
        }
        if (index == Config.Value.Type.welding) {
            this.weldingTemperature = primary;
        }
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
