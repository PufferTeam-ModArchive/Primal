package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.TanningRecipe;
import net.pufferlab.primal.utils.Utils;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;

public class NEITanningHandler extends NEITemplateHandler {

    public class TanningPair extends NEICachedRecipe {

        public TanningPair(TanningRecipe recipe) {
            this(recipe.input, recipe.output);
            setRecipe(recipe);
        }

        public TanningPair(List<ItemStack> ingred, ItemStack result) {
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
        transferRects.add(new RecipeTransferRect(new Rectangle(59, 8, 48, 48), Primal.MODID + ".tanning"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".tanning") && getClass() == NEITanningHandler.class) {
            List<TanningRecipe> recipes = TanningRecipe.getRecipeList();
            for (TanningRecipe recipe : recipes) {
                TanningPair recipePair = new NEITanningHandler.TanningPair(recipe);
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<TanningRecipe> recipes = TanningRecipe.getRecipeList();
        for (TanningRecipe recipe : recipes) {
            if (Utils.equalsStack(recipe.output, result)) {
                TanningPair recipePair = new NEITanningHandler.TanningPair(recipe);
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<TanningRecipe> recipes = TanningRecipe.getRecipeList();
        for (TanningRecipe recipe : recipes) {
            if (Utils.containsStack(recipe.input, ingredient)) {
                TanningPair recipePair = new NEITanningHandler.TanningPair(recipe);
                recipePair.computeVisuals();
                arecipes.add(recipePair);
            }
        }
    }

    @Override
    public String getRecipeName() {
        return NEICompat.translateNEI("tanning");
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/tanning.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".tanning";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(Utils.asResource(getGuiTexture()));
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
    }
}
