package net.pufferlab.primal.recipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.Utils;

public class ChoppingLogRecipe {

    private static final Map<String, ItemStack> recipeMap = new HashMap<>();
    private static final Map<String, List<ItemStack>> oreDictMap = new HashMap<>();

    public static void addChoppingLogRecipe(ItemStack output, String input) {
        oreDictMap.put(input, OreDictionary.getOres(input));
        recipeMap.put(input, output);
    }

    public static ItemStack getOutput(String input) {
        if (input == null) return null;
        for (Map.Entry<String, ItemStack> recipe : recipeMap.entrySet()) {
            if (recipe.getKey()
                .equals(input)) {
                return recipe.getValue();
            }
        }
        return null;
    }

    public static String getOreDict(ItemStack input) {
        for (Map.Entry<String, ItemStack> recipe : recipeMap.entrySet()) {
            if (Utils.containsOreDict(input, recipe.getKey())) {
                return recipe.getKey();
            }
        }
        return null;
    }

    public static boolean hasRecipe(String input) {
        ItemStack output = getOutput(input);
        return output != null;
    }

    public static Map<String, ItemStack> getRecipeMap() {
        return recipeMap;
    }

    public static Map<String, List<ItemStack>> getOreDictMap() {
        return oreDictMap;
    }
}
