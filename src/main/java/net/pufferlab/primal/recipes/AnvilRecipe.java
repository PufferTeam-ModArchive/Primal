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

    public ItemStack output;
    public List<ItemStack> input;
    public AnvilAction[] actions;
    public AnvilOrder[] orders;

    public AnvilRecipe(ItemStack output, List<ItemStack> input, Object... objects) {
        int j = 0;
        int size = objects.length / 2;
        this.actions = new AnvilAction[size];
        this.orders = new AnvilOrder[size];
        for (int i = 0; i < objects.length; i = i + 2) {
            if (objects[i] instanceof AnvilAction action) {
                this.actions[j] = action;
            }
            if (objects[i + 1] instanceof AnvilOrder order) {
                this.orders[j] = order;
            }
            j++;
        }
        this.output = output;
        this.input = input;
    }

    public boolean equals(ItemStack input, AnvilAction... actions) {
        if (!Utils.containsStack(input, this.input)) return false;

        for (int i = 0; i < this.actions.length; i++) {
            AnvilAction recipeAction = this.actions[i];
            AnvilOrder recipeOrder = this.orders[i];

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
}
