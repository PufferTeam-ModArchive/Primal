package net.pufferlab.primal.recipes;

import java.util.*;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.pufferlab.primal.inventory.InventoryCraftingHolder;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesHelper {

    public static final List<ItemStack> remove = new ArrayList<>();
    public static final List<ItemStack> removeSmelting = new ArrayList<>();
    public static final List<InventoryCraftingHolder> inventories = new ArrayList<>();

    public static void removeRecipe(ItemStack input) {
        remove.add(input);
    }

    public static void removeRecipes() {
        ArrayList<IRecipe> recipes = (ArrayList<IRecipe>) CraftingManager.getInstance()
            .getRecipeList();
        recipes.removeIf(r -> {
            ItemStack rCopy = r.getRecipeOutput();
            int size = r.getRecipeSize();
            if (size > 9) {
                return false;
            }
            if (rCopy == null) {
                return false;
            }
            if (rCopy.getItem() == null) {
                return false;
            }
            for (ItemStack i : remove) {
                if (Utils.equalsStack(rCopy, i)) {
                    return true;
                }
            }
            return false;
        });
    }

    public static void addShapedRecipe(ItemStack output, Object... recipe) {
        GameRegistry.addRecipe(new ShapedOreRecipe(output, recipe));
    }

    public static void addShapelessRecipe(ItemStack output, Object... recipe) {
        GameRegistry.addRecipe(new ShapelessOreRecipe(output, recipe));
    }

    public static void removeSmeltingRecipe(ItemStack output) {
        removeSmelting.add(output);
    }

    public static void removeSmeltingRecipes() {
        Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting()
            .getSmeltingList();
        recipes.entrySet()
            .removeIf(r -> {
                ItemStack rCopy = r.getValue();
                for (ItemStack i : removeSmelting) {
                    if (Utils.equalsStack(rCopy, i)) {
                        return true;
                    }
                }
                return false;
            });
    }

    public static void addSmeltingRecipe(ItemStack output, ItemStack input, float xp) {
        GameRegistry.addSmelting(input, output, xp);
    }

    public static void removeShapedRecipe(ItemStack output, Object... recipe) {
        ShapedOreRecipe recipeOre = new ShapedOreRecipe(output, recipe);
        Object[] objects = recipeOre.getInput();
        ItemStack[] stacks = new ItemStack[objects.length];
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof ItemStack) {
                stacks[i] = (ItemStack) objects[i];
            } else if (objects[i] instanceof List<?>list) {
                stacks[i] = (ItemStack) list.get(0);
            }
        }
        inventories.add(new InventoryCraftingHolder(output, stacks));
    }

    public static void removeShapelessRecipe(ItemStack output, Object... recipe) {
        ShapelessOreRecipe recipeOre = new ShapelessOreRecipe(output, recipe);
        List<Object> objects = recipeOre.getInput();
        ItemStack[] stacks = new ItemStack[objects.size()];
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) instanceof ItemStack) {
                stacks[i] = (ItemStack) objects.get(i);
            } else if (objects.get(i) instanceof List<?>list) {
                stacks[i] = (ItemStack) list.get(0);
            }
        }
        inventories.add(new InventoryCraftingHolder(output, stacks));
    }

    public static void removeSpecialRecipes() {
        ArrayList<IRecipe> recipes = (ArrayList<IRecipe>) CraftingManager.getInstance()
            .getRecipeList();

        recipes.removeIf(r -> {
            try {
                for (InventoryCraftingHolder inventoryCraftingHolder : inventories) {
                    if (Utils.equalsStack(r.getRecipeOutput(), inventoryCraftingHolder.input)
                        && r.matches(inventoryCraftingHolder, null)) {
                        return true;
                    }
                }
            } catch (Exception ignored) {}
            return false;
        });
    }
}
