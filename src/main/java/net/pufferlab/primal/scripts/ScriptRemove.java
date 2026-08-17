package net.pufferlab.primal.scripts;

import static net.pufferlab.primal.recipes.RecipesHelper.*;

import net.pufferlab.primal.Config;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.recipes.RecipesHelper;

public class ScriptRemove implements IScript {

    public static final String[] itemsToRemove = new String[] { "minecraft:flower_pot", "minecraft:leather",
        "minecraft:leather_helmet", "minecraft:leather_chestplate", "minecraft:leather_leggings",
        "minecraft:leather_boots", "minecraft:bread" };
    public static final String[] toolsToRemove = new String[] { "minecraft:wooden_axe", "minecraft:wooden_pickaxe",
        "minecraft:wooden_sword", "minecraft:wooden_hoe", "minecraft:wooden_shovel", "minecraft:stone_axe",
        "minecraft:stone_pickaxe", "minecraft:stone_sword", "minecraft:stone_hoe", "minecraft:stone_shovel" };

    public static final String[] itemsToRemoveSmelting = new String[] { "minecraft:brick:0:*", "minecraft:coal:1:*",
        "minecraft:hardened_clay:1:*" };

    public void runEarly() {
        updateRemoval();
        if (Mods.efr.isLoaded()) {
            updateEFRRemoval();
        }
        if (Mods.bop.isLoaded()) {
            updateBOPRemoval();
        }
        if (Mods.tc.isLoaded()) {
            updateTCRemoval();
        }
        removeRecipes();
    }

    public void updateRemoval() {
        if (Config.torchRebalance.getBoolean()) {
            removeRecipe(getItem("minecraft:torch:0:1"));
        }

        for (String s : itemsToRemove) {
            removeRecipe(getItem(s));
        }

        if (Config.vanillaToolsRemovalMode.getInt() == 1) {
            for (String s : toolsToRemove) {
                removeRecipe(getItem(s));
            }
        }
        for (String s : toolsToRemove) {
            addOreDict("toolBroken", getItem(s + ":*:*"));
        }

        for (String s : itemsToRemoveSmelting) {
            removeSmeltingRecipe(getItem(s));
        }

        removeShapelessRecipe(getItem("minecraft:dye:11:1"), getItem("minecraft:yellow_flower:0:1"));
        removeShapelessRecipe(getItem("minecraft:dye:1:1"), getItem("minecraft:red_flower:0:1"));
        removeShapelessRecipe(getItem("minecraft:dye:1:1"), getItem("minecraft:red_flower:4:1"));
        removeShapelessRecipe(getItem("minecraft:dye:1:2"), getItem("minecraft:double_plant:4:1"));
        removeShapelessRecipe(getItem("minecraft:dye:12:2"), getItem("minecraft:red_flower:1:1"));
        removeShapelessRecipe(getItem("minecraft:dye:13:2"), getItem("minecraft:red_flower:2:1"));
        removeShapelessRecipe(getItem("minecraft:dye:7:2"), getItem("minecraft:red_flower:3:1"));
        removeShapelessRecipe(getItem("minecraft:dye:1:2"), getItem("minecraft:red_flower:4:1"));
        removeShapelessRecipe(getItem("minecraft:dye:14:2"), getItem("minecraft:red_flower:5:1"));
        removeShapelessRecipe(getItem("minecraft:dye:7:2"), getItem("minecraft:red_flower:6:1"));
        removeShapelessRecipe(getItem("minecraft:dye:9:2"), getItem("minecraft:red_flower:7:1"));
        removeShapelessRecipe(getItem("minecraft:dye:7:2"), getItem("minecraft:red_flower:8:1"));

        removeShapelessRecipe(getItem("minecraft:dye:11:2"), getItem("minecraft:double_plant:0:1"));
        removeShapelessRecipe(getItem("minecraft:dye:13:2"), getItem("minecraft:double_plant:1:1"));
        removeShapelessRecipe(getItem("minecraft:dye:1:2"), getItem("minecraft:double_plant:4:1"));
        removeShapelessRecipe(getItem("minecraft:dye:9:2"), getItem("minecraft:double_plant:5:1"));

        removeShapelessRecipe(getItem("minecraft:iron_ingot:0:9"), getItem("minecraft:iron_block:0:1"));
        removeShapedRecipe(getItem("minecraft:iron_ingot:0:1"), "PPP", "PPP", "PPP", 'P', "nuggetIron");
        removeShapedRecipe(
            getItem("minecraft:iron_block:0:1"),
            "PPP",
            "PPP",
            "PPP",
            'P',
            getItem("minecraft:iron_ingot:0:1"));
        removeShapelessRecipe(getItem("minecraft:gold_ingot:0:9"), getItem("minecraft:gold_block:0:1"));
        removeShapedRecipe(
            getItem("minecraft:gold_ingot:0:1"),
            "PPP",
            "PPP",
            "PPP",
            'P',
            getItem("minecraft:gold_nugget:0:1"));
        removeShapedRecipe(getItem("minecraft:gold_ingot:0:1"), "PPP", "PPP", "PPP", 'P', "nuggetGold");
        removeShapedRecipe(
            getItem("minecraft:gold_block:0:1"),
            "PPP",
            "PPP",
            "PPP",
            'P',
            getItem("minecraft:gold_ingot:0:1"));

        removeShapedRecipe(
            getItem("minecraft:iron_shovel:0:1"),
            " P ",
            " S ",
            " S ",
            'P',
            "ingotIron",
            'S',
            "stickWood");

        removeShapedRecipe(
            getItem("minecraft:iron_pickaxe:0:1"),
            "PPP",
            " S ",
            " S ",
            'P',
            "ingotIron",
            'S',
            "stickWood");

        removeShapedRecipe(getItem("minecraft:iron_axe:0:1"), "PP ", "PS ", " S ", 'P', "ingotIron", 'S', "stickWood");

        removeShapedRecipe(getItem("minecraft:iron_hoe:0:1"), "PP ", " S ", " S ", 'P', "ingotIron", 'S', "stickWood");

        removeShapedRecipe(
            getItem("minecraft:iron_sword:0:1"),
            " P ",
            " P ",
            " S ",
            'P',
            "ingotIron",
            'S',
            "stickWood");
    }

