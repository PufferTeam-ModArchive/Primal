package net.pufferlab.primal.compat.minetweaker;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.recipes.BarrelRecipe;

import minetweaker.IUndoableAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;

public class MTBarrelHandler {

    public static class RemoveRecipe implements IUndoableAction {

        private final IItemStack output;
        private final ILiquidStack outputLiquid;
        private final IIngredient input;
        private final ILiquidStack inputLiquid;
        private final int processingTime;

        public RemoveRecipe(IItemStack output, ILiquidStack outputLiquid, IIngredient input, ILiquidStack inputLiquid,
            int processingTime) {
            this.output = output;
            this.outputLiquid = outputLiquid;
            this.input = input;
            this.inputLiquid = inputLiquid;
            this.processingTime = processingTime;
        }

        @Override
        public void apply() {
            ItemStack output = MTCompat.get(this.output);
            FluidStack outputLiquid = MTCompat.get(this.outputLiquid);
            List<ItemStack> input = MTCompat.get(this.input);
            FluidStack inputLiquid = MTCompat.get(this.inputLiquid);
            BarrelRecipe.removeBarrelRecipe(output, outputLiquid, input, inputLiquid);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Removing Barrel recipe for " + output;
        }

        @Override
        public void undo() {
            ItemStack output = MTCompat.get(this.output);
            FluidStack outputLiquid = MTCompat.get(this.outputLiquid);
            List<ItemStack> input = MTCompat.get(this.input);
            FluidStack inputLiquid = MTCompat.get(this.inputLiquid);
            BarrelRecipe.addBarrelRecipe(output, outputLiquid, input, inputLiquid, this.processingTime);
        }

        @Override
        public String describeUndo() {
            return "Restoring Barrel recipe for " + output;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    public static class AddRecipe implements IUndoableAction {

        private final IItemStack output;
        private final ILiquidStack outputLiquid;
        private final IIngredient input;
        private final ILiquidStack inputLiquid;
        private final int processingTime;

        public AddRecipe(IItemStack output, ILiquidStack outputLiquid, IIngredient input, ILiquidStack inputLiquid,
            int processingTime) {
            this.output = output;
            this.outputLiquid = outputLiquid;
            this.input = input;
            this.inputLiquid = inputLiquid;
            this.processingTime = processingTime;
        }

        @Override
        public void apply() {
            ItemStack output = MTCompat.get(this.output);
            FluidStack outputLiquid = MTCompat.get(this.outputLiquid);
            List<ItemStack> input = MTCompat.get(this.input);
            FluidStack inputLiquid = MTCompat.get(this.inputLiquid);
            BarrelRecipe.addBarrelRecipe(output, outputLiquid, input, inputLiquid, this.processingTime);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Adding Barrel recipe for " + output;
        }

        @Override
        public void undo() {
            ItemStack output = MTCompat.get(this.output);
            FluidStack outputLiquid = MTCompat.get(this.outputLiquid);
            List<ItemStack> input = MTCompat.get(this.input);
            FluidStack inputLiquid = MTCompat.get(this.inputLiquid);
            BarrelRecipe.removeBarrelRecipe(output, outputLiquid, input, inputLiquid);
        }

        @Override
        public String describeUndo() {
            return "Removing Barrel recipe for " + output;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

}
