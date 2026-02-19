package net.pufferlab.primal.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.utils.Utils;

public class MeltingRecipe {

    private static final List<MeltingRecipe> recipeList = new ArrayList<>();

    public static void addRecipe(FluidStack output, ItemStack input) {
        recipeList.add(new MeltingRecipe(output, Collections.singletonList(input)));
    }

    public static void addRecipe(FluidStack output, String input) {
        recipeList.add(new MeltingRecipe(output, OreDictionary.getOres(input)));
    }

    public static void removeRecipe(FluidStack output) {
        recipeList.removeIf(r -> {
            if (Utils.equalsStack(r.output, output)) {
                return true;
            }
            return false;
        });
    }

    public static FluidStack getOutput(ItemStack input) {
        for (MeltingRecipe currentRecipe : recipeList) {
            if (currentRecipe.equals(input)) {
                return currentRecipe.output;
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack input) {
        FluidStack recipe = getOutput(input);
        return recipe != null;
    }

    public static List<MeltingRecipe> getRecipeList() {
        return recipeList;
    }

    public FluidStack output;
    public ItemStack outputBlock;
    public List<ItemStack> input;

    public MeltingRecipe(FluidStack output, List<ItemStack> input) {
        this.input = input;
        this.output = output;
        if (output != null) {
            this.outputBlock = new ItemStack(
                output.getFluid()
                    .getBlock(),
                output.amount,
                0);
        }
    }

    public boolean equals(ItemStack input) {
        if (Utils.containsStack(this.input, input)) {
            return true;
        }
        return false;
    }
}
