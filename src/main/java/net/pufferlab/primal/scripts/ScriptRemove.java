package net.pufferlab.primal.scripts;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.recipes.RecipesHelper;

public class ScriptRemove implements IScript {

    ArrayList<ItemStack> remove = new ArrayList<ItemStack>();
    ArrayList<ItemStack> removeSmelting = new ArrayList<ItemStack>();

    public static final String[] itemsToRemove = new String[] { "minecraft:flower_pot" };
    public static final String[] toolsToRemove = new String[] { "minecraft:wooden_axe", "minecraft:wooden_pickaxe",
        "minecraft:wooden_sword", "minecraft:wooden_hoe", "minecraft:wooden_shovel", "minecraft:stone_axe",
        "minecraft:stone_pickaxe", "minecraft:stone_sword", "minecraft:stone_hoe", "minecraft:stone_shovel" };

    public static final String[] itemsToRemoveSmelting = new String[] { "minecraft:brick:0:*", "minecraft:coal:1:*" };

    public void init() {
        for (String s : itemsToRemove) {
            remove.add(getItem(s));
        }

        if (Config.vanillaToolsRemovalMode == 1) {
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

    public void postInit() {
        RecipesHelper.removeRecipe(remove);
        RecipesHelper.removeFurnaceSmelting(removeSmelting);
    }
}
