package net.pufferlab.primitivelife.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.Utils;
import net.pufferlab.primitivelife.recipes.PitKilnRecipes;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIPitKilnHandler extends TemplateRecipeHandler {

    public class PitKilnPair extends CachedRecipe {

        public PitKilnPair(ItemStack ingred, ItemStack result) {
            this.ingred = new PositionedStack(ingred, 43, 44, true);
            this.result = new PositionedStack(result, 119, 24, false);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Collections.singletonList(ingred));
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
        transferRects.add(new RecipeTransferRect(new Rectangle(59, 8, 48, 48), PrimitiveLife.MODID + ".pit_kiln"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(PrimitiveLife.MODID + ".pit_kiln") && getClass() == NEIPitKilnHandler.class) {
            Map<ItemStack, ItemStack> recipes = PitKilnRecipes.getRecipeMap();
            for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
                arecipes.add(new NEIPitKilnHandler.PitKilnPair(recipe.getKey(), recipe.getValue()));
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<ItemStack, ItemStack> recipes = PitKilnRecipes.getRecipeMap();
        for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.containsStack(recipe.getValue(), result)) {
                arecipes.add(new NEIPitKilnHandler.PitKilnPair(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @Override
    public void loadUsageRecipes(String inputID, Object... ingredients) {
        super.loadUsageRecipes(inputID, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Map<ItemStack, ItemStack> recipes = PitKilnRecipes.getRecipeMap();
        for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
            if (Utils.containsStack(recipe.getKey(), ingredient)) {
                arecipes.add(new NEIPitKilnHandler.PitKilnPair(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return "Pit Kiln";
    }

    @Override
    public String getGuiTexture() {
        return PrimitiveLife.MODID + ":textures/gui/nei/pit_kiln.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return PrimitiveLife.MODID + ".pit_kiln";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
    }
}
