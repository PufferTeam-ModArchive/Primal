package net.pufferlab.primitivelife.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.pufferlab.primitivelife.Utils;

public class PitKilnRecipes {

    private static final Map<String, ItemStack> recipeMap = new HashMap<>();
    private static final Map<String, ItemStack> recipeMapOutput = new HashMap<>();
    private static final Map<ItemStack, ItemStack> recipeMapList = new HashMap<>();

    public static void addPitKilnRecipe(ItemStack output, ItemStack input) {
        recipeMap.put(Utils.getItemKey(input), output);
        recipeMapOutput.put(Utils.getItemKey(output), input);
        recipeMapList.put(output, input);
    }

    public static Map<String, ItemStack> getRecipeMap() {
        return recipeMap;
    }

    public static Map<String, ItemStack> getRecipeMapOutput() {
        return recipeMapOutput;
    }

    public static Map<ItemStack, ItemStack> getRecipeMapList() {
        return recipeMapList;
    }
}
