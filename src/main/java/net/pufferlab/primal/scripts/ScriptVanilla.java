package net.pufferlab.primal.scripts;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ScriptVanilla implements IScript {

    ArrayList<ItemStack> removeTool = new ArrayList<ItemStack>();
    public static final String[] toolsToRemove = new String[] { "minecraft:wooden_axe", "minecraft:wooden_pickaxe",
        "minecraft:wooden_sword", "minecraft:wooden_hoe", "minecraft:wooden_shovel", "minecraft:stone_axe",
        "minecraft:stone_pickaxe", "minecraft:stone_sword", "minecraft:stone_hoe", "minecraft:stone_shovel" };

    public void run() {
        for (String s : toolsToRemove) {
            removeTool.add(getItem(s + ":*:1"));
        }

        for (ItemStack tool : removeTool) {
            addOreDict("toolBroken", tool);
        }
    }
}
