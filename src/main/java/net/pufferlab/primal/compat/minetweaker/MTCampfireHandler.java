package net.pufferlab.primal.compat.minetweaker;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.recipes.CampfireRecipe;

import minetweaker.IUndoableAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;

public class MTCampfireHandler {

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
            CampfireRecipe.removeRecipe(output, input);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Removing Campfire recipe for " + output;
        }

        @Override
        public void undo() {
            ItemStack output = MTCompat.get(this.output);
            List<ItemStack> input = MTCompat.get(this.input);
            CampfireRecipe.addRecipe(output, input);
        }

        @Override
        public String describeUndo() {
            return "Restoring Campfire recipe for " + output;
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
            CampfireRecipe.addRecipe(output, input);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Adding Campfire recipe for " + output;
        }

        @Override
        public void undo() {
            ItemStack output = MTCompat.get(this.output);
            List<ItemStack> input = MTCompat.get(this.input);
            CampfireRecipe.removeRecipe(output, input);
        }

        @Override
        public String describeUndo() {
            return "Removing Campfire recipe for " + output;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

}
