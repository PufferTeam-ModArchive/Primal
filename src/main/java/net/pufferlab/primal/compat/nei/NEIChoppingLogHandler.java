package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.ChoppingLogRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIChoppingLogHandler extends TemplateRecipeHandler {

    public class ChoppingLogPair extends CachedRecipe {

        public ChoppingLogPair(List<ItemStack> ingred, ItemStack result) {
            this.ingredients = new PositionedStack(ingred, 43, 10, true);
            this.result = new PositionedStack(result, 119, 24, false);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, Collections.singletonList(ingredients));
        }

        public PositionedStack getResult() {
            return result;
        }

        public void computeVisuals() {
            ingredients.generatePermutations();
        }

        PositionedStack result;
        PositionedStack ingredients;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(59, 8, 48, 48), Primal.MODID + ".chopping_log"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".chopping_log") && getClass() == NEIChoppingLogHandler.class) {
            Map<List<ItemStack>, ItemStack> recipes = ChoppingLogRecipe.getRecipeMap();
            for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
                ChoppingLogPair recipePair = new NEIChoppingLogHandler.ChoppingLogPair(
                    recipe.getKey(),
                    recipe.getValue());
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<List<ItemStack>, ItemStack> recipes = ChoppingLogRecipe.getRecipeMap();
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.areStackEquals(recipe.getValue(), result)) {
                ChoppingLogPair recipePair = new NEIChoppingLogHandler.ChoppingLogPair(
                    recipe.getKey(),
                    recipe.getValue());
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Map<List<ItemStack>, ItemStack> recipes = ChoppingLogRecipe.getRecipeMap();
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.containsList(recipe.getKey(), ingredient)) {
                ChoppingLogPair recipePair = new NEIChoppingLogHandler.ChoppingLogPair(
                    recipe.getKey(),
                    recipe.getValue());
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        }
    }

    @Override
    public String getRecipeName() {
        return "Chopping Log";
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/chopping_log.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".chopping_log";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
    }
}
