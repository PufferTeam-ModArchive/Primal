package net.pufferlab.primal.scripts;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.utils.MetalType;

public class ScriptOreProcessing implements IScript {

    public void run() {
        addOreDicts();
        addToolRecipes();
        addMeltingRecipes();
        addCastingRecipes();
        addAlloyingRecipes();
        addKnappingRecipes();
        addPitKilnRecipes();
    }

    public void addOreDicts() {
        addOreDict("axeBronze", getModItem("bronze_axe", 1));
        addOreDict("pickaxeBronze", getModItem("bronze_pickaxe", 1));
        addOreDict("shovelBronze", getModItem("bronze_shovel", 1));
        addOreDict("swordBronze", getModItem("bronze_sword", 1));
        addOreDict("hoeBronze", getModItem("bronze_hoe", 1));
    }

    public void addToolRecipes() {
        for (MetalType type : Constants.toolMetalTypes) {
            String name = type.name;

            addShapedRecipe(
                Utils.getOreDictItem(Utils.getOreDictionaryName("pickaxe", name)),
                "I",
                "S",
                'I',
                Utils.getOreDictItem(Utils.getOreDictionaryName("pickaxe_head", name)),
                'S',
                "stickWood");
            addShapedRecipe(
                Utils.getOreDictItem(Utils.getOreDictionaryName("axe", name)),
                "I",
                "S",
                'I',
                Utils.getOreDictItem(Utils.getOreDictionaryName("axe_head", name)),
                'S',
                "stickWood");
            addShapedRecipe(
                Utils.getOreDictItem(Utils.getOreDictionaryName("shovel", name)),
                "I",
                "S",
                'I',
                Utils.getOreDictItem(Utils.getOreDictionaryName("shovel_head", name)),
                'S',
                "stickWood");
            addShapedRecipe(
                Utils.getOreDictItem(Utils.getOreDictionaryName("sword", name)),
                "I",
                "S",
                'I',
                Utils.getOreDictItem(Utils.getOreDictionaryName("sword_blade", name)),
                'S',
                "stickWood");
            addShapedRecipe(
                Utils.getOreDictItem(Utils.getOreDictionaryName("hoe", name)),
                "I",
                "S",
                'I',
                Utils.getOreDictItem(Utils.getOreDictionaryName("hoe_head", name)),
                'S',
                "stickWood");
        }
    }

    public void addMeltingRecipes() {
        for (MetalType type : Constants.metalTypes) {
            String name = type.name;

            addMeltingRecipe(type.ingotFluid, Utils.getOreDictionaryName("ingot", name));
            addMeltingRecipe(type.nuggetFluid, Utils.getOreDictionaryName("nugget", name));
        }
        for (MetalType type : Constants.toolMetalTypes) {
            String name = type.name;

            addMeltingRecipe(type.tripleIngotFluid, Utils.getOreDictionaryName("pickaxe_head", name));
            addMeltingRecipe(type.tripleIngotFluid, Utils.getOreDictionaryName("axe_head", name));
            addMeltingRecipe(type.ingotFluid, Utils.getOreDictionaryName("shovel_head", name));
            addMeltingRecipe(type.doubleIngotFluid, Utils.getOreDictionaryName("sword_blade", name));
            addMeltingRecipe(type.doubleIngotFluid, Utils.getOreDictionaryName("hoe_head", name));
        }
    }

    public void addCastingRecipes() {
        ItemStack ingotMold = getModItem("ingot_mold", 1);
        for (MetalType type : Constants.metalTypes) {
            String name = type.name;

            addCastingRecipe(
                ingotMold,
                Utils.getOreDictItem(Utils.getOreDictionaryName("ingot", name)),
                type.ingotFluid);

        }
        ItemStack pickaxeMold = getModItem("pickaxe_mold", 1);
        ItemStack axeMold = getModItem("axe_mold", 1);
        ItemStack shovelMold = getModItem("shovel_mold", 1);
        ItemStack swordMold = getModItem("sword_mold", 1);
        ItemStack hoeMold = getModItem("hoe_mold", 1);
        for (MetalType type : Constants.toolMetalTypes) {
            String name = type.name;
            addCastingRecipe(
                pickaxeMold,
                Utils.getOreDictItem(Utils.getOreDictionaryName("pickaxe_head", name)),
                type.tripleIngotFluid);
            addCastingRecipe(
                axeMold,
                Utils.getOreDictItem(Utils.getOreDictionaryName("axe_head", name)),
                type.tripleIngotFluid);
            addCastingRecipe(
                shovelMold,
                Utils.getOreDictItem(Utils.getOreDictionaryName("shovel_head", name)),
                type.ingotFluid);
            addCastingRecipe(
                swordMold,
                Utils.getOreDictItem(Utils.getOreDictionaryName("sword_blade", name)),
                type.doubleIngotFluid);
            addCastingRecipe(
                hoeMold,
                Utils.getOreDictItem(Utils.getOreDictionaryName("hoe_head", name)),
                type.doubleIngotFluid);
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
