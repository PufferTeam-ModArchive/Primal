package net.pufferlab.primal.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.MetalType;

public class CrucibleRecipe {

    private static final List<CrucibleRecipe> recipeList = new ArrayList<>();

    public static void addRecipe(FluidStack output, ItemStack input) {
        recipeList.add(new CrucibleRecipe(output, Collections.singletonList(input)));
    }

    public static void removeRecipe(FluidStack output) {
        recipeList.removeIf(r -> {
            if (Utils.equalsStack(r.output, output)) {
                return true;
            }
            return false;
        });
    }

    public static CrucibleRecipe getRecipe(ItemStack... input) {
        for (CrucibleRecipe currentRecipe : recipeList) {
            if (currentRecipe.equals(input)) {
                return currentRecipe;
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack input) {
        CrucibleRecipe recipe = getRecipe(input);
        return recipe != null;
    }

    public static List<CrucibleRecipe> getRecipeList() {
        return recipeList;
    }

    public boolean isMelting;
    FluidStack output;
    List<ItemStack> input;

    public boolean isAlloying;
    MetalType outputs;
    MetalType[] inputs;
    int[] minPercentage;
    int[] maxPercentage;

    public CrucibleRecipe(FluidStack output, List<ItemStack> input) {
        this.input = input;
        this.output = output;
    }

    public CrucibleRecipe(MetalType output, List<ItemStack> inputs) {
        this.isMelting = true;
        this.outputs = output;
        this.input = inputs;
    }

    public CrucibleRecipe(MetalType outputs, Object... objects) {
        this.isAlloying = true;
        int j = 0;
        this.outputs = outputs;
        this.minPercentage = new int[objects.length / 2];
        this.maxPercentage = new int[objects.length / 2];
        this.inputs = new MetalType[objects.length / 2];
        for (int i = 0; i < objects.length; i = +2) {
            if (objects[i] instanceof String string) {
                String[] minmax = string.split("-");
                int min = Integer.parseInt(minmax[0]);
                int max = Integer.parseInt(minmax[1]);
                this.minPercentage[j] = min;
                this.maxPercentage[j] = max;
            }
            if (objects[i + 1] instanceof MetalType type) {
                this.inputs[j] = type;
            }
            j++;
        }
    }

    public boolean equals(ItemStack... input) {
        for (ItemStack stack : input) {

        }
        return false;
    }
}
