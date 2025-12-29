package net.pufferlab.primal.compat.minetweaker;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.recipes.KnappingRecipe;
import net.pufferlab.primal.recipes.KnappingType;

import minetweaker.IUndoableAction;
import minetweaker.api.item.IItemStack;

public class MTKnappingHandler {

    public static class RemoveRecipe implements IUndoableAction {

        private final IItemStack output;
        private final String type;
        private final boolean[][] icons;

        public RemoveRecipe(String type, IItemStack output, String... rows) {
            this.output = output;
            this.type = type;
            this.icons = KnappingRecipe.getKnappingPattern(rows);
        }

        @Override
        public void apply() {
            ItemStack output = MTCompat.get(this.output);
            KnappingType type = KnappingType.getType(this.type);
            KnappingRecipe.removeKnappingRecipe(type, output, icons);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Removing Knapping recipe for " + output;
        }

        @Override
        public void undo() {
            ItemStack output = MTCompat.get(this.output);
            KnappingType type = KnappingType.getType(this.type);
            KnappingRecipe.addKnappingRecipe(type, output, this.icons);
        }

        @Override
        public String describeUndo() {
            return "Restoring Knapping recipe for " + output;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    public static class AddRecipe implements IUndoableAction {

        private final IItemStack output;
        private final String type;
        private final boolean[][] icons;

        public AddRecipe(String type, IItemStack output, String... rows) {
            this.output = output;
            this.type = type;
            this.icons = KnappingRecipe.getKnappingPattern(rows);
        }

        @Override
        public void apply() {
            ItemStack output = MTCompat.get(this.output);
            KnappingType type = KnappingType.getType(this.type);
            KnappingRecipe.addKnappingRecipe(type, output, this.icons);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Adding Knapping recipe for " + output;
        }

        @Override
        public void undo() {
            ItemStack output = MTCompat.get(this.output);
            KnappingType type = KnappingType.getType(this.type);
            KnappingRecipe.removeKnappingRecipe(type, output, this.icons);
        }

        @Override
        public String describeUndo() {
            return "Removing Knapping recipe for " + output;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    public static class RegisterType implements IUndoableAction {

        public final KnappingType type;

        public RegisterType(String name, IItemStack item, boolean needsKnife, String sound, float pitch) {
            this.type = KnappingType.getNewType(name, MTCompat.get(item), needsKnife, sound, pitch);
        }

        public RegisterType(String name, IItemStack item, boolean needsKnife) {
            this.type = KnappingType.getNewType(name, MTCompat.get(item), needsKnife, "dig.stone", 1.0F);
        }

        public RegisterType(String name, IItemStack item) {
            this.type = KnappingType.getNewType(name, MTCompat.get(item), false, "dig.stone", 1.0F);
        }

        @Override
        public void apply() {
            KnappingType.addType(type);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Adding Knapping Type for " + this.type.name;
        }

        @Override
        public void undo() {
            KnappingType.removeType(this.type);
        }

        @Override
        public String describeUndo() {
            return "Removing Knapping Type for " + this.type.name;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

}
