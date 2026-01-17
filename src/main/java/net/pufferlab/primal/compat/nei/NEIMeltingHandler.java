package net.pufferlab.primal.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.MeltingRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIMeltingHandler extends TemplateRecipeHandler {

    public class MeltingPair extends CachedRecipe {

        public MeltingPair(List<ItemStack> ingred, ItemStack result, FluidStack resultFS) {
            this.ingred = new PositionedStack(ingred, 43, 25, true);
            this.result = new PositionedStack(result, 114, 25, true);
            this.resultFS = resultFS;
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
        public FluidStack resultFS;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(64, 8, 48, 48), Primal.MODID + ".melting"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".melting") && getClass() == NEIMeltingHandler.class) {
            List<MeltingRecipe> recipes = MeltingRecipe.getRecipeList();
            for (MeltingRecipe recipe : recipes) {
                arecipes.add(new MeltingPair(recipe.input, recipe.outputBlock, recipe.output));
            }
        } else if (outputId.equals("liquid")) {
            if (results[0] instanceof FluidStack stack) {
                List<MeltingRecipe> recipes = MeltingRecipe.getRecipeList();
                for (MeltingRecipe recipe : recipes) {
                    if (Utils.equalsStack(stack, recipe.output)) {
                        arecipes.add(new MeltingPair(recipe.input, recipe.outputBlock, recipe.output));
                    }
                }
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<MeltingRecipe> recipes = MeltingRecipe.getRecipeList();
        for (MeltingRecipe recipe : recipes) {
            if (Utils.equalsStack(result, recipe.output) || Utils.equalsStack(result, recipe.outputBlock)) {
                arecipes.add(new MeltingPair(recipe.input, recipe.outputBlock, recipe.output));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<MeltingRecipe> recipes = MeltingRecipe.getRecipeList();
        for (MeltingRecipe recipe : recipes) {
            if (Utils.containsStack(ingredient, recipe.input)) {
                arecipes.add(new MeltingPair(recipe.input, recipe.outputBlock, recipe.output));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return NEICompat.translateNEI("melting");
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/melting.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".melting";
    }

    @Override
    public boolean mouseClicked(GuiRecipe<?> gui, int button, int recipe) {
        boolean isUsage = button == 1;
        if (arecipes.get(recipe) instanceof MeltingPair cruciblePair) {
            if (cruciblePair.resultFS != null) {
                if (NEICompat.isHovering(cruciblePair.result, gui, recipe)) {
                    return NEICompat.transferFluid(isUsage, cruciblePair.resultFS);
                }
            }
        }
        return super.mouseClicked(gui, button, recipe);
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 106);

    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe<?> gui, ItemStack stack, List<String> currenttip, int recipe) {
        if (arecipes.get(recipe) instanceof MeltingPair cruciblePair) {
            if (cruciblePair.resultFS != null) {
                if (NEICompat.isHovering(cruciblePair.result, gui, recipe)) {
                    currenttip.add(EnumChatFormatting.GRAY + "" + cruciblePair.resultFS.amount + " mB");
                }
            }
        }

        return currenttip;
    }
}
