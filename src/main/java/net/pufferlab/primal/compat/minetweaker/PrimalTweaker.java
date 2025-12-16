package net.pufferlab.primal.compat.minetweaker;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.primal")
public class PrimalTweaker {

    @ZenMethod
    public static void addCampfireRecipe(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new MTCampfireHandler.AddRecipe(output, input));
    }

    @ZenMethod
    public static void removeCampfireRecipe(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new MTCampfireHandler.RemoveRecipe(output, input));
    }

    @ZenMethod
    public static void addChoppingLogRecipe(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new MTChoppingLogHandler.AddRecipe(output, input));
    }

    @ZenMethod
    public static void removeChoppingLogRecipe(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new MTChoppingLogHandler.RemoveRecipe(output, input));
    }

    @ZenMethod
    public static void addPitKilnRecipe(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new MTPitKilnHandler.AddRecipe(output, input));
    }

    @ZenMethod
    public static void removePitKilnLogRecipe(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new MTPitKilnHandler.RemoveRecipe(output, input));
    }

    @ZenMethod
    public static void addTanningRecipe(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new MTTanningHandler.AddRecipe(output, input));
    }

    @ZenMethod
    public static void removeTanningRecipe(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new MTTanningHandler.RemoveRecipe(output, input));
    }

    public static ItemStack get(IItemStack iStack) {
        return MineTweakerMC.getItemStack(iStack);
    }

    public static List<ItemStack> get(IIngredient iIngredient) {
        List<IItemStack> items = iIngredient.getItems();
        return Arrays.asList(MineTweakerMC.getItemStacks(items));
    }
}
