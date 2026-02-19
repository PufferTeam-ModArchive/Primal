package net.pufferlab.primal.scripts;

import net.pufferlab.primal.*;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;

public class ScriptPrimitive implements IScript {

    public void runEarly() {
        addOredicts();

        runModCompatEarly();
    }

    public void runModCompatEarly() {
        if (Mods.efr.isLoaded()) {
            addEFROredicts();
        }
    }

    public void run() {
        addCraftingRecipes();
        addCampfireRecipes();
        addChoppingLogRecipes();
        addQuernRecipes();
        addTanningRecipes();
        addKnappingRecipes();
        addPitKilnRecipes();
        addBarrelRecipes();

        runModCompat();
    }

    public void runModCompat() {
        if (Mods.efr.isLoaded()) {
            addEFRCraftingRecipes();
            addEFRCampfireRecipes();
            addEFRPitKilnRecipes();
            addEFRBarrelRecipes();
        }
    }

    public void addOredicts() {
        addOreDict("straw", getModItem("straw", 1));
        addOreDict("firewood", getModItem("firewood", 1));
        addOreDict("charcoal", getItem("minecraft", "coal", 1, 1));
        addOreDict("coal", getItem("minecraft", "coal", 0, 1));
        addOreDict("coalAny", getItem("minecraft", "coal", 0, 1));
        addOreDict("coalAny", getItem("minecraft", "coal", 1, 1));
        addOreDict("ash", getModItem("ash", 1));
        addOreDict("kindling", getModItem("straw_kindling", 1));
        addOreDict("shell", getModItem("scallop", 1));
        addOreDict("shell", getModItem("conch", 1));
        addOreDict("limeAny", getModItem("scallop", 1));
        addOreDict("limeAny", getModItem("conch", 1));
        addOreDict("mold", getItem(Primal.MODID, "mold", wildcard, 1));
        addOreDict("barkWood", getItem(Primal.MODID, "bark", wildcard, 1));
        addOreDict("barkWoodTannin", getItem(Primal.MODID, "bark", Utils.getIndex(Constants.woodTypes, "oak"), 1));
        addOreDict("barkWoodTannin", getItem(Primal.MODID, "bark", Utils.getIndex(Constants.woodTypes, "acacia"), 1));
        addOreDict("itemLarge", getModItem("clay_large_vessel", 1));
        addOreDict("itemLarge", getModItem("clay_crucible", 1));
        addOreDict("itemLarge", getModItem("large_vessel", 1));
        addOreDict("itemLarge", getModItem("crucible", 1));
        addOreDict("itemLarge", getModItem("barrel", 1));
        addOreDict("itemContainer", getModItem("large_vessel", 1));
        addOreDict("itemContainer", getModItem("crucible", 1));
        addOreDict("itemContainer", getModItem("barrel", 1));
        addOreDict("toolKnife", getItem(Primal.MODID, "flint_knife", wildcard, 1));
        addOreDict("toolHandstone", getItem(Primal.MODID, "handstone", wildcard, 1));
        addOreDict("blockColoredWool", getItem("minecraft:wool:*:1"));
        addOreDict("blockColoredGlass", getItem("minecraft:stained_glass:*:1"));
        addOreDict("blockColoredGlass", getItem("minecraft:glass:*:1"));
        addOreDict("blockColoredGlassPane", getItem("minecraft:stained_glass_pane:*:1"));
        addOreDict("blockColoredGlassPane", getItem("minecraft:glass_pane:*:1"));
        addOreDict("blockColoredHardenedClay", getItem("minecraft:stained_hardened_clay:*:1"));
        addOreDict("blockColoredHardenedClay", getItem("minecraft:hardened_clay:*:1"));
        addOreDict("blockColoredCarpet", getItem("minecraft:carpet:*:1"));
    }

