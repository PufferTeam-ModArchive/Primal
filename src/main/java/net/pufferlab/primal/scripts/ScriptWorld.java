package net.pufferlab.primal.scripts;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.utils.CutUtils;
import net.pufferlab.primal.utils.SoilType;
import net.pufferlab.primal.utils.StoneType;

public class ScriptWorld implements IScript {

    public void runEarly() {
        addOredicts();
    }

    public void run() {
        addCraftingRecipes();
    }

    public void addOredicts() {
        if (Config.strataStoneTypes.getBoolean()) {
            addOreDict("limeAny", getModItem("limestone_rock", 1));
            addOreDict("coalAny", getModItem("anthracite_coal", 1));
            addOreDict("coalAny", getModItem("lignite_coal", 1));
            for (StoneType type : Constants.stoneTypes) {
                String name = type.name;
                addOreDict("rock", getModItem(name + "_rock", 1));
                addOreDict("stone", getModItem(name + "_raw", 1));
                addOreDict("cobblestone", getModItem(name + "_cobble", 1));
            }
            for (SoilType soil : Constants.soilTypes) {
                String name = soil.name;
                addOreDict("dirt", getModItem(name + "_dirt", 1));
            }
        }
    }

    public void addCraftingRecipes() {
        if (Config.strataStoneTypes.getBoolean()) {
            for (StoneType type : Constants.stoneTypes) {
                String name = type.name;
                addShapedRecipe(getModItem(name + "_cobble", 1), "PP", "PP", 'P', getModItem(name + "_rock", 1));
                addShapedRecipe(getModItem(name + "_rock", 4), "P", 'P', getModItem(name + "_cobble", 1));
            }
            for (int i = 0; i < CutUtils.getItemList().length; i++) {
                ItemStack stack = CutUtils.getItemList()[i];
                String name = CutUtils.getBlockNames()[i];
                addShapedRecipe(getModItem(name + "_slab", 6), "III", 'I', stack);
                addShapedRecipe(getModItem(name + "_vertical_slab", 6), "I", "I", "I", 'I', stack);
                addShapedRecipe(getModItem(name + "_stairs", 8), "I  ", "II ", "III", 'I', stack);
            }
        }
    }
}
