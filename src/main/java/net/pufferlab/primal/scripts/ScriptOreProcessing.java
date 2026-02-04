package net.pufferlab.primal.scripts;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.recipes.AnvilAction;
import net.pufferlab.primal.recipes.AnvilOrder;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.utils.MetalType;
import net.pufferlab.primal.utils.OreType;

public class ScriptOreProcessing implements IScript {

    public void run() {
        addOreDicts();
        addCraftingRecipes();
        addToolRecipes();
        addAnvilRecipes();
        addMeltingRecipes();
        addCastingRecipes();
        addAlloyingRecipes();
        addKnappingRecipes();
        addPitKilnRecipes();
    }

    public void addOreDicts() {
        addOreDict("axeCopper", getModItem("copper_axe", 1));
        addOreDict("pickaxeCopper", getModItem("copper_pickaxe", 1));
        addOreDict("shovelCopper", getModItem("copper_shovel", 1));
        addOreDict("swordCopper", getModItem("copper_sword", 1));
        addOreDict("knifeCopper", getModItem("copper_knife", 1));
        addOreDict("hoeCopper", getModItem("copper_hoe", 1));
        addOreDict("hammerCopper", getModItem("copper_hammer", 1));
        addOreDict("axeBronze", getModItem("bronze_axe", 1));
        addOreDict("pickaxeBronze", getModItem("bronze_pickaxe", 1));
        addOreDict("shovelBronze", getModItem("bronze_shovel", 1));
        addOreDict("swordBronze", getModItem("bronze_sword", 1));
        addOreDict("knifeBronze", getModItem("bronze_knife", 1));
        addOreDict("hoeBronze", getModItem("bronze_hoe", 1));
        addOreDict("hammerBronze", getModItem("bronze_hammer", 1));
        addOreDict("axeIron", getItem("minecraft", "iron_axe", wildcard, 1));
        addOreDict("pickaxeIron", getItem("minecraft", "iron_pickaxe", wildcard, 1));
        addOreDict("shovelIron", getItem("minecraft", "iron_shovel", wildcard, 1));
        addOreDict("swordIron", getItem("minecraft", "iron_sword", wildcard, 1));
        addOreDict("hoeIron", getItem("minecraft", "iron_hoe", wildcard, 1));
        addOreDict("knifeIron", getModItem("iron_knife", 1));
        addOreDict("hammerIron", getModItem("iron_hammer", 1));
    }

