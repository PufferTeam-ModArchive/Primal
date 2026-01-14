package net.pufferlab.primal.scripts;

import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.MetalType;

public class ScriptOreProcessing implements IScript {

    public void run() {
        addMeltingRecipes();
        addCastingRecipes();
        addAlloyingRecipes();
    }

    public void addMeltingRecipes() {
        for (MetalType type : Constants.metalTypes) {
            String name = type.name;

            addMeltingRecipe(type.ingotFluid, Utils.getOreDictionaryName("ingot", name));
            addMeltingRecipe(type.nuggetFluid, Utils.getOreDictionaryName("nugget", name));
        }
    }

    public void addCastingRecipes() {

    }

    public void addAlloyingRecipes() {
        addAlloyingRecipe(
            Constants.bronze.fluidStack,
            "70-90",
            Constants.copper.fluidStack,
            "10-30",
            Constants.tin.fluidStack);
    }
}
