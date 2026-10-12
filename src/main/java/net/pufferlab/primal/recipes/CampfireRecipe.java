package net.pufferlab.primal.recipes;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;

public class CampfireRecipe extends Recipe<CampfireRecipe> {

    public static final Item unlit_torch = Item.getItemFromBlock(Registry.unlit_torch);

    private static final RecipeList<CampfireRecipe> recipeList = new RecipeList<>();

    public static void addRecipe(ItemStack output, List<ItemStack> input) {
        recipeList.add(new CampfireRecipe(input, output));
    }

    public static void addRecipe(ItemStack output, String input) {
        recipeList.add(new CampfireRecipe(ItemUtils.getOres(input), output));
    }

    public static void addRecipe(ItemStack output, ItemStack input) {
        recipeList.add(new CampfireRecipe(Utils.asList(input), output));
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
        for (CampfireRecipe recipe : recipeList) {
            if (Utils.containsStack(recipe.input, input)) {
                return recipe.output;
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

    public static List<CampfireRecipe> getRecipeList() {
        return recipeList;
    }

    public List<ItemStack> input;
    public ItemStack output;

    public CampfireRecipe(List<ItemStack> input, ItemStack output) {
        this.input = input;
        this.output = output;
    }
}
