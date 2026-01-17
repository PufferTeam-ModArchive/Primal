package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.PitKilnRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIPitKilnHandler extends TemplateRecipeHandler {

    public class PitKilnPair extends CachedRecipe {

        public PitKilnPair(List<ItemStack> ingred, ItemStack result) {
            this.ingred = new PositionedStack(ingred, 43, 44, true);
            this.result = new PositionedStack(result, 119, 24, false);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, Collections.singletonList(ingred));
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        public PositionedStack getOtherStack() {
            return new PositionedStack(new ItemStack(Blocks.fire, 0, 1), 43, 9);
        }

        final PositionedStack ingred;
        final PositionedStack result;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(59, 8, 48, 48), Primal.MODID + ".pit_kiln"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".pit_kiln") && getClass() == NEIPitKilnHandler.class) {
            Map<List<ItemStack>, ItemStack> recipes = PitKilnRecipe.getRecipeMap();
            for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
                arecipes.add(new NEIPitKilnHandler.PitKilnPair(recipe.getKey(), recipe.getValue()));
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<List<ItemStack>, ItemStack> recipes = PitKilnRecipe.getRecipeMap();
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.equalsStack(recipe.getValue(), result)) {
                arecipes.add(new NEIPitKilnHandler.PitKilnPair(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Map<List<ItemStack>, ItemStack> recipes = PitKilnRecipe.getRecipeMap();
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.containsStack(recipe.getKey(), ingredient)) {
                arecipes.add(new NEIPitKilnHandler.PitKilnPair(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return NEICompat.translateNEI("pit_kiln");
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/pit_kiln.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".pit_kiln";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
    }
}
