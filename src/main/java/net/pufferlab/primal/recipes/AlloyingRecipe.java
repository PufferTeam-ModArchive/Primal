package net.pufferlab.primal.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Utils;

public class AlloyingRecipe {

    private static final List<AlloyingRecipe> recipeList = new ArrayList<>();

    public static void addRecipe(FluidStack output, Object... inputs) {
        recipeList.add(new AlloyingRecipe(output, inputs));
    }

    public static void removeRecipe(FluidStack output) {
        recipeList.removeIf(r -> {
            if (Utils.equalsStack(r.outputs, output)) {
                return true;
            }
            return false;
        });
    }

    public static FluidStack getOutput(FluidStack... input) {
        for (AlloyingRecipe currentRecipe : recipeList) {
            int equal = currentRecipe.equals(input);
            if (equal > 0) {
                FluidStack stack2 = currentRecipe.outputs.copy();
                stack2.amount = equal;
                return stack2;
            }
        }
        return null;
    }

    public static AlloyingRecipe getRecipe(FluidStack... input) {
        for (AlloyingRecipe currentRecipe : recipeList) {
            if (currentRecipe.equals(input) > 0) {
                return currentRecipe;
            }
        }
        return null;
    }

    public static boolean hasRecipe(FluidStack... input) {
        AlloyingRecipe recipe = getRecipe(input);
        return recipe != null;
    }

    public static List<AlloyingRecipe> getRecipeList() {
        return recipeList;
    }

    public FluidStack outputs;
    public ItemStack outputsBlock;
    public FluidStack[] inputs;
    public ItemStack[] inputsBlock;
    public int[] minPercentage;
    public int[] maxPercentage;

    public AlloyingRecipe(FluidStack outputs, Object... objects) {
        int j = 0;
        this.outputs = outputs;
        int size = objects.length / 2;
        this.minPercentage = new int[size];
        this.maxPercentage = new int[size];
        this.inputs = new FluidStack[size];
        this.inputsBlock = new ItemStack[size];
        for (int i = 0; i < objects.length; i = i + 2) {
            if (objects[i] instanceof String string) {
                String[] minmax = string.split("-");
                int min = Integer.parseInt(minmax[0]);
                int max = Integer.parseInt(minmax[1]);
                this.minPercentage[j] = min;
                this.maxPercentage[j] = max;
            }
            if (objects[i + 1] instanceof FluidStack type) {
                this.inputs[j] = type;
            }
            j++;
        }

        for (int i = 0; i < this.inputs.length; i++) {
            FluidStack stack = this.inputs[i];
            if (stack == null) {
                throw new NullPointerException();
            } else {
                this.inputsBlock[i] = new ItemStack(
                    inputs[i].getFluid()
                        .getBlock(),
                    1,
                    0);
            }
        }
        this.outputsBlock = new ItemStack(
            outputs.getFluid()
                .getBlock(),
            1,
            0);
    }

    public int equals(FluidStack... stacks) {
        int totalAmount = 0;
        FluidStack[] inputsRelative = new FluidStack[this.inputs.length];
        for (FluidStack stack : stacks) {
            for (int i = 0; i < inputs.length; i++) {
                if (Utils.equalsStack(stack, inputs[i])) {
                    inputsRelative[i] = stack.copy();
                }
            }
            if (stack != null) {
                totalAmount = totalAmount + stack.amount;
            }
        }

        boolean valid = true;
        for (int j = 0; j < inputsRelative.length; j++) {
            FluidStack stack = inputsRelative[j];
            if (stack != null) {
                int min = minPercentage[j];
                int max = maxPercentage[j];
                int cMin = (int) Math.floor(((float) stack.amount / (float) totalAmount) * 100F);
                if (cMin > max || cMin < min) {
                    valid = false;
                }
            } else {
                valid = false;
            }
        }
        if (valid) {
            return totalAmount;
        }
        return -1;
    }
}
