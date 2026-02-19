package net.pufferlab.primal.recipes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.Utils;

public class CampfireRecipe {

    public static final Item unlit_torch = Item.getItemFromBlock(Registry.unlit_torch);

    private static final Map<List<ItemStack>, ItemStack> recipeMap = new HashMap<>();

    public static void addRecipe(ItemStack output, List<ItemStack> input) {
        recipeMap.put(input, output);
    }

    public static void addRecipe(ItemStack output, String input) {
        recipeMap.put(OreDictionary.getOres(input), output);
    }

    public static void addRecipe(ItemStack output, ItemStack input) {
        recipeMap.put(Collections.singletonList(input), output);
    }

    public static void removeRecipe(ItemStack output, List<ItemStack> input) {
        recipeMap.entrySet()
            .removeIf(r -> {
                if (Utils.containsStack(r.getKey(), input) && Utils.equalsStack(r.getValue(), output)) {
                    return true;
                }
                return false;
            });
    }

    public static ItemStack getOutput(ItemStack input) {
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipeMap.entrySet()) {
            if (Utils.containsStack(recipe.getKey(), input)) {
                return recipe.getValue();
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack input) {
        if (input == null) return false;
        if (input.getItem() == null) return false;
        if (input.getItem() == unlit_torch) return false;
        ItemStack output = getOutput(input);
        return output != null;
    }

    public static Map<List<ItemStack>, ItemStack> getRecipeMap() {
        return recipeMap;
    }
}