    public void addCraftingRecipes() {
        for (MetalType type : Constants.metalTypes) {
            String name = type.name;
            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("block", name)),
                "PPP",
                "PPP",
                "PPP",
                'P',
                getOreDictItem(getOreDictionaryName("ingot", name)));
            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("ingot", name), 9),
                "P",
                'P',
                getOreDictItem(getOreDictionaryName("block", name)));

            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("ingot", name)),
                "PPP",
                "PPP",
                "PPP",
                'P',
                getOreDictItem(getOreDictionaryName("nugget", name)));
            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("nugget", name), 9),
                "P",
                'P',
                getOreDictItem(getOreDictionaryName("ingot", name)));
        }
    }

    public void addToolRecipes() {
        for (MetalType type : Constants.toolMetalTypes) {
            String name = type.name;

            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("pickaxe", name)),
                "I",
                "S",
                'I',
                getOreDictItem(getOreDictionaryName("pickaxe_head", name)),
                'S',
                "stickWood");
            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("axe", name)),
                "I",
                "S",
                'I',
                getOreDictItem(getOreDictionaryName("axe_head", name)),
                'S',
                "stickWood");
            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("shovel", name)),
                "I",
                "S",
                'I',
                getOreDictItem(getOreDictionaryName("shovel_head", name)),
                'S',
                "stickWood");
            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("sword", name)),
                "I",
                "S",
                'I',
                getOreDictItem(getOreDictionaryName("sword_blade", name)),
                'S',
                "stickWood");
            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("knife", name)),
                "I",
                "S",
                'I',
                getOreDictItem(getOreDictionaryName("knife_blade", name)),
                'S',
                "stickWood");
            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("hoe", name)),
                "I",
                "S",
                'I',
                getOreDictItem(getOreDictionaryName("hoe_head", name)),
                'S',
                "stickWood");
            addShapedRecipe(
                getOreDictItem(getOreDictionaryName("hammer", name)),
                "I",
                "S",
                'I',
                getOreDictItem(getOreDictionaryName("hammer_head", name)),
                'S',
                "stickWood");
        }
    }

    public void addAnvilRecipes() {
        for (MetalType type : Constants.toolMetalTypes) {
            String name = type.name;
            addAnvilRecipe(
                getOreDictItem(getOreDictionaryName("pickaxe_head", name)),
                getOreDictionaryName("ingot", name),
                AnvilAction.punch,
                AnvilOrder.last,
                AnvilAction.bend,
                AnvilOrder.notLast,
                AnvilAction.draw,
                AnvilOrder.notLast);
            addAnvilRecipe(
                getOreDictItem(getOreDictionaryName("axe_head", name)),
                getOreDictionaryName("ingot", name),
                AnvilAction.punch,
                AnvilOrder.last,
                AnvilAction.hitMedium,
                AnvilOrder.secondLast,
                AnvilAction.upset,
                AnvilOrder.thirdLast);
            addAnvilRecipe(
                getOreDictItem(getOreDictionaryName("sword_blade", name)),
                getOreDictionaryName("ingot", name),
                AnvilAction.hitMedium,
                AnvilOrder.last,
                AnvilAction.bend,
                AnvilOrder.secondLast,
                AnvilAction.bend,
                AnvilOrder.thirdLast);
            addAnvilRecipe(
                getOreDictItem(getOreDictionaryName("hammer_head", name)),
                getOreDictionaryName("ingot", name),
                AnvilAction.punch,
                AnvilOrder.last,
                AnvilAction.shrink,
                AnvilOrder.notLast,
                AnvilAction.hitMedium,
                AnvilOrder.notLast);
        }
    }

    public void addMeltingRecipes() {
        for (MetalType type : Constants.metalTypes) {
            String name = type.name;

            addMeltingRecipe(type.ingotFluid, getOreDictionaryName("ingot", name));
            addMeltingRecipe(type.nuggetFluid, getOreDictionaryName("nugget", name));
        }
        for (OreType type : Constants.oreTypes) {
            String name = type.name;
            addMeltingRecipe(type.metalType.oreFluid, getOreDictionaryName("medium_ore", name));
            addMeltingRecipe(type.metalType.smallOreFluid, getOreDictionaryName("small_ore", name));
        }
        for (MetalType type : Constants.toolMetalTypes) {
            String name = type.name;

            addMeltingRecipe(type.tripleToolFluid, getOreDictionaryName("pickaxe_head", name));
            addMeltingRecipe(type.tripleToolFluid, getOreDictionaryName("axe_head", name));
            addMeltingRecipe(type.ingotFluid, getOreDictionaryName("shovel_head", name));
            addMeltingRecipe(type.doubleToolFluid, getOreDictionaryName("sword_blade", name));
            addMeltingRecipe(type.ingotFluid, getOreDictionaryName("knife_blade", name));
            addMeltingRecipe(type.doubleToolFluid, getOreDictionaryName("hoe_head", name));
            addMeltingRecipe(type.ingotFluid, getOreDictionaryName("hammer_head", name));
        }
    }

    public void addCastingRecipes() {
        ItemStack ingotMold = getModItem("ingot_mold", 1);
        for (MetalType type : Constants.metalTypes) {
            String name = type.name;

            addCastingRecipe(ingotMold, getOreDictItem(getOreDictionaryName("ingot", name)), type.ingotFluid);

        }
        ItemStack pickaxeMold = getModItem("pickaxe_mold", 1);
        ItemStack axeMold = getModItem("axe_mold", 1);
        ItemStack shovelMold = getModItem("shovel_mold", 1);
        ItemStack swordMold = getModItem("sword_mold", 1);
        ItemStack knifeMold = getModItem("knife_mold", 1);
        ItemStack hoeMold = getModItem("hoe_mold", 1);
        ItemStack hammerMold = getModItem("hammer_mold", 1);
        for (MetalType type : Constants.toolMetalTypes) {
            String name = type.name;
            addCastingRecipe(
                pickaxeMold,
                getOreDictItem(getOreDictionaryName("pickaxe_head", name)),
                type.tripleToolFluid);
            addCastingRecipe(axeMold, getOreDictItem(getOreDictionaryName("axe_head", name)), type.tripleToolFluid);
            addCastingRecipe(shovelMold, getOreDictItem(getOreDictionaryName("shovel_head", name)), type.ingotFluid);
            addCastingRecipe(
                swordMold,
                getOreDictItem(getOreDictionaryName("sword_blade", name)),
                type.doubleToolFluid);
            addCastingRecipe(knifeMold, getOreDictItem(getOreDictionaryName("knife_blade", name)), type.ingotFluid);
            addCastingRecipe(hoeMold, getOreDictItem(getOreDictionaryName("hoe_head", name)), type.doubleToolFluid);
            addCastingRecipe(hammerMold, getOreDictItem(getOreDictionaryName("hammer_head", name)), type.ingotFluid);
        }
    }

    public void addAlloyingRecipes() {
        addAlloyingRecipe(
            Constants.bronze.fluidStack,
            "70-80",
            Constants.copper.fluidStack,
            "10-20",
            Constants.tin.fluidStack);
    }

    public void addPitKilnRecipes() {
        for (String type : Constants.moldItems) {
            addPitKilnRecipe(getModItem(type, 1), getModItem("clay_" + type, 1));
        }
    }

    public void addKnappingRecipes() {
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_ingot_mold", 1),
            "CCCCC",
            "C   C",
            "C   C",
            "CCCCC",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_ingot_mold", 1),
            "CCCCC",
            "CCCCC",
            "C   C",
            "C   C",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_ingot_mold", 1),
            "CCCCC",
            "C  CC",
            "C  CC",
            "C  CC",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_ingot_mold", 1),
            "CCCCC",
            "CC  C",
            "CC  C",
            "CC  C",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_pickaxe_mold", 1),
            "CCCCC",
            "C   C",
            " CCC ",
            "CCCCC",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_pickaxe_mold", 1),
            "CCCCC",
            "CCCCC",
            "C   C",
            " CCC ",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_axe_mold", 1),
            "C CCC",
            "    C",
            "     ",
            "    C",
            "C CCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_shovel_mold", 1),
            "CC CC",
            "C   C",
            "C   C",
            "C   C",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_sword_mold", 1),
            "CCC  ",
            "CC   ",
            "C   C",
            "C  CC",
            " CCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_hoe_mold", 1),
            "CCCCC",
            "     ",
            "  CCC",
            "CCCCC",
            "CCCCC");
        addKnappingRecipe(
            KnappingType.clay,
            getModItem("clay_hoe_mold", 1),
            "CCCCC",
            "CCCCC",
            "     ",
            "  CCC",
            "CCCCC");
    }
}
