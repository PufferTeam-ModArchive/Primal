package net.pufferlab.primal.scripts;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.*;

public interface IScript {

    default ItemStack getItem(String s) {
        return Utils.getItem(s);
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
        OreDictionary.registerOre(name, item);
    }

    default void addCampfireRecipe(ItemStack output, ItemStack input) {
        CampfireRecipe.addCampfireRecipe(output, input);
    }

    default void addChoppingLogRecipe(ItemStack output, String input) {
        ChoppingLogRecipe.addChoppingLogRecipe(output, input);
    }

    default void addTanningRecipe(ItemStack output, ItemStack input) {
        TanningRecipe.addTanningRecipe(output, input);
    }

    default void addKnappingRecipe(KnappingType type, ItemStack item, String... rows) {
        KnappingRecipe.addKnappingRecipe(type, item, rows);
    }

    default void addShapelessRecipe(ItemStack output, Object... recipe) {
        RecipesHelper.addShapelessRecipe(output, recipe);
    }

    default void addShapedRecipe(ItemStack output, Object... recipe) {
        RecipesHelper.addShapedRecipe(output, recipe);
    }

    default void addPitKilnRecipe(ItemStack output, ItemStack input) {
        PitKilnRecipe.addPitKilnRecipe(output, input);
    }

    default void addBarrelRecipe(ItemStack output, FluidStack outputLiquid, ItemStack input, FluidStack inputLiquid,
        int processing) {
        BarrelRecipe.addBarrelRecipe(output, outputLiquid, input, inputLiquid, processing);
    }

    default void addBarrelRecipe(ItemStack output, FluidStack outputLiquid, String input, FluidStack inputLiquid,
        int processing) {
        BarrelRecipe.addBarrelRecipe(output, outputLiquid, input, inputLiquid, processing);
    }
}
