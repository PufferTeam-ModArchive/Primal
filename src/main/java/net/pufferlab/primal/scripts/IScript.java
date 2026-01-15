package net.pufferlab.primal.scripts;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.*;

import cpw.mods.fml.common.registry.LanguageRegistry;

public interface IScript {

    int wildcard = Constants.wildcard;

    default ItemStack getItem(String s) {
        return Utils.getItem(s);
    }

    default ItemStack getItem(String mod, String item) {
        return Utils.getItem(mod, item);
    }

    default ItemStack getItem(String mod, String item, int meta, int number) {
        return Utils.getItem(mod, item, meta, number);
    }

    default ItemStack getModItem(String wood, int number) {
        return Utils.getModItem(wood, number);
    }

    default ItemStack getModItem(String type, String wood, int number) {
        return Utils.getModItem(type, wood, number);
    }

    default FluidStack getFluid(String fluid, int number) {
        return Utils.getFluid(fluid, number);
    }

    default void addOreDict(String name, ItemStack item) {
        if (item != null) {
            OreDictionary.registerOre(name, item);
        }
    }

    default void addCampfireRecipe(ItemStack output, ItemStack input) {
        if (output != null && input != null) {
            CampfireRecipe.addRecipe(output, input);
        }
    }

    default void addQuernRecipe(ItemStack output, ItemStack input) {
        if (output != null && input != null) {
            QuernRecipe.addRecipe(output, input);
        }
    }

    default void addChoppingLogRecipe(ItemStack output, String input) {
        if (output != null && input != null) {
            ChoppingLogRecipe.addRecipe(output, input);
        }
    }

    default void addTanningRecipe(ItemStack output, ItemStack input) {
        if (output != null && input != null) {
            TanningRecipe.addRecipe(output, input);
        }
    }

    default void addKnappingRecipe(KnappingType type, ItemStack item, String... rows) {
        if (type != null && item != null) {
            KnappingRecipe.addRecipe(type, item, rows);
        }
    }

    default void addShapelessRecipe(ItemStack output, Object... recipe) {
        RecipesHelper.addShapelessRecipe(output, recipe);
    }

    default void addShapedRecipe(ItemStack output, Object... recipe) {
        RecipesHelper.addShapedRecipe(output, recipe);
    }

    default void addPitKilnRecipe(ItemStack output, ItemStack input) {
        if (output != null && input != null) {
            PitKilnRecipe.addRecipe(output, input);
        }
    }

    default void addMeltingRecipe(FluidStack output, String input) {
        if (Utils.isValidOreDict(input) && output != null) {
            MeltingRecipe.addRecipe(output, input);
        }
    }

    default void addAlloyingRecipe(FluidStack output, Object... inputs) {
        AlloyingRecipe.addRecipe(output, inputs);
    }

    default void addMeltingRecipe(FluidStack output, ItemStack input) {
        MeltingRecipe.addRecipe(output, input);
    }

    default void addCastingRecipe(ItemStack cast, ItemStack output, FluidStack input) {
        CastingRecipe.addRecipe(cast, output, input);
    }

    default void addBarrelRecipe(ItemStack output, FluidStack outputLiquid, ItemStack input, FluidStack inputLiquid,
        int processing) {
        if ((output != null || outputLiquid != null) && (input != null || inputLiquid != null)) {
            BarrelRecipe.addRecipe(output, outputLiquid, input, inputLiquid, processing);
        }
    }

    default void addBarrelRecipe(ItemStack output, FluidStack outputLiquid, String input, FluidStack inputLiquid,
        int processing) {
        if ((output != null || outputLiquid != null) && (Utils.isValidOreDict(input) || inputLiquid != null)) {
            BarrelRecipe.addRecipe(output, outputLiquid, input, inputLiquid, processing);
        }
    }

    @SuppressWarnings("deprecation")
    default void addLocalization(String localization, String name) {
        LanguageRegistry.instance()
            .addStringLocalization(localization, name);
    }
}