    public void addEFROredicts() {
        addOreDict("blockColoredConcrete", getItem(Mods.efr.MODID, "concrete", wildcard, 1));
        addOreDict("blockColoredConcretePowder", getItem(Mods.efr.MODID, "concrete_powder", wildcard, 1));
        addOreDict("blockColoredBanner", getItem(Mods.efr.MODID, "banner", wildcard, 1));
        for (String color : Constants.colorTypes) {
            addOreDict("blockColoredGlazedTerracotta", getModItem("glazed_terracotta", color, 1));
            addOreDict("blockColoredBed", getModItem("bed", color, 1));
        }
        addOreDict("rabbitHide", getItem(Mods.efr.MODID, "rabbit_hide", wildcard, 1));
    }

    public void addCraftingRecipes() {
        addShapedRecipe(getModItem("glowstone_crystal", 1), " S ", "SIS", " S ", 'I', "coalAny", 'S', "dustGlowstone");
        if (Config.torchRebalance.getBoolean()) {
            addShapedRecipe(
                getItem("minecraft:torch:0:4"),
                "I",
                "S",
                'I',
                getModItem("glowstone_crystal", 1),
                'S',
                "stickWood");
            addShapedRecipe(getModItem("unlit_torch", 4), "I", "S", 'I', "coalAny", 'S', "stickWood");
        }
        addShapedRecipe(getModItem("unlit_torch", 2), "I", "S", 'I', "straw", 'S', "stickWood");

        addShapedRecipe(getModItem("chopping_log", 1), "SS", 'S', "logWood");
        addShapedRecipe(getModItem("barrel", 1), "P P", "P P", "PPP", 'P', "plankWood");
        addShapedRecipe(getModItem("oven", 1), "SSS", "SIS", "SSS", 'I', getModItem("ash", 1), 'S', "ingotBrick");
        addShapedRecipe(getModItem("brick_chimney", 1), "S S", "S S", "S S", 'S', "ingotBrick");
        addShapedRecipe(getModItem("flint_axe", 1), "I", "S", 'I', getModItem("flint_axe_head", 1), 'S', "stickWood");
        addShapedRecipe(
            getModItem("flint_pickaxe", 1),
            "I",
            "S",
            'I',
            getModItem("flint_pickaxe_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getModItem("flint_shovel", 1),
            "I",
            "S",
            'I',
            getModItem("flint_shovel_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getModItem("flint_knife", 1),
            "I",
            "S",
            'I',
            getModItem("flint_knife_blade", 1),
            'S',
            "stickWood");
        addShapedRecipe(getModItem("flint_hoe", 1), "I", "S", 'I', getModItem("flint_hoe_head", 1), 'S', "stickWood");
        addShapedRecipe(getItem(Primal.MODID, "thatch", 0, 1), "II", "II", 'I', getModItem("straw", 1));
        addShapedRecipe(getItem(Primal.MODID, "thatch_roof", 0, 1), "I ", "II", 'I', getModItem("straw", 1));
        addShapedRecipe(getModItem("firestarter", 1), "SI", "I ", 'S', getModItem("straw", 1), 'I', "stickWood");
        addShapedRecipe(getModItem("firestarter", 1), " I", "IS", 'S', getModItem("straw", 1), 'I', "stickWood");
    }

    public void addEFRCraftingRecipes() {

    }

    public void addCampfireRecipes() {
        addCampfireRecipe(getModItem("lit_torch", 1), getModItem("unlit_torch", 1));
        addCampfireRecipe(getItem("minecraft:bread:0:1"), getModItem("wheat_dough", 1));
        addCampfireRecipe(getItem("minecraft:cooked_beef:0:1"), getItem("minecraft:beef:0:1"));
        addCampfireRecipe(getItem("minecraft:cooked_chicken:0:1"), getItem("minecraft:chicken:0:1"));
        addCampfireRecipe(getItem("minecraft:cooked_porkchop:0:1"), getItem("minecraft:porkchop:0:1"));
        addCampfireRecipe(getItem("minecraft:baked_potato:0:1"), getItem("minecraft:potato:0:1"));
        addCampfireRecipe(getItem("minecraft:cooked_fished:0:1"), getItem("minecraft:fish:0:1"));
        addCampfireRecipe(getItem("minecraft:cooked_fished:1:1"), getItem("minecraft:fish:1:1"));
    }

    public void addEFRCampfireRecipes() {
        addCampfireRecipe(getItem(Mods.efr.MODID, "mutton_cooked", 0, 1), getItem(Mods.efr.MODID, "mutton_raw", 0, 1));
        addCampfireRecipe(getItem(Mods.efr.MODID, "rabbit_cooked", 0, 1), getItem(Mods.efr.MODID, "rabbit_raw", 0, 1));
    }

    public void addChoppingLogRecipes() {
        addChoppingLogRecipe(getModItem("firewood", 2), "logWood");
    }

    public void addQuernRecipes() {
        addQuernRecipe(getModItem("wheat_flour", 1), getItem("minecraft:wheat:0:1"));
        addQuernRecipe(getModItem("lime_powder", 1), "limeAny");
    }

    public void addTanningRecipes() {
        addTanningRecipe(getModItem("scraped_hide", 1), getModItem("soaked_hide", 1));
    }

    public void addKnappingRecipes() {
        addKnappingRecipe(KnappingType.clay, getModItem("clay_bucket", 1), "C   C", "C   C", "C   C", "C   C", "CCCCC");
        addKnappingRecipe(KnappingType.clay, getModItem("clay_brick", 6), "CC CC", "     ", "CC CC", "     ", "CC CC");
        addKnappingRecipe(KnappingType.clay, getModItem("clay_brick", 6), "C C C", "C C C", "     ", "C C C", "C C C");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_flower_pot", 1),
            " CCC ",
            "C   C",
            "C   C",
            "C   C",
            " CCC ");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_large_vessel", 1),
            "CCCCC",
            "C   C",
            "C   C",
            "C   C",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_crucible", 1),
            "CC CC",
            "C   C",
            "C   C",
            "C   C",
            "CCCCC");

        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_axe_head", 1),
            " C   ",
            "CCCC ",
            "CCCCC",
            "CCCC ",
            " C   ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_pickaxe_head", 2),
            " CCC ",
            "C   C",
            "     ",
            " CCC ",
            "C   C");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_pickaxe_head", 1),
            " CCC ",
            "C   C",
            "     ",
            "     ",
            "     ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_pickaxe_head", 1),
            "     ",
            "     ",
            "     ",
            " CCC ",
            "C   C");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_pickaxe_head", 1),
            "     ",
            " CCC ",
            "C   C",
            "     ",
            "     ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_shovel_head", 1),
            "  C  ",
            " CCC ",
            " CCC ",
            " CCC ",
            " CCC ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_shovel_head", 1),
            " C   ",
            "CCC  ",
            "CCC  ",
            "CCC  ",
            "CCC  ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_shovel_head", 1),
            "   C ",
            "  CCC",
            "  CCC",
            "  CCC",
            "  CCC");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_knife_blade", 2),
            "C   C",
            "CC CC",
            "CC CC",
            "CC CC",
            "CC CC");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_knife_blade", 2),
            " C C ",
            "CC CC",
            "CC CC",
            "CC CC",
            "CC CC");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_knife_blade", 2),
            "C  C ",
            "CC CC",
            "CC CC",
            "CC CC",
            "CC CC");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_hoe_head", 1),
            "CCCCC",
            "   CC",
            "     ",
            "     ",
            "     ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_hoe_head", 1),
            "     ",
            "CCCCC",
            "   CC",
            "     ",
            "     ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_hoe_head", 1),
            "     ",
            "     ",
            "CCCCC",
            "   CC",
            "     ");
        addKnappingRecipe(
            KnappingType.flint,
            getModItem("flint_hoe_head", 1),
            "     ",
            "     ",
            "     ",
            "CCCCC",
            "   CC");
        addKnappingRecipe(
            KnappingType.straw,
            getModItem("straw_cordage", 1),
            "CCCCC",
            "    C",
            "CCC C",
            "C   C",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.straw,
            getModItem("straw_kindling", 1),
            " CCC ",
            "CCCCC",
            "CCCCC",
            "CCCCC",
            " CCC ");
        addKnappingRecipe(
            KnappingType.leather,
            getItem("minecraft:leather_helmet:0:1"),
            " CCC ",
            "CCCCC",
            "CCCCC",
            "C   C",
            "     ");
        addKnappingRecipe(
            KnappingType.leather,
            getItem("minecraft:leather_helmet:0:1"),
            "     ",
            " CCC ",
            "CCCCC",
            "CCCCC",
            "C   C");
        addKnappingRecipe(
            KnappingType.leather,
            getItem("minecraft:leather_chestplate:0:1"),
            "C   C",
            "CCCCC",
            "CCCCC",
            " CCC ",
            " CCC ");
        addKnappingRecipe(
            KnappingType.leather,
            getItem("minecraft:leather_leggings:0:1"),
            "CCCCC",
            "CC CC",
            "C   C",
            "C   C",
            "C   C");
        addKnappingRecipe(
            KnappingType.leather,
            getItem("minecraft:leather_boots:0:1"),
            "C   C",
            "C   C",
            "CC CC",
            "     ",
            "     ");
        addKnappingRecipe(
            KnappingType.leather,
            getItem("minecraft:leather_boots:0:1"),
            "     ",
            "C   C",
            "C   C",
            "CC CC",
            "     ");
        addKnappingRecipe(
            KnappingType.leather,
            getItem("minecraft:leather_boots:0:1"),
            "     ",
            "     ",
            "C   C",
            "C   C",
            "CC CC");
    }

    public void addPitKilnRecipes() {
        addPitKilnRecipe(getModItem("empty_ceramic_bucket", 1), getModItem("clay_bucket", 1));
        addPitKilnRecipe(getItem("minecraft:brick:0:1"), getModItem("clay_brick", 1));
        addPitKilnRecipe(getItem("minecraft:flower_pot:0:1"), getModItem("clay_flower_pot", 1));
        addPitKilnRecipe(getItem("minecraft:hardened_clay:0:1"), getItem("minecraft:clay:0:1"));
        addPitKilnRecipe(getModItem("large_vessel", 1), getModItem("clay_large_vessel", 1));
        addPitKilnRecipe(getModItem("crucible", 1), getModItem("clay_crucible", 1));
    }

    public void addEFRPitKilnRecipes() {
        for (String color : Constants.colorTypes) {
            addPitKilnRecipe(getModItem("glazed_terracotta", color, 1), getModItem("hardened_clay", color, 1));
        }
    }

    public void addBarrelRecipes() {
        addBarrelRecipe(getModItem("wheat_dough", 1), null, getModItem("wheat_flour", 1), getFluid("water", 100), 100);
        addBarrelRecipe(null, getFluid("limewater", 500), getModItem("lime_powder", 1), getFluid("water", 500), 100);
        addBarrelRecipe(getModItem("soaked_hide", 1), null, getModItem("hide", 1), getFluid("limewater", 500), 1200);
        addBarrelRecipe(
            getItem("minecraft", "leather", 0, 1),
            null,
            getModItem("scraped_hide", 1),
            getFluid("tannin", 500),
            1200);
        addBarrelRecipe(null, getFluid("tannin", 1000), "barkWoodTannin", getFluid("water", 1000), 2400);
        for (String color : Constants.colorTypes) {
            addBarrelRecipe(
                null,
                getFluid(color, 1000),
                ItemUtils.getOreDictionaryName("dye", color),
                getFluid("water", 1000),
                100);
        }

        for (String color : Constants.colorTypes) {
            for (String item : Constants.colorItems) {
                addBarrelRecipe(
                    getModItem(item, color, 1),
                    null,
                    ItemUtils.getOreDictionaryName("blockColored", item),
                    getFluid(color, 125),
                    100);
            }
        }
    }

    public void addEFRBarrelRecipes() {
        for (String color : Constants.colorTypes) {
            for (String item : Constants.colorItemsEFR) {
                addBarrelRecipe(
                    getModItem(item, color, 1),
                    null,
                    ItemUtils.getOreDictionaryName("blockColored", item),
                    getFluid(color, 125),
                    100);
            }
        }
    }
}
