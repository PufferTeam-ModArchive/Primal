package net.pufferlab.primitivelife.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class KnappingRecipes {

    private static final List<KnappingPattern> recipeList = new ArrayList<>();

    public static void addKnappingRecipe(int type, ItemStack item, String... rows) {
        recipeList.add(new KnappingPattern(type, item, rows));
    }

    public static ItemStack getOutput(int type, boolean[][] icons) {
        for (KnappingPattern currentPattern : recipeList) {
            if (currentPattern.equals(type, icons)) {
                return currentPattern.output.copy();
            }
        }
        return null;
    }

    public static List<KnappingPattern> getRecipeList() {
        return recipeList;
    }
}
