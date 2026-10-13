package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.CampfireRecipe;
import net.pufferlab.primal.utils.Utils;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;

public class NEICampfireHandler extends NEITemplateHandler {

    public class CampfirePair extends NEICachedRecipe {

        public CampfirePair(CampfireRecipe recipe) {
            this(recipe.input, recipe.output);
            setRecipe(recipe);
        }

        public CampfirePair(List<ItemStack> ingred, ItemStack result) {
            this.ingred = new PositionedStack(ingred, 43, 10, false);
            this.result = new PositionedStack(result, 119, 24, false);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, Utils.asList(ingred));
        }

        public PositionedStack getResult() {
            return result;
        }

        final PositionedStack result;
        final PositionedStack ingred;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(59, 8, 48, 48), Primal.MODID + ".campfire"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".campfire") && getClass() == NEICampfireHandler.class) {
            List<CampfireRecipe> recipes = CampfireRecipe.getRecipeList();
            for (CampfireRecipe recipe : recipes) {
                arecipes.add(new NEICampfireHandler.CampfirePair(recipe));
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<CampfireRecipe> recipes = CampfireRecipe.getRecipeList();
        for (CampfireRecipe recipe : recipes) {
            if (Utils.equalsStack(recipe.output, result)) {
                arecipes.add(new NEICampfireHandler.CampfirePair(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<CampfireRecipe> recipes = CampfireRecipe.getRecipeList();
        for (CampfireRecipe recipe : recipes) {
            if (Utils.containsStack(recipe.input, ingredient)) {
                arecipes.add(new NEICampfireHandler.CampfirePair(recipe));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return NEICompat.translateNEI("campfire");
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/campfire.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".campfire";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(Utils.asResource(getGuiTexture()));
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
    }
}
