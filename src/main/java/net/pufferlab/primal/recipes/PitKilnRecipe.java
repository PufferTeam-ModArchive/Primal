package net.pufferlab.primal.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Utils;

public class PitKilnRecipe {

    private static final Map<ItemStack, ItemStack> recipeMap = new HashMap<>();

    public static void addPitKilnRecipe(ItemStack output, ItemStack input) {
        recipeMap.put(input, output);
    }

    public static ItemStack getOutput(ItemStack input) {
        for (Map.Entry<ItemStack, ItemStack> recipe : recipeMap.entrySet()) {
            if (Utils.containsStack(recipe.getKey(), input)) {
                return recipe.getValue();
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack input) {
        ItemStack output = getOutput(input);
        return output != null;
    }

    public static Map<ItemStack, ItemStack> getRecipeMap() {
        return recipeMap;
    }

}
