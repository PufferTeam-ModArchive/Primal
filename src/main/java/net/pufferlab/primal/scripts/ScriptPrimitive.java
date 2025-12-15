package net.pufferlab.primal.scripts;

import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.KnappingType;

public class ScriptPrimitive implements IScript {

    public void run() {
        addOredicts();
        addCraftingRecipes();
        addCampfireRecipes();
        addChoppingLogRecipes();
        addScrapingRecipes();
        addKnappingRecipes();
        addPitKilnRecipes();
        addBarrelRecipes();

        runModCompat();
    }

    public void runModCompat() {
        if (Primal.EFRLoaded) {
            addEFROredicts();
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
        addOreDict("ash", getModItem("ash", 1));
        addOreDict("kindling", getModItem("straw_kindling", 1));
        addOreDict("rock", getItem(Primal.MODID + ":rock:*:1"));
        addOreDict("itemLarge", getModItem("clay_large_vessel", 1));
        addOreDict("itemContainer", getItem(Primal.MODID, "large_vessel", 0, 1));
        addOreDict("itemContainer", getItem(Primal.MODID, "barrel", 0, 1));
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
        addOreDict("blockColoredConcrete", getItem("etfuturum:concrete:*:1"));
        addOreDict("blockColoredConcretePowder", getItem("etfuturum:concrete_powder:*:1"));
        addOreDict("blockColoredBanner", getItem("etfuturum:banner:*:1"));
        for (String color : Constants.colorTypes) {
            addOreDict("blockColoredGlazedTerracotta", getModItem("glazed_terracotta", color, 1));
            addOreDict("blockColoredBed", getModItem("bed", color, 1));
        }
        addOreDict("rabbitHide", getItem("etfuturum:rabbit_hide:*:1"));
    }

    public void addCraftingRecipes() {
        addShapelessRecipe(
            getModItem("straw_kindling", 1),
            getModItem("straw", 1),
            getModItem("straw_cordage", 1),
            "stickWood");
        addShapedRecipe(getItem(Primal.MODID, "chopping_log", 0, 1), "SS", 'S', "logWood");
        addShapedRecipe(getItem(Primal.MODID, "barrel", 0, 1), "P P", "P P", "PPP", 'P', "plankWood");
        addShapedRecipe(
            getItem(Primal.MODID, "flint_axe", 0, 1),
            "I",
            "S",
            'I',
            getModItem("flint_axe_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getItem(Primal.MODID, "flint_pickaxe", 0, 1),
            "I",
            "S",
            'I',
            getModItem("flint_pickaxe_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getItem(Primal.MODID, "flint_shovel", 0, 1),
            "I",
            "S",
            'I',
            getModItem("flint_shovel_head", 1),
            'S',
            "stickWood");
        addShapedRecipe(
            getItem(Primal.MODID, "flint_knife", 0, 1),
            "I",
            "S",
            'I',
            getModItem("flint_knife_blade", 1),
            'S',
            "stickWood");
        addShapedRecipe(getItem(Primal.MODID, "thatch", 0, 1), "II", "II", 'I', getModItem("straw", 1));
        addShapedRecipe(getItem(Primal.MODID, "thatch_roof", 0, 1), "I ", "II", 'I', getModItem("straw", 1));
        addShapedRecipe(
            getItem(Primal.MODID, "firestarter", 0, 1),
            "SI",
            "I ",
            'S',
            getModItem("straw", 1),
            'I',
            "stickWood");
        addShapedRecipe(
            getItem(Primal.MODID, "firestarter", 0, 1),
            " I",
            "IS",
            'S',
            getModItem("straw", 1),
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

    public void addEFRCampfireRecipes() {
        addCampfireRecipe(getItem("etfuturum:mutton_cooked:0:1"), getItem("etfuturum:mutton_raw:0:1"));
        addCampfireRecipe(getItem("etfuturum:rabbit_cooked:0:1"), getItem("etfuturum:rabbit_raw:0:1"));
    }

    public void addChoppingLogRecipes() {
        addChoppingLogRecipe(getModItem("firewood", 2), "logWood");
    }

    public void addScrapingRecipes() {
        addScrapingRecipe(getModItem("scraped_hide", 1), getModItem("soaked_hide", 1));
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
            KnappingType.straw,
            getModItem("straw_cordage", 1),
            "CCCCC",
            "    C",
            "CCC C",
            "C   C",
            "CCCCC");
    }

    public void addPitKilnRecipes() {
        addPitKilnRecipe(getItem(Primal.MODID, "ceramic_bucket", 0, 1), getModItem("clay_bucket", 1));
        addPitKilnRecipe(getItem("minecraft:brick:0:1"), getModItem("clay_brick", 1));
        addPitKilnRecipe(getItem("minecraft:flower_pot:0:1"), getModItem("clay_flower_pot", 1));
        addPitKilnRecipe(getItem("minecraft:hardened_clay:0:1"), getItem("minecraft:clay:0:1"));
        addPitKilnRecipe(getItem(Primal.MODID, "large_vessel", 0, 1), getModItem("clay_large_vessel", 1));
    }

    public void addEFRPitKilnRecipes() {
        for (String color : Constants.colorTypes) {
            addPitKilnRecipe(getModItem("glazed_terracotta", color, 1), getModItem("hardened_clay", color, 1));
        }
    }

    public void addBarrelRecipes() {
        addBarrelRecipe(null, getFluid("limewater", 500), getModItem("lime", 1), getFluid("water", 500), 100);
        addBarrelRecipe(getModItem("soaked_hide", 1), null, getModItem("hide", 1), getFluid("limewater", 500), 1200);
        addBarrelRecipe(
            getItem("minecraft", "leather", 0, 1),
            null,
            getModItem("scraped_hide", 1),
            getFluid("tannin", 500),
            1200);
        addBarrelRecipe(null, getFluid("tannin", 1000), "logWood", getFluid("water", 1000), 2400);
        for (String color : Constants.colorTypes) {
            addBarrelRecipe(
                null,
                getFluid(color, 1000),
                Utils.getOreDictionaryName("dye", color),
                getFluid("water", 1000),
                100);
        }

        for (String color : Constants.colorTypes) {
            for (String item : Constants.colorItems) {
                addBarrelRecipe(
                    getModItem(item, color, 1),
                    null,
                    Utils.getOreDictionaryName("blockColored", item),
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
                    Utils.getOreDictionaryName("blockColored", item),
                    getFluid(color, 125),
                    100);
            }
        }
    }
}
