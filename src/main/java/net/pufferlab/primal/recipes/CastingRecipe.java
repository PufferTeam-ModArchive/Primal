package net.pufferlab.primal.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Utils;

public class CastingRecipe {

    private static final List<CastingRecipe> recipeList = new ArrayList<>();

    public static void addRecipe(ItemStack cast, ItemStack output, FluidStack input) {
        recipeList.add(new CastingRecipe(cast, output, input));
    }

    public static void removeRecipe(ItemStack output) {
        recipeList.removeIf(r -> {
            if (Utils.equalsStack(r.output, output)) {
                return true;
            }
            return false;
        });
    }

    public static CastingRecipe getRecipe(ItemStack cast, FluidStack input) {
        for (CastingRecipe currentRecipe : recipeList) {
            if (currentRecipe.equals(cast, input)) {
                return currentRecipe;
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack cast, FluidStack input) {
        CastingRecipe recipe = getRecipe(cast, input);
        return recipe != null;
    }

    public static List<CastingRecipe> getRecipeList() {
        return recipeList;
    }

    public ItemStack output;
    public ItemStack cast;
    public FluidStack input;
    public ItemStack inputBlock;

    public CastingRecipe(ItemStack cast, ItemStack output, FluidStack input) {
        this.input = input;
        this.output = output;
        this.cast = cast;
        if (input != null) {
            this.inputBlock = new ItemStack(
                input.getFluid()
                    .getBlock(),
                input.amount,
                0);
        }
    }

    public boolean equals(ItemStack cast, FluidStack input) {
        if (Utils.equalsStack(this.input, input) && Utils.equalsStack(this.cast, cast)) {
            if (input.amount >= this.input.amount) {
                return true;
            }
        }
        return false;
    }
}
