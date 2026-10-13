package net.pufferlab.primal.compat.nei;

import java.awt.*;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.recipes.Recipe;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEITemplateHandler extends TemplateRecipeHandler {

    public class NEICachedRecipe extends CachedRecipe {

        private String recipeID;

        public void setRecipe(Recipe<?> recipe) {
            this.recipeID = recipe.getRecipeID();
        }

        public String getRecipeID() {
            return recipeID;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }
    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe<?> gui, ItemStack stack, List<String> currenttip, int recipe) {
        if (PlayerData.get(Primal.proxy.getClientPlayer())
            .getRecipeDebug()) {
            if (arecipes.get(recipe) instanceof NEICachedRecipe pair) {
                String recipeID = pair.getRecipeID();
                if (recipeID != null) {
                    currenttip.add(recipeID);
                }
            }
        }

        return super.handleItemTooltip(gui, stack, currenttip, recipe);
    }

    @Override
    public String getGuiTexture() {
        return "";
    }

    @Override
    public String getRecipeName() {
        return "";
    }
}
