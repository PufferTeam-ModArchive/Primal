package net.pufferlab.primal.recipes;

import java.util.*;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.utils.RecipeUtils;

public class BarrelRecipe {

    private static final List<BarrelRecipe> recipeList = new ArrayList<>();
    private static final Map<String, BarrelRecipe> recipeIDMap = new HashMap<>();

    public static void addRecipe(ItemStack output, FluidStack outputLiquid, ItemStack input, FluidStack inputLiquid) {
        recipeList.add(
            new BarrelRecipe(output, outputLiquid, Collections.singletonList(input), inputLiquid, 60)
                .setRecipeID(output, outputLiquid, input, inputLiquid));
    }

    public static void addRecipe(ItemStack output, FluidStack outputLiquid, List<ItemStack> input,
        FluidStack inputLiquid, int processingTime) {
        recipeList.add(
            new BarrelRecipe(output, outputLiquid, input, inputLiquid, processingTime)
                .setRecipeID(output, outputLiquid, input, inputLiquid));
    }

    public static void addRecipe(ItemStack output, FluidStack outputLiquid, ItemStack input, FluidStack inputLiquid,
        int processingTime) {
        recipeList.add(
            new BarrelRecipe(output, outputLiquid, Collections.singletonList(input), inputLiquid, processingTime)
                .setRecipeID(output, outputLiquid, input, inputLiquid));
    }

    public static void addRecipe(ItemStack output, FluidStack outputLiquid, String input, FluidStack inputLiquid,
        int processingTime) {
        recipeList.add(
            new BarrelRecipe(output, outputLiquid, OreDictionary.getOres(input), inputLiquid, processingTime)
                .setRecipeID(output, outputLiquid, input, inputLiquid));
    }

    public static void removeRecipe(ItemStack output, FluidStack outputLiquid, List<ItemStack> input,
        FluidStack inputLiquid) {
        recipeList.removeIf(r -> {
            if (Utils.containsStack(r.input, input) && Utils.equalsStack(r.inputLiquid, inputLiquid)
                && Utils.equalsStack(r.output, output)
                && Utils.equalsStack(r.outputLiquid, outputLiquid)) {
                return true;
            }
            return false;
        });
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

    public static BarrelRecipe getRecipe(String id) {
        return recipeIDMap.get(id);
    }

    public ItemStack output;
    public FluidStack outputLiquid;
    public ItemStack outputLiquidBlock;
    public List<ItemStack> input;
    public FluidStack inputLiquid;
    public ItemStack inputLiquidBlock;
    public int processingTime;
    public String recipeID;

    public BarrelRecipe(ItemStack output, FluidStack outputLiquid, List<ItemStack> input, FluidStack inputLiquid,
        int processingTime) {
        this.output = output;
        this.outputLiquid = outputLiquid;
        if (outputLiquid != null) {
            this.outputLiquidBlock = new ItemStack(
                outputLiquid.getFluid()
                    .getBlock(),
                outputLiquid.amount,
                0);
        }
        this.input = input;
        this.inputLiquid = inputLiquid;
        this.inputLiquidBlock = new ItemStack(
            inputLiquid.getFluid()
                .getBlock(),
            inputLiquid.amount,
            0);
        this.processingTime = processingTime;
    }

    public BarrelRecipe setRecipeID(Object... objects) {
        String string = RecipeUtils.getRecipeHash(objects);
        if (!recipeIDMap.containsKey(string)) {
            this.recipeID = string;
            recipeIDMap.put(string, this);
        } else {
            RecipeUtils.throwInvalidRecipe(string);
        }
        return this;
    }

    public boolean equals(ItemStack input, FluidStack inputLiquid) {
        if (Utils.equalsStack(this.inputLiquid, inputLiquid) && Utils.containsStack(input, this.input)) {
            if (this.inputLiquid.amount <= inputLiquid.amount) {
                return true;
            }
        }
        return false;
    }
}
