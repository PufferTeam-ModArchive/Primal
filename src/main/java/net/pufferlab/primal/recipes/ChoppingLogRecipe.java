package net.pufferlab.primal.recipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.Utils;

public class ChoppingLogRecipe {

    private static final Map<List<ItemStack>, ItemStack> recipeMap = new HashMap<>();

    public static void addChoppingLogRecipe(ItemStack output, String input) {
        recipeMap.put(OreDictionary.getOres(input), output);
    }

    public static ItemStack getOutput(ItemStack input) {
        if (input == null) return null;
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipeMap.entrySet()) {
            if (Utils.containsList(input, recipe.getKey())) {
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
