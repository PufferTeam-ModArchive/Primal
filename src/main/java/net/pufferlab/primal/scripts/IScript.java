package net.pufferlab.primal.scripts;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.KnappingRecipe;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.recipes.PitKilnRecipe;
import net.pufferlab.primal.recipes.RecipesHelper;

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

    default void addOreDict(String name, ItemStack item) {
        OreDictionary.registerOre(name, item);
    }

    default void addKnappingRecipe(KnappingType type, ItemStack item, String... rows) {
        KnappingRecipe.addKnappingRecipe(type, item, rows);
    }

    default void addShapedRecipe(ItemStack output, Object... recipe) {
        RecipesHelper.addShapedRecipe(output, recipe);
    }

    default void addPitKilnRecipe(ItemStack output, ItemStack input) {
        PitKilnRecipe.addPitKilnRecipe(output, input);
    }

}
