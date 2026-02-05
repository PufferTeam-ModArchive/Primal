package net.pufferlab.primal.scripts;

import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.StoneType;

public class ScriptWorld implements IScript {

    public void run() {
        addOredicts();
        addCraftingRecipes();
    }

    public void addOredicts() {
        if (Config.strataStoneTypes.getBoolean()) {
            addOreDict("limeAny", getModItem("limestone_rock", 1));
            addOreDict("coalAny", getModItem("anthracite_coal", 1));
            addOreDict("coalAny", getModItem("lignite_coal", 1));
            for (StoneType type : Constants.stoneTypes) {
                String name = type.name;
                addOreDict("rock", ItemUtils.getModItem(name + "_rock", 1));
                addOreDict("stone", ItemUtils.getModItem(name + "_raw", 1));
                addOreDict("cobblestone", ItemUtils.getModItem(name + "_cobble", 1));
                addOreDict("dirt", ItemUtils.getModItem(name + "_dirt", 1));
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
        }
    }
}
