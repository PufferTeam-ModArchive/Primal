package net.pufferlab.primal.scripts;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.recipes.RecipesHelper;

public class ScriptRemove implements IScript {

    public static final List<ItemStack> remove = new ArrayList<>();
    public static final List<ItemStack> removeSmelting = new ArrayList<>();

    public static final String[] itemsToRemove = new String[] { "minecraft:flower_pot", "minecraft:leather",
        "minecraft:leather_helmet", "minecraft:leather_chestplate", "minecraft:leather_leggings",
        "minecraft:leather_boots", "minecraft:bread" };
    public static final String[] toolsToRemove = new String[] { "minecraft:wooden_axe", "minecraft:wooden_pickaxe",
        "minecraft:wooden_sword", "minecraft:wooden_hoe", "minecraft:wooden_shovel", "minecraft:stone_axe",
        "minecraft:stone_pickaxe", "minecraft:stone_sword", "minecraft:stone_hoe", "minecraft:stone_shovel" };

    public static final String[] itemsToRemoveSmelting = new String[] { "minecraft:brick:0:*", "minecraft:coal:1:*",
        "minecraft:hardened_clay:1:*" };

    public void runEarly() {
        updateList();
        removeRecipes();
    }

    public void updateList() {
        if (Config.torchRebalance.getBoolean()) {
            remove.add(getItem("minecraft:torch:0:1"));
        }

        for (String s : itemsToRemove) {
            remove.add(getItem(s));
        }

        if (Config.vanillaToolsRemovalMode.getInt() == 1) {
            for (String s : toolsToRemove) {
                remove.add(getItem(s));
            }
        }
        for (String s : toolsToRemove) {
            addOreDict("toolBroken", getItem(s + ":*:*"));
        }

        for (String s : itemsToRemoveSmelting) {
            removeSmelting.add(getItem(s));
        }
    }

    public void removeRecipes() {
        RecipesHelper.removeRecipe(remove);
        RecipesHelper.removeFurnaceSmelting(removeSmelting);
    }
}
