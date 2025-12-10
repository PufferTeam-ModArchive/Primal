package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
import static codechicken.nei.NEIClientUtils.translate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.BarrelRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIBarrelHandler extends TemplateRecipeHandler {

    public class BarrelPair extends CachedRecipe {

        public BarrelPair(List<ItemStack> ingred, ItemStack ingred2, ItemStack result, ItemStack result2,
            int processingTime) {
            this.ingred = new PositionedStack(ingred, 43, 25, true);
            this.ingred2 = new PositionedStack(Utils.nullableStack(ingred2), 43, 44, true);
            if (result == null & result2 != null) {
                this.result = new PositionedStack(Utils.nullableStack(result2), 114, 44, false);
            } else if (result != null & result2 == null) {
                this.result = new PositionedStack(Utils.nullableStack(result), 114, 25, false);
            } else {
                this.result = new PositionedStack(Utils.nullableStack(result), 114, 25, false);
                this.result2 = new PositionedStack(Utils.nullableStack(result2), 114, 44, false);
            }
            this.processingTime = processingTime;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            List<PositionedStack> list = new ArrayList<>();
            list.add(ingred);
            list.add(ingred2);
            return getCycledIngredients(cycleticks / 20, list);
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        @Override
        public PositionedStack getOtherStack() {
            return result2;
        }

        PositionedStack ingred;
        PositionedStack ingred2;
        PositionedStack result;
        PositionedStack result2;
        int processingTime;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(64, 8, 48, 48), Primal.MODID + ".barrel"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".barrel") && getClass() == NEIBarrelHandler.class) {
            List<BarrelRecipe> recipes = BarrelRecipe.getRecipeList();
            for (BarrelRecipe recipe : recipes) {
                arecipes.add(
                    new NEIBarrelHandler.BarrelPair(
                        recipe.input,
                        recipe.inputLiquidBlock,
                        recipe.output,
                        recipe.outputLiquidBlock,
                        recipe.processingTime));
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<BarrelRecipe> recipes = BarrelRecipe.getRecipeList();
        for (BarrelRecipe recipe : recipes) {
            if (Utils.containsStack(result, recipe.output) || Utils.containsStack(result, recipe.outputLiquid)
                || Utils.containsStack(result, recipe.outputLiquidBlock)) {
                arecipes.add(
                    new NEIBarrelHandler.BarrelPair(
                        recipe.input,
                        recipe.inputLiquidBlock,
                        recipe.output,
                        recipe.outputLiquidBlock,
                        recipe.processingTime));
            }
        }
    }

    @Override
    public void loadUsageRecipes(String inputID, Object... ingredients) {
        super.loadUsageRecipes(inputID, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<BarrelRecipe> recipes = BarrelRecipe.getRecipeList();
        for (BarrelRecipe recipe : recipes) {
            if (Utils.containsList(ingredient, recipe.input) || Utils.containsStack(ingredient, recipe.inputLiquid)
                || Utils.containsStack(ingredient, recipe.inputLiquidBlock)) {
                arecipes.add(
                    new NEIBarrelHandler.BarrelPair(
                        recipe.input,
                        recipe.inputLiquidBlock,
                        recipe.output,
                        recipe.outputLiquidBlock,
                        recipe.processingTime));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return "Barrel";
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/barrel.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".barrel";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);
        BarrelPair recipePair = (BarrelPair) arecipes.get(recipe);
        int timeSecondSmelt = recipePair.processingTime / 20;
        int timeMinuteSmelt = timeSecondSmelt / 60;
        if (timeMinuteSmelt < 1) {
            Minecraft.getMinecraft().fontRenderer
                .drawString(translate("recipe.primal.barrel.smeltTime", timeSecondSmelt), 40, 10, 0xFF000000);
        } else if (timeMinuteSmelt < 2) {
            Minecraft.getMinecraft().fontRenderer
                .drawString(translate("recipe.primal.barrel.smeltTimeMinute", timeMinuteSmelt), 40, 10, 0xFF000000);
        } else {
            Minecraft.getMinecraft().fontRenderer
                .drawString(translate("recipe.primal.barrel.smeltTimeMinutes", timeMinuteSmelt), 40, 10, 0xFF000000);
        }

    }
}
