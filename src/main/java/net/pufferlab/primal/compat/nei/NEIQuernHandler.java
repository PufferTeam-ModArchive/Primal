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
import net.pufferlab.primal.recipes.QuernRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIQuernHandler extends TemplateRecipeHandler {

    public class QuernPair extends CachedRecipe {

        public QuernPair(List<ItemStack> ingred, ItemStack result) {
            this.ingred = new PositionedStack(ingred, 43, 10, false);
            this.result = new PositionedStack(result, 119, 24, false);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, Collections.singletonList(ingred));
        }

        public PositionedStack getResult() {
            return result;
        }

        final PositionedStack result;
        final PositionedStack ingred;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(59, 8, 48, 48), Primal.MODID + ".quern"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".quern") && getClass() == NEIQuernHandler.class) {
            Map<List<ItemStack>, ItemStack> recipes = QuernRecipe.getRecipeMap();
            for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
                arecipes.add(new NEIQuernHandler.QuernPair(recipe.getKey(), recipe.getValue()));
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<List<ItemStack>, ItemStack> recipes = QuernRecipe.getRecipeMap();
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.equalsStack(recipe.getValue(), result)) {
                arecipes.add(new NEIQuernHandler.QuernPair(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Map<List<ItemStack>, ItemStack> recipes = QuernRecipe.getRecipeMap();
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.containsStack(recipe.getKey(), ingredient)) {
                arecipes.add(new NEIQuernHandler.QuernPair(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return NEICompat.translateNEI("quern");
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/quern.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".quern";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
    }
}
