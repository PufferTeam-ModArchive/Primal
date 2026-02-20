package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
import static codechicken.nei.NEIClientUtils.translate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.BarrelRecipe;
import net.pufferlab.primal.utils.Utils;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIBarrelHandler extends TemplateRecipeHandler {

    public class BarrelPair extends CachedRecipe {

        public BarrelPair(List<ItemStack> ingred, ItemStack ingred2, FluidStack ingredFS, ItemStack result,
            ItemStack result2, FluidStack resultFS, int processingTime) {
            this.ingred = new PositionedStack(ingred, 43, 25, true);
            this.ingred2 = new PositionedStack(ingred2, 43, 44, true);
            if (result == null & result2 != null) {
                this.result = new PositionedStack(result2, 114, 44, false);
                this.resultF = this.result;
            } else if (result != null & result2 == null) {
                this.result = new PositionedStack(result, 114, 25, false);
            } else {
                this.result = new PositionedStack(result, 114, 25, false);
                this.result2 = new PositionedStack(result2, 114, 44, false);
                this.resultF = this.result2;
            }
            this.processingTime = processingTime;
            this.ingredFS = ingredFS;
            this.resultFS = resultFS;
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

        public PositionedStack ingred;
        public PositionedStack ingred2;
        public PositionedStack result;
        public PositionedStack result2;
        public FluidStack ingredFS;
        public FluidStack resultFS;
        public PositionedStack resultF;
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
                        recipe.inputLiquid,
                        recipe.output,
                        recipe.outputLiquidBlock,
                        recipe.outputLiquid,
                        recipe.processingTime));
            }
        } else if (outputId.equals("liquid")) {
            if (results[0] instanceof FluidStack stack) {
                List<BarrelRecipe> recipes = BarrelRecipe.getRecipeList();
                for (BarrelRecipe recipe : recipes) {
                    if (Utils.equalsStack(stack, recipe.outputLiquid)) {
                        arecipes.add(
                            new NEIBarrelHandler.BarrelPair(
                                recipe.input,
                                recipe.inputLiquidBlock,
                                recipe.inputLiquid,
                                recipe.output,
                                recipe.outputLiquidBlock,
                                recipe.outputLiquid,
                                recipe.processingTime));
                    }
                }
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<BarrelRecipe> recipes = BarrelRecipe.getRecipeList();
        for (BarrelRecipe recipe : recipes) {
            if (Utils.equalsStack(result, recipe.output) || Utils.equalsStack(result, recipe.outputLiquid)
                || Utils.equalsStack(result, recipe.outputLiquidBlock)) {
                arecipes.add(
                    new NEIBarrelHandler.BarrelPair(
                        recipe.input,
                        recipe.inputLiquidBlock,
                        recipe.inputLiquid,
                        recipe.output,
                        recipe.outputLiquidBlock,
                        recipe.outputLiquid,
                        recipe.processingTime));
            }
        }
    }

    @Override
    public void loadUsageRecipes(String inputID, Object... ingredients) {
        if (inputID.equals("liquid")) {
            if (ingredients[0] instanceof FluidStack stack) {
                List<BarrelRecipe> recipes = BarrelRecipe.getRecipeList();
                for (BarrelRecipe recipe : recipes) {
                    if (Utils.equalsStack(stack, recipe.inputLiquid)) {
                        arecipes.add(
                            new NEIBarrelHandler.BarrelPair(
                                recipe.input,
                                recipe.inputLiquidBlock,
                                recipe.inputLiquid,
                                recipe.output,
                                recipe.outputLiquidBlock,
                                recipe.outputLiquid,
                                recipe.processingTime));
                    }
                }
            }
        } else super.loadUsageRecipes(inputID, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<BarrelRecipe> recipes = BarrelRecipe.getRecipeList();
        for (BarrelRecipe recipe : recipes) {
            if (Utils.containsStack(ingredient, recipe.input) || Utils.equalsStack(ingredient, recipe.inputLiquid)
                || Utils.equalsStack(ingredient, recipe.inputLiquidBlock)) {
                arecipes.add(
                    new NEIBarrelHandler.BarrelPair(
                        recipe.input,
                        recipe.inputLiquidBlock,
                        recipe.inputLiquid,
                        recipe.output,
                        recipe.outputLiquidBlock,
                        recipe.outputLiquid,
                        recipe.processingTime));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return NEICompat.translateNEI("barrel");
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
    public boolean mouseClicked(GuiRecipe<?> gui, int button, int recipe) {
        boolean isUsage = button == 1;
        BarrelPair barrelPair = (BarrelPair) arecipes.get(recipe);
        if (barrelPair.ingred2 != null && barrelPair.ingredFS != null) {
            if (NEICompat.isHovering(barrelPair.ingred2, gui, recipe)) {
                return NEICompat.transferFluid(isUsage, barrelPair.ingredFS);
            }
        }
        if (barrelPair.resultF != null && barrelPair.resultFS != null) {
            if (NEICompat.isHovering(barrelPair.resultF, gui, recipe)) {
                return NEICompat.transferFluid(isUsage, barrelPair.resultFS);
            }
        }
        return super.mouseClicked(gui, button, recipe);
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

    @Override
    public List<String> handleItemTooltip(GuiRecipe<?> gui, ItemStack stack, List<String> currenttip, int recipe) {
        BarrelPair barrelPair = (BarrelPair) arecipes.get(recipe);
        if (barrelPair.ingred2 != null && barrelPair.ingredFS != null) {
            if (NEICompat.isHovering(barrelPair.ingred2, gui, recipe)) {
                currenttip.add(EnumChatFormatting.GRAY + "" + barrelPair.ingredFS.amount + " mB");
            }
        }
        if (barrelPair.resultF != null && barrelPair.resultFS != null) {
            if (NEICompat.isHovering(barrelPair.resultF, gui, recipe)) {
                currenttip.add(EnumChatFormatting.GRAY + "" + barrelPair.resultFS.amount + " mB");
            }
        }

        return currenttip;
    }
}
