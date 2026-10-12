package net.pufferlab.primal.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;

public class ChoppingLogRecipe extends Recipe<ChoppingLogRecipe> {

    private static final RecipeList<ChoppingLogRecipe> recipeList = new RecipeList<>();

    public static void addRecipe(ItemStack output, List<ItemStack> input) {
        recipeList.add(new ChoppingLogRecipe(input, output));
    }

    public static void addRecipe(ItemStack output, String input) {
        recipeList.add(new ChoppingLogRecipe(ItemUtils.getOres(input), output));
    }

    public static void addRecipe(ItemStack output, ItemStack input) {
        recipeList.add(new ChoppingLogRecipe(Utils.asList(input), output));
    }

    public static void removeRecipe(ItemStack output, List<ItemStack> input) {
        recipeList.removeIf(r -> {
            if (Utils.containsStack(r.input, input) && Utils.equalsStack(r.output, output)) {
                return true;
            }
            return false;
        });
    }

    public static ItemStack getOutput(ItemStack input) {
        for (ChoppingLogRecipe recipe : recipeList) {
            if (Utils.containsStack(recipe.input, input)) {
                return recipe.output;
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack input) {
        if (input == null) return false;
        ItemStack output = getOutput(input);
        return output != null;
    }

    public static List<ChoppingLogRecipe> getRecipeList() {
        return recipeList;
    }

    public List<ItemStack> input;
    public ItemStack output;

    public ChoppingLogRecipe(List<ItemStack> input, ItemStack output) {
        this.input = input;
        this.output = output;
    }
}
