package net.pufferlab.primal.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.Utils;

public class BarrelRecipe {

    private static final List<BarrelRecipe> recipeList = new ArrayList<>();

    public static void addBarrelRecipe(ItemStack output, FluidStack outputLiquid, ItemStack input,
        FluidStack inputLiquid) {
        recipeList.add(new BarrelRecipe(output, outputLiquid, Collections.singletonList(input), inputLiquid, 60));
    }

    public static void addBarrelRecipe(ItemStack output, FluidStack outputLiquid, ItemStack input,
        FluidStack inputLiquid, int processingTime) {
        recipeList
            .add(new BarrelRecipe(output, outputLiquid, Collections.singletonList(input), inputLiquid, processingTime));
    }

    public static void addBarrelRecipe(ItemStack output, FluidStack outputLiquid, String input, FluidStack inputLiquid,
        int processingTime) {
        recipeList
            .add(new BarrelRecipe(output, outputLiquid, OreDictionary.getOres(input), inputLiquid, processingTime));
    }

    public static BarrelRecipe getRecipe(ItemStack input, FluidStack inputLiquid) {
        for (BarrelRecipe currentRecipe : recipeList) {
            if (currentRecipe.equals(input, inputLiquid)) {
                return currentRecipe;
            }
        }
        return null;
    }

    public static boolean hasRecipe(ItemStack input, FluidStack inputLiquid) {
        BarrelRecipe recipe = getRecipe(input, inputLiquid);
        return recipe != null;
    }

    public static List<BarrelRecipe> getRecipeList() {
        return recipeList;
    }

    public ItemStack output;
    public FluidStack outputLiquid;
    public ItemStack outputLiquidBlock;
    public List<ItemStack> input;
    public FluidStack inputLiquid;
    public ItemStack inputLiquidBlock;
    public int processingTime;

    public BarrelRecipe(ItemStack output, FluidStack outputLiquid, List<ItemStack> input, FluidStack inputLiquid,
        int processingTime) {
        this.output = output;
        this.outputLiquid = outputLiquid;
        if(outputLiquid != null) {
            this.outputLiquidBlock = new ItemStack(
                outputLiquid.getFluid()
                    .getBlock(),
                1,
                0);
            this.outputLiquidBlock.stackTagCompound = new NBTTagCompound();
            this.outputLiquidBlock.stackTagCompound.setInteger("DisplayFluidAmount", outputLiquid.amount);
        }
        this.input = input;
        this.inputLiquid = inputLiquid;
        this.inputLiquidBlock = new ItemStack(
            inputLiquid.getFluid()
                .getBlock(),
            1,
            0);
        this.inputLiquidBlock.stackTagCompound = new NBTTagCompound();
        this.inputLiquidBlock.stackTagCompound.setInteger("DisplayFluidAmount", inputLiquid.amount);
        this.processingTime = processingTime;
    }

    public boolean equals(ItemStack input, FluidStack inputLiquid) {
        if (Utils.containsStack(this.inputLiquid, inputLiquid) && Utils.containsList(input, this.input)) {
            if (this.inputLiquid.amount <= inputLiquid.amount) {
                return true;
            }
        }
        return false;
    }
}
