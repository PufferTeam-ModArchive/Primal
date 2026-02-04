package net.pufferlab.primal.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.Utils;

public class AnvilRecipe {

    private static final List<AnvilRecipe> recipeList = new ArrayList<>();

    public static void addRecipe(ItemStack output, ItemStack input, Object... objects) {
        recipeList.add(new AnvilRecipe(output, Collections.singletonList(input), objects));
    }

    public static void addRecipe(ItemStack output, String input, Object... objects) {
        recipeList.add(new AnvilRecipe(output, OreDictionary.getOres(input), objects));
    }

    public static AnvilRecipe getRecipe(ItemStack inputItem, AnvilAction... actions) {
        for (AnvilRecipe currentRecipe : recipeList) {
            if (currentRecipe.equals(inputItem, actions)) {
                return currentRecipe;
            }
        }
        return null;
    }

    public static List<AnvilRecipe> getRecipes(ItemStack inputItem) {
        List<AnvilRecipe> recipes = new ArrayList<>();
        for (AnvilRecipe currentRecipe : recipeList) {
            if (currentRecipe.equals(inputItem)) {
                recipes.add(currentRecipe);
            }
        }
        return recipes;
    }

    public static AnvilRecipe getRecipe(ItemStack inputItem) {
        for (AnvilRecipe currentRecipe : recipeList) {
            if (currentRecipe.equals(inputItem)) {
                return currentRecipe;
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack input) {
        AnvilRecipe recipe = getRecipe(input);
        return recipe != null;
    }

    public static List<AnvilRecipe> getRecipeList() {
        return recipeList;
    }

    public ItemStack output;
    public List<ItemStack> input;
    public AnvilAction[] recipeActions;
    public AnvilOrder[] recipeOrders;
    public int recipeLine;
    public int recipeID;

    public AnvilRecipe(ItemStack output, List<ItemStack> input, Object... objects) {
        int j = 0;
        this.recipeID = recipeList.size();
        this.recipeLine = 70;
        int size = objects.length / 2;
        this.recipeActions = new AnvilAction[size];
        this.recipeOrders = new AnvilOrder[size];
        for (int i = 0; i < objects.length; i = i + 2) {
            if (objects[i] instanceof AnvilAction action) {
                if (action.isHitAction()) {
                    this.recipeActions[j] = AnvilAction.hitMedium;
                } else {
                    this.recipeActions[j] = action;
                }
            }
            if (objects[i + 1] instanceof AnvilOrder order) {
                this.recipeOrders[j] = order;
            }
            j++;
        }
        this.output = output;
        this.input = input;

        this.normalizeByOrder();
    }

    private void normalizeByOrder() {
        int max = recipeActions.length;

        AnvilAction[] sortedActions = new AnvilAction[max];
        AnvilOrder[] sortedOrders = new AnvilOrder[max];

        for (int i = 0; i < max; i++) {
            AnvilOrder order = recipeOrders[i];
            int target = order.getTargetIndex();
            if (target >= 0) {
                if (sortedActions[target] != null) {
                    throw new IllegalStateException("Duplicate order: " + order);
                }
                sortedOrders[target] = order;
                sortedActions[target] = recipeActions[i];
            }
        }
        for (int i = 0; i < max; i++) {
            AnvilOrder order = recipeOrders[i];
            int target = order.getTargetIndex();
            if (target < 0) {
                int start = order.getStartIndex();
                boolean placed = false;
                for (int j = start; j < max; j++) {
                    if (sortedActions[j] == null) {
                        sortedOrders[j] = order;
                        sortedActions[j] = recipeActions[i];
                        placed = true;
                        break;
                    }
                }
                if (!placed) {
                    throw new IllegalStateException("No valid slot for order: " + order);
                }
            }
        }
        this.recipeActions = sortedActions;
        this.recipeOrders = sortedOrders;
    }

    public boolean equals(ItemStack input, AnvilAction... actions) {
        if (!Utils.containsStack(input, this.input)) return false;

        for (int i = 0; i < this.recipeActions.length; i++) {
            AnvilAction recipeAction = this.recipeActions[i];
            AnvilOrder recipeOrder = this.recipeOrders[i];

            boolean found = false;

            for (int j = 0; j < actions.length; j++) {
                if (recipeAction.equals(actions[j])) {
                    found = true;

                    if (!recipeOrder.isValidOrder(j)) return false;

                    break;
                }
            }

            if (!found) return false;
        }

        return true;
    }

    public boolean equals(ItemStack input) {
        if (Utils.containsStack(this.input, input)) {
            return true;
        }
        return false;
    }

    public boolean equals(AnvilAction action, int order) {
        if (action == null) return false;
        for (int i = 0; i < this.recipeActions.length; i++) {
            AnvilAction recipeAction = this.recipeActions[i];
            AnvilOrder recipeOrder = this.recipeOrders[i];
            if (recipeAction.equals(action)) {

                if (recipeOrder.isValidOrder(order)) {
                    return true;
                }
            }
        }
        return false;
    }

}
