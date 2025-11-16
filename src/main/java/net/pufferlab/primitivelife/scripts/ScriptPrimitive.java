package net.pufferlab.primitivelife.scripts;

import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.recipes.KnappingType;

public class ScriptPrimitive implements IScript {

    public void run() {
        addCraftingRecipes();
        addKnappingRecipes();
        addPitKilnRecipes();
    }

    public void addCraftingRecipes() {
        addShapedRecipe(
            getItem(PrimitiveLife.MODID, "flint_axe", 0, 1),
            "I",
            "S",
            'I',
            getModItem("misc", "item", "flint_axe_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getItem(PrimitiveLife.MODID, "flint_pickaxe", 0, 1),
            "I",
            "S",
            'I',
            getModItem("misc", "item", "flint_pickaxe_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getItem(PrimitiveLife.MODID, "flint_shovel", 0, 1),
            "I",
            "S",
            'I',
            getModItem("misc", "item", "flint_shovel_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getItem(PrimitiveLife.MODID, "flint_knife", 0, 1),
            "I",
            "S",
            'I',
            getModItem("misc", "item", "flint_knife_blade", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getItem(PrimitiveLife.MODID, "thatch", 0, 1),
            "II",
            "II",
            'I',
            getModItem("misc", "item", "straw", 1));
        addShapedRecipe(
            getItem(PrimitiveLife.MODID, "thatch_roof", 0, 1),
            "I ",
            "II",
            'I',
            getModItem("misc", "item", "straw", 1));
        addShapedRecipe(
            getItem(PrimitiveLife.MODID, "firestarter", 0, 1),
            "SI",
            "I ",
            'S',
            getModItem("misc", "item", "straw", 1),
            'I',
            "stickWood");
        addShapedRecipe(
            getItem(PrimitiveLife.MODID, "firestarter", 0, 1),
            " I",
            "IS",
            'S',
            getModItem("misc", "item", "straw", 1),
            'I',
            "stickWood");
    }

    public void addKnappingRecipes() {
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("misc", "item", "clay_bucket", 1),
            "C   C",
            "C   C",
            "C   C",
            "C   C",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("misc", "item", "clay_brick", 2),
            "CC CC",
            "CC CC",
            "CC CC",
            "CC CC",
            "CC CC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("misc", "item", "clay_brick", 2),
            "CCCCC",
            "CCCCC",
            "     ",
            "CCCCC",
            "CCCCC");

        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_axe_head", 1),
            " C   ",
            "CCCC ",
            "CCCCC",
            "CCCC ",
            " C   ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_pickaxe_head", 2),
            " CCC ",
            "C   C",
            "     ",
            " CCC ",
            "C   C");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_pickaxe_head", 1),
            " CCC ",
            "C   C",
            "     ",
            "     ",
            "     ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_pickaxe_head", 1),
            "     ",
            "     ",
            "     ",
            " CCC ",
            "C   C");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_pickaxe_head", 1),
            "     ",
            " CCC ",
            "C   C",
            "     ",
            "     ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_shovel_head", 1),
            "  C  ",
            " CCC ",
            " CCC ",
            " CCC ",
            " CCC ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_shovel_head", 1),
            " C   ",
            "CCC  ",
            "CCC  ",
            "CCC  ",
            "CCC  ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_shovel_head", 1),
            "   C ",
            "  CCC",
            "  CCC",
            "  CCC",
            "  CCC");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_knife_blade", 2),
            "C   C",
            "CC CC",
            "CC CC",
            "CC CC",
            "CC CC");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_knife_blade", 2),
            " C C ",
            "CC CC",
            "CC CC",
            "CC CC",
            "CC CC");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("misc", "item", "flint_knife_blade", 2),
            "C  C ",
            "CC CC",
            "CC CC",
            "CC CC",
            "CC CC");
    }

    public void addPitKilnRecipes() {
        addPitKilnRecipe(
            getItem(PrimitiveLife.MODID, "ceramic_bucket", 0, 1),
            getModItem("misc", "item", "clay_bucket", 1));
        addPitKilnRecipe(getItem("minecraft:brick:0:1"), getModItem("misc", "item", "clay_brick", 1));
        addPitKilnRecipe(getItem("minecraft:hardened_clay:0:1"), getItem("minecraft:clay:0:1"));
    }
}
