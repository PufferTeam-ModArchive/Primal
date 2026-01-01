package net.pufferlab.primal.compat.minetweaker;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.recipes.QuernRecipe;

import minetweaker.IUndoableAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;

public class MTQuernHandler {

    public static class RemoveRecipe implements IUndoableAction {

        private final IItemStack output;
        private final IIngredient input;

        public RemoveRecipe(IItemStack output, IIngredient input) {
            this.output = output;
            this.input = input;
        }

        @Override
        public void apply() {
            ItemStack output = MTCompat.get(this.output);
            List<ItemStack> input = MTCompat.get(this.input);
            QuernRecipe.removeRecipe(output, input);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Removing Quern recipe for " + output;
        }

        @Override
        public void undo() {
            ItemStack output = MTCompat.get(this.output);
            List<ItemStack> input = MTCompat.get(this.input);
            QuernRecipe.addRecipe(output, input);
        }

        @Override
        public String describeUndo() {
            return "Restoring Quern recipe for " + output;
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
            ItemStack output = MTCompat.get(this.output);
            List<ItemStack> input = MTCompat.get(this.input);
            QuernRecipe.addRecipe(output, input);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Adding Quern recipe for " + output;
        }

        @Override
        public void undo() {
            ItemStack output = MTCompat.get(this.output);
            List<ItemStack> input = MTCompat.get(this.input);
            QuernRecipe.removeRecipe(output, input);
        }

        @Override
        public String describeUndo() {
            return "Removing Quern recipe for " + output;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

}
