package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.AlloyingRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIAlloyingHandler extends TemplateRecipeHandler {

    public class AlloyingPair extends CachedRecipe {

        public AlloyingPair(ItemStack[] ingred, ItemStack result, FluidStack resultFS, FluidStack[] inputsFS, int[] min,
            int[] max) {
            if (inputsFS.length == 2) {
                this.result = new PositionedStack(result, 114, 25, true);
                this.ingred = new PositionedStack(ingred[0], 46, 25, true);
                this.ingredFS = inputsFS[0];
                this.ingred2 = new PositionedStack(ingred[1], 46 - 31, 25, true);
                this.ingred2FS = inputsFS[1];
            }
            if (inputsFS.length == 3) {
                this.result = new PositionedStack(result, 137, 25, true);
                this.ingred3 = new PositionedStack(ingred[2], 77, 25, true);
                this.ingred3FS = inputsFS[2];
            }
            this.min = min;
            this.max = max;
            this.inputsFS = inputsFS;
            this.resultFS = resultFS;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            List<PositionedStack> list = new ArrayList<>();
            if (inputsFS.length == 2) {
                list.add(ingred);
                list.add(ingred2);
            }
            if (inputsFS.length == 3) {
                list.add(ingred3);
            }
            return getCycledIngredients(cycleticks / 20, list);
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        public PositionedStack ingred;
        public PositionedStack ingred2;
        public PositionedStack ingred3;
        public PositionedStack result;
        public FluidStack[] inputsFS;
        public FluidStack ingredFS;
        public FluidStack ingred2FS;
        public FluidStack ingred3FS;
        public FluidStack resultFS;
        public int[] min;
        public int[] max;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(64, 8, 48, 48), Primal.MODID + ".alloying"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".alloying") && getClass() == NEIAlloyingHandler.class) {
            List<AlloyingRecipe> recipes = AlloyingRecipe.getRecipeList();
            for (AlloyingRecipe recipe : recipes) {
                arecipes.add(
                    new AlloyingPair(
                        recipe.inputsBlock,
                        recipe.outputsBlock,
                        recipe.outputs,
                        recipe.inputs,
                        recipe.minPercentage,
                        recipe.maxPercentage));
            }
        } else if (outputId.equals("liquid")) {
            if (results[0] instanceof FluidStack stack) {
                List<AlloyingRecipe> recipes = AlloyingRecipe.getRecipeList();
                for (AlloyingRecipe recipe : recipes) {
                    if (Utils.equalsStack(stack, recipe.outputs)) {
                        arecipes.add(
                            new AlloyingPair(
                                recipe.inputsBlock,
                                recipe.outputsBlock,
                                recipe.outputs,
                                recipe.inputs,
                                recipe.minPercentage,
                                recipe.maxPercentage));
                    }
                }
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<AlloyingRecipe> recipes = AlloyingRecipe.getRecipeList();
        for (AlloyingRecipe recipe : recipes) {
            if (Utils.equalsStack(result, recipe.outputs) || Utils.equalsStack(result, recipe.outputsBlock)) {
                arecipes.add(
                    new AlloyingPair(
                        recipe.inputsBlock,
                        recipe.outputsBlock,
                        recipe.outputs,
                        recipe.inputs,
                        recipe.minPercentage,
                        recipe.maxPercentage));
            }
        }
    }

    @Override
    public void loadUsageRecipes(String inputID, Object... ingredients) {
        if (inputID.equals("liquid")) {
            if (ingredients[0] instanceof FluidStack stack) {
                List<AlloyingRecipe> recipes = AlloyingRecipe.getRecipeList();
                for (AlloyingRecipe recipe : recipes) {
                    if (Utils.containsStack(stack, recipe.inputs)) {
                        arecipes.add(
                            new AlloyingPair(
                                recipe.inputsBlock,
                                recipe.outputsBlock,
                                recipe.outputs,
                                recipe.inputs,
                                recipe.minPercentage,
                                recipe.maxPercentage));
                    }
                }
            }
        } else super.loadUsageRecipes(inputID, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<AlloyingRecipe> recipes = AlloyingRecipe.getRecipeList();
        for (AlloyingRecipe recipe : recipes) {
            if (Utils.containsStack(ingredient, recipe.inputsBlock)) {
                arecipes.add(
                    new AlloyingPair(
                        recipe.inputsBlock,
                        recipe.outputsBlock,
                        recipe.outputs,
                        recipe.inputs,
                        recipe.minPercentage,
                        recipe.maxPercentage));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return "Alloying";
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/alloying_1.png";
    }

    public String getGuiTexture3() {
        return Primal.MODID + ":textures/gui/nei/alloying_2.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".alloying";
    }

    @Override
    public boolean mouseClicked(GuiRecipe<?> gui, int button, int recipe) {
        boolean isUsage = button == 1;
        if (arecipes.get(recipe) instanceof AlloyingPair cruciblePair) {
            if (cruciblePair.resultFS != null) {
                if (NEICompat.isHovering(cruciblePair.result, gui, recipe)) {
                    return NEICompat.transferFluid(isUsage, cruciblePair.resultFS);
                }
            }
            if (cruciblePair.ingredFS != null) {
                if (NEICompat.isHovering(cruciblePair.ingred, gui, recipe)) {
                    return NEICompat.transferFluid(isUsage, cruciblePair.ingredFS);
                }
            }
            if (cruciblePair.ingred2FS != null) {
                if (NEICompat.isHovering(cruciblePair.ingred2, gui, recipe)) {
                    return NEICompat.transferFluid(isUsage, cruciblePair.ingred2FS);
                }
            }
            if (cruciblePair.ingred3FS != null) {
                if (NEICompat.isHovering(cruciblePair.ingred3, gui, recipe)) {
                    return NEICompat.transferFluid(isUsage, cruciblePair.ingred3FS);
                }
            }
        }
        return super.mouseClicked(gui, button, recipe);
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        if (arecipes.get(recipe) instanceof AlloyingPair cruciblePair) {
            if (cruciblePair.inputsFS.length == 2) {
                changeTexture(getGuiTexture());
            }

            if (cruciblePair.inputsFS.length == 3) {
                changeTexture(getGuiTexture3());
            }
            drawTexturedModalRect(0, 0, 5, 11, 166, 106);

            if (cruciblePair.inputsFS.length == 2) {
                Minecraft.getMinecraft().fontRenderer
                    .drawString(cruciblePair.min[0] + "-" + cruciblePair.max[0] + "%", 46, 10, 0xFF000000);
                Minecraft.getMinecraft().fontRenderer
                    .drawString(cruciblePair.min[1] + "-" + cruciblePair.max[1] + "%", 46 - 40, 10, 0xFF000000);
            }
            if (cruciblePair.inputsFS.length == 3) {
                Minecraft.getMinecraft().fontRenderer
                    .drawString(cruciblePair.min[2] + "-" + cruciblePair.max[2] + "%", 46 + 40, 10, 0xFF000000);
            }
        }

    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe<?> gui, ItemStack stack, List<String> currenttip, int recipe) {
        if (arecipes.get(recipe) instanceof AlloyingPair cruciblePair) {
            if (cruciblePair.ingredFS != null) {
                if (NEICompat.isHovering(cruciblePair.ingred, gui, recipe)) {
                    currenttip
                        .add(EnumChatFormatting.GRAY + "" + cruciblePair.min[0] + "% - " + cruciblePair.max[0] + " %");
                }
            }
            if (cruciblePair.ingred2FS != null) {
                if (NEICompat.isHovering(cruciblePair.ingred2, gui, recipe)) {
                    currenttip
                        .add(EnumChatFormatting.GRAY + "" + cruciblePair.min[1] + "% - " + cruciblePair.max[1] + " %");
                }
            }
            if (cruciblePair.ingred3FS != null) {
                if (NEICompat.isHovering(cruciblePair.ingred3, gui, recipe)) {
                    currenttip
                        .add(EnumChatFormatting.GRAY + "" + cruciblePair.min[2] + "% - " + cruciblePair.max[2] + " %");
                }
            }
        }

        return currenttip;
    }
}
