package net.pufferlab.primal.scripts;

import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.KnappingType;

public class ScriptPrimitive implements IScript {

    public void run() {
        addOredicts();
        addCraftingRecipes();
        addCampfireRecipes();
        addChoppingLogRecipes();
        addKnappingRecipes();
        addPitKilnRecipes();
    }

    public void addOredicts() {
        addOreDict("straw", getModItem("misc", "item", "straw", 1));
        addOreDict("firewood", getModItem("misc", "item", "firewood", 1));
        addOreDict("charcoal", getItem("minecraft", "coal", 1, 1));
        addOreDict("coal", getItem("minecraft", "coal", 0, 1));
        addOreDict("ash", getModItem("misc", "item", "ash", 1));
        addOreDict("kindling", getModItem("misc", "item", "straw_kindling", 1));
    }

    public void addCraftingRecipes() {
        addShapelessRecipe(
            getModItem("misc", "item", "straw_kindling", 1),
            getModItem("misc", "item", "straw", 1),
            getModItem("misc", "item", "straw_cordage", 1),
            "stickWood");
        addShapedRecipe(getItem(Primal.MODID, "chopping_log", 0, 1), "SS", 'S', "logWood");
        addShapedRecipe(
            getItem(Primal.MODID, "flint_axe", 0, 1),
            "I",
            "S",
            'I',
            getModItem("misc", "item", "flint_axe_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getItem(Primal.MODID, "flint_pickaxe", 0, 1),
            "I",
            "S",
            'I',
            getModItem("misc", "item", "flint_pickaxe_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getItem(Primal.MODID, "flint_shovel", 0, 1),
            "I",
            "S",
            'I',
            getModItem("misc", "item", "flint_shovel_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getItem(Primal.MODID, "flint_knife", 0, 1),
            "I",
            "S",
            'I',
            getModItem("misc", "item", "flint_knife_blade", 1),
            'S',
            "stickWood");
        addShapedRecipe(getItem(Primal.MODID, "thatch", 0, 1), "II", "II", 'I', getModItem("misc", "item", "straw", 1));
        addShapedRecipe(
            getItem(Primal.MODID, "thatch_roof", 0, 1),
            "I ",
            "II",
            'I',
            getModItem("misc", "item", "straw", 1));
        addShapedRecipe(
            getItem(Primal.MODID, "firestarter", 0, 1),
            "SI",
            "I ",
            'S',
            getModItem("misc", "item", "straw", 1),
            'I',
            "stickWood");
        addShapedRecipe(
            getItem(Primal.MODID, "firestarter", 0, 1),
            " I",
            "IS",
            'S',
            getModItem("misc", "item", "straw", 1),
            'I',
            "stickWood");
    }

    public void addCampfireRecipes() {
        addCampfireRecipe(getItem("minecraft:cooked_beef:0:1"), getItem("minecraft:beef:0:1"));
        addCampfireRecipe(getItem("minecraft:cooked_chicken:0:1"), getItem("minecraft:chicken:0:1"));
        addCampfireRecipe(getItem("minecraft:cooked_porkchop:0:1"), getItem("minecraft:porkchop:0:1"));
        addCampfireRecipe(getItem("minecraft:baked_potato:0:1"), getItem("minecraft:potato:0:1"));
        addCampfireRecipe(getItem("minecraft:cooked_fished:0:1"), getItem("minecraft:fish:0:1"));
        addCampfireRecipe(getItem("minecraft:cooked_fished:1:1"), getItem("minecraft:fish:1:1"));
    }

    public void addChoppingLogRecipes() {
        addChoppingLogRecipe(getModItem("misc", "item", "firewood", 4), "logWood");
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
            getModItem("misc", "item", "clay_brick", 6),
            "CC CC",
            "     ",
            "CC CC",
            "     ",
            "CC CC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("misc", "item", "clay_brick", 6),
            "C C C",
            "C C C",
            "     ",
            "C C C",
            "C C C");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("misc", "item", "clay_flower_pot", 1),
            " CCC ",
            "C   C",
            "C   C",
            "C   C",
            " CCC ");

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
        addKnappingRecipe(
            KnappingType.straw,
            getModItem("misc", "item", "straw_cordage", 1),
            "CCCCC",
            "    C",
            "CCC C",
            "C   C",
            "CCCCC");
    }

    public void addPitKilnRecipes() {
        addPitKilnRecipe(getItem(Primal.MODID, "ceramic_bucket", 0, 1), getModItem("misc", "item", "clay_bucket", 1));
        addPitKilnRecipe(getItem("minecraft:brick:0:1"), getModItem("misc", "item", "clay_brick", 1));
        addPitKilnRecipe(getItem("minecraft:flower_pot:0:1"), getModItem("misc", "item", "clay_flower_pot", 1));
        addPitKilnRecipe(getItem("minecraft:hardened_clay:0:1"), getItem("minecraft:clay:0:1"));
    }
}
