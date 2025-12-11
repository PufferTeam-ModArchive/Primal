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
import net.pufferlab.primal.recipes.ScrapingRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIScrapingHandler extends TemplateRecipeHandler {

    public class ScrapingPair extends CachedRecipe {

        public ScrapingPair(List<ItemStack> ingred, ItemStack result) {
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
        transferRects.add(new RecipeTransferRect(new Rectangle(59, 8, 48, 48), Primal.MODID + ".scraping"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".scraping") && getClass() == NEIScrapingHandler.class) {
            Map<List<ItemStack>, ItemStack> recipes = ScrapingRecipe.getRecipeMap();
            for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
                ScrapingPair recipePair = new NEIScrapingHandler.ScrapingPair(recipe.getKey(), recipe.getValue());
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<List<ItemStack>, ItemStack> recipes = ScrapingRecipe.getRecipeMap();
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.containsStack(recipe.getValue(), result)) {
                ScrapingPair recipePair = new NEIScrapingHandler.ScrapingPair(recipe.getKey(), recipe.getValue());
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Map<List<ItemStack>, ItemStack> recipes = ScrapingRecipe.getRecipeMap();
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.containsList(recipe.getKey(), ingredient)) {
                ScrapingPair recipePair = new NEIScrapingHandler.ScrapingPair(recipe.getKey(), recipe.getValue());
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        }
    }

    @Override
    public String getRecipeName() {
        return "Scraping";
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/scraping.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".scraping";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
    }
}
