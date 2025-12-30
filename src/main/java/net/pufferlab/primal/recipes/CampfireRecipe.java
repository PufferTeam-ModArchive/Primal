package net.pufferlab.primal.recipes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Utils;

public class CampfireRecipe {

    private static final Map<List<ItemStack>, ItemStack> recipeMap = new HashMap<>();

    public static void addCampfireRecipe(ItemStack output, List<ItemStack> input) {
        recipeMap.put(input, output);
    }

    public static void addCampfireRecipe(ItemStack output, ItemStack input) {
        recipeMap.put(Collections.singletonList(input), output);
    }

    public static void removeCampfireRecipe(ItemStack output, List<ItemStack> input) {
        recipeMap.entrySet()
            .removeIf(r -> {
                if (Utils.containsList(r.getKey(), input) && Utils.areStackEquals(r.getValue(), output)) {
                    return true;
                }
                return false;
            });
    }

    public static ItemStack getOutput(ItemStack input) {
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipeMap.entrySet()) {
            if (Utils.containsList(recipe.getKey(), input)) {
                return recipe.getValue();
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack input) {
        ItemStack output = getOutput(input);
        return output != null;
    }

    public static Map<List<ItemStack>, ItemStack> getRecipeMap() {
        return recipeMap;
    }
}
