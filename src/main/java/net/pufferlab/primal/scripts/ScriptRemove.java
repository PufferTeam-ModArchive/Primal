package net.pufferlab.primal.scripts;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.recipes.RecipesHelper;

public class ScriptRemove implements IScript {

    ArrayList<ItemStack> remove = new ArrayList<ItemStack>();
    ArrayList<ItemStack> removeSmelting = new ArrayList<ItemStack>();

    public static final String[] itemsToRemove = new String[] { "minecraft:flower_pot" };

    public static final String[] itemsToRemoveSmelting = new String[] { "minecraft:brick" };

    public void init() {
        for (String s : itemsToRemove) {
            remove.add(getItem(s));
        }

        for (String s : itemsToRemoveSmelting) {
            removeSmelting.add(getItem(s));
        }
    }

    public void postInit() {
        RecipesHelper.removeRecipe(remove);
        RecipesHelper.removeFurnaceSmelting(removeSmelting);
    }
}
