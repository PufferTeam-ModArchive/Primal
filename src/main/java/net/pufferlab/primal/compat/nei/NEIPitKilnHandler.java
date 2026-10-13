package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.PitKilnRecipe;
import net.pufferlab.primal.utils.Utils;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;

public class NEIPitKilnHandler extends NEITemplateHandler {

    public class PitKilnPair extends NEICachedRecipe {

        public PitKilnPair(PitKilnRecipe recipe) {
            this(recipe.input, recipe.output);
            setRecipe(recipe);
        }

        public PitKilnPair(List<ItemStack> ingred, ItemStack result) {
            this.ingred = new PositionedStack(ingred, 43, 44, true);
            this.result = new PositionedStack(result, 119, 24, false);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, Utils.asList(ingred));
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
            List<PitKilnRecipe> recipes = PitKilnRecipe.getRecipeList();
            for (PitKilnRecipe recipe : recipes) {
                arecipes.add(new NEIPitKilnHandler.PitKilnPair(recipe));
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<PitKilnRecipe> recipes = PitKilnRecipe.getRecipeList();
        for (PitKilnRecipe recipe : recipes) {
            if (Utils.equalsStack(recipe.output, result)) {
                arecipes.add(new NEIPitKilnHandler.PitKilnPair(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<PitKilnRecipe> recipes = PitKilnRecipe.getRecipeList();
        for (PitKilnRecipe recipe : recipes) {
            if (Utils.containsStack(recipe.input, ingredient)) {
                arecipes.add(new NEIPitKilnHandler.PitKilnPair(recipe));
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
        changeTexture(Utils.asResource(getGuiTexture()));
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
    }
}
