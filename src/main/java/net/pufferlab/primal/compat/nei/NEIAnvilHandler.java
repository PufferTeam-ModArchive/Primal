package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.AnvilRecipe;
import net.pufferlab.primal.utils.Utils;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIAnvilHandler extends TemplateRecipeHandler {

    public class AnvilPair extends CachedRecipe {

        public AnvilPair(List<ItemStack> ingred, ItemStack result) {
            this.ingred = new PositionedStack(ingred, 43, 25, true);
            this.result = new PositionedStack(result, 114, 25, true);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, Collections.singletonList(ingred));
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        public PositionedStack ingred;
        public PositionedStack result;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(64, 8, 48, 48), Primal.MODID + ".anvil"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".anvil") && getClass() == NEIAnvilHandler.class) {
            List<AnvilRecipe> recipes = AnvilRecipe.getRecipeList();
            for (AnvilRecipe recipe : recipes) {
                arecipes.add(new AnvilPair(recipe.input, recipe.output));
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<AnvilRecipe> recipes = AnvilRecipe.getRecipeList();
        for (AnvilRecipe recipe : recipes) {
            if (Utils.equalsStack(result, recipe.output)) {
                arecipes.add(new AnvilPair(recipe.input, recipe.output));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<AnvilRecipe> recipes = AnvilRecipe.getRecipeList();
        for (AnvilRecipe recipe : recipes) {
            if (Utils.containsStack(ingredient, recipe.input)) {
                arecipes.add(new AnvilPair(recipe.input, recipe.output));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return NEICompat.translateNEI("anvil");
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/anvil.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".anvil";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);

    }
}
