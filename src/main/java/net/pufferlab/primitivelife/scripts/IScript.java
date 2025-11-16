package net.pufferlab.primitivelife.scripts;

import net.minecraft.item.ItemStack;
import net.pufferlab.primitivelife.Utils;
import net.pufferlab.primitivelife.recipes.KnappingRecipes;
import net.pufferlab.primitivelife.recipes.PitKilnRecipes;
import net.pufferlab.primitivelife.recipes.RecipesHelper;

public interface IScript {

    default ItemStack getItem(String s) {
        return Utils.getItem(s);
    }

    default ItemStack getItem(String mod, String item, int meta, int number) {
        return Utils.getItem(mod, item, meta, number);
    }

    default ItemStack getModItem(String mod, String name, String wood, int number) {
        return Utils.getModItem(mod, name, wood, number);
    }

    default void addKnappingRecipe(int type, ItemStack item, String... rows) {
        KnappingRecipes.addKnappingRecipe(type, item, rows);
    }

    default void addShapedRecipe(ItemStack output, Object... recipe) {
        RecipesHelper.addShapedRecipe(output, recipe);
    }

    default void addPitKilnRecipe(ItemStack output, ItemStack input) {
        PitKilnRecipes.addPitKilnRecipe(output, input);
    }

}