    public void updateEFRRemoval() {
        removeShapelessRecipe(getItem("minecraft:dye:1:1"), getItem(Mods.efr.MODID, "rose", 0, 1));
        removeShapelessRecipe(getItem("minecraft:dye:1:1"), getItem(Mods.efr.MODID, "beetroot", 0, 1));
        removeShapelessRecipe(getItem("minecraft:dye:9:1"), getItem(Mods.efr.MODID, "pink_petals", 0, 1));
    }

    public void updateBOPRemoval() {
        removeShapelessRecipe(getItem("minecraft:dye:1:1"), getItem(Mods.bop.MODID, "flowers2", 8, 1));
        removeShapelessRecipe(getItem("minecraft:dye:5:1"), getItem(Mods.bop.MODID, "flowers", 8, 1));
        removeShapelessRecipe(getItem("minecraft:dye:5:1"), getItem(Mods.bop.MODID, "flowers2", 3, 1));
        removeShapelessRecipe(getItem("minecraft:dye:6:1"), getItem(Mods.bop.MODID, "flowers", 1, 1));
        removeShapelessRecipe(getItem("minecraft:dye:7:1"), getItem(Mods.bop.MODID, "flowers", 15, 1));
        removeShapelessRecipe(getItem("minecraft:dye:9:1"), getItem(Mods.bop.MODID, "flowers", 6, 1));
        removeShapelessRecipe(getItem("minecraft:dye:9:1"), getItem(Mods.bop.MODID, "flowers2", 0, 1));
        removeShapelessRecipe(getItem("minecraft:dye:10:1"), getItem(Mods.bop.MODID, "mushrooms", 3, 1));
        removeShapelessRecipe(getItem("minecraft:dye:11:1"), getItem(Mods.bop.MODID, "flowers2", 4, 1));
        removeShapelessRecipe(getItem("minecraft:dye:12:1"), getItem(Mods.bop.MODID, "flowers", 4, 1));
        removeShapelessRecipe(getItem("minecraft:dye:12:1"), getItem(Mods.bop.MODID, "flowers2", 7, 1));
        removeShapelessRecipe(getItem("minecraft:dye:13:1"), getItem(Mods.bop.MODID, "flowers", 7, 1));
        removeShapelessRecipe(getItem("minecraft:dye:14:1"), getItem(Mods.bop.MODID, "flowers", 5, 1));
        removeShapelessRecipe(getItem("minecraft:dye:14:1"), getItem(Mods.bop.MODID, "flowers2", 2, 1));
    }

    public void updateTCRemoval() {
        removeShapedRecipe(
            getItem("minecraft:iron_ingot:0:1"),
            "PPP",
            "PPP",
            "PPP",
            'P',
            getItem(Mods.tc.MODID, "ItemNugget", 0, 1));
    }

    public void removeRecipes() {
        RecipesHelper.removeRecipes();
        RecipesHelper.removeSmeltingRecipes();
        RecipesHelper.removeSpecialRecipes();
    }
}
