package net.pufferlab.primal.compat.minetweaker;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.recipes.PitKilnRecipe;

import minetweaker.IUndoableAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;

public class MTPitKilnHandler {

    public static class RemoveRecipe implements IUndoableAction {

        private final IItemStack output;
        private final IIngredient input;

        public RemoveRecipe(IItemStack output, IIngredient input) {
            this.output = output;
            this.input = input;
        }

        @Override
        public void apply() {
            ItemStack output = PrimalTweaker.get(this.output);
            List<ItemStack> input = PrimalTweaker.get(this.input);
            PitKilnRecipe.removePitKilnRecipe(output, input);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Removing PitKiln recipe for " + output;
        }

        @Override
        public void undo() {
            ItemStack output = PrimalTweaker.get(this.output);
            List<ItemStack> input = PrimalTweaker.get(this.input);
            PitKilnRecipe.addPitKilnRecipe(output, input);
        }

        @Override
        public String describeUndo() {
            return "Restoring PitKiln recipe for " + output;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    public static class AddRecipe implements IUndoableAction {

        private final IItemStack output;
        private final IIngredient input;

        public AddRecipe(IItemStack output, IIngredient input) {
            this.output = output;
            this.input = input;
        }

        @Override
        public void apply() {
            ItemStack output = PrimalTweaker.get(this.output);
            List<ItemStack> input = PrimalTweaker.get(this.input);
            PitKilnRecipe.addPitKilnRecipe(output, input);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Adding PitKiln recipe for " + output;
        }

        @Override
        public void undo() {
            ItemStack output = PrimalTweaker.get(this.output);
            List<ItemStack> input = PrimalTweaker.get(this.input);
            PitKilnRecipe.removePitKilnRecipe(output, input);
        }

        @Override
        public String describeUndo() {
            return "Removing PitKiln recipe for " + output;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

}
