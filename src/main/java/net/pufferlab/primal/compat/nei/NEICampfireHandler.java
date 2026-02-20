package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.CampfireRecipe;
import net.pufferlab.primal.utils.Utils;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEICampfireHandler extends TemplateRecipeHandler {

    public class CampfirePair extends CachedRecipe {

        public CampfirePair(List<ItemStack> ingred, ItemStack result) {
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
        transferRects.add(new RecipeTransferRect(new Rectangle(59, 8, 48, 48), Primal.MODID + ".campfire"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".campfire") && getClass() == NEICampfireHandler.class) {
            Map<List<ItemStack>, ItemStack> recipes = CampfireRecipe.getRecipeMap();
            for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
                arecipes.add(new NEICampfireHandler.CampfirePair(recipe.getKey(), recipe.getValue()));
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<List<ItemStack>, ItemStack> recipes = CampfireRecipe.getRecipeMap();
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.equalsStack(recipe.getValue(), result)) {
                arecipes.add(new NEICampfireHandler.CampfirePair(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Map<List<ItemStack>, ItemStack> recipes = CampfireRecipe.getRecipeMap();
        for (Map.Entry<List<ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.containsStack(recipe.getKey(), ingredient)) {
                arecipes.add(new NEICampfireHandler.CampfirePair(recipe.getKey(), recipe.getValue()));
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
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
    }
}
