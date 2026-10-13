package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.ChoppingLogRecipe;
import net.pufferlab.primal.utils.Utils;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;

public class NEIChoppingLogHandler extends NEITemplateHandler {

    public class ChoppingLogPair extends NEICachedRecipe {

        public ChoppingLogPair(ChoppingLogRecipe recipe) {
            this(recipe.input, recipe.output);
            setRecipe(recipe);
        }

        public ChoppingLogPair(List<ItemStack> ingred, ItemStack result) {
            this.ingredients = new PositionedStack(ingred, 43, 10, true);
            this.result = new PositionedStack(result, 119, 24, false);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, Utils.asList(ingredients));
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
            List<ChoppingLogRecipe> recipes = ChoppingLogRecipe.getRecipeList();
            for (ChoppingLogRecipe recipe : recipes) {
                ChoppingLogPair recipePair = new NEIChoppingLogHandler.ChoppingLogPair(recipe);
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<ChoppingLogRecipe> recipes = ChoppingLogRecipe.getRecipeList();
        for (ChoppingLogRecipe recipe : recipes) {
            if (Utils.equalsStack(recipe.output, result)) {
                ChoppingLogPair recipePair = new NEIChoppingLogHandler.ChoppingLogPair(recipe);
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<ChoppingLogRecipe> recipes = ChoppingLogRecipe.getRecipeList();
        for (ChoppingLogRecipe recipe : recipes) {
            if (Utils.containsStack(recipe.input, ingredient)) {
                ChoppingLogPair recipePair = new NEIChoppingLogHandler.ChoppingLogPair(recipe);
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        }
    }

    @Override
    public String getRecipeName() {
        return NEICompat.translateNEI("chopping_log");
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
        changeTexture(Utils.asResource(getGuiTexture()));
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
    }
}
