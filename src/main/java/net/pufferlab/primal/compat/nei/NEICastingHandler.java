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
import net.pufferlab.primal.recipes.CastingRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEICastingHandler extends TemplateRecipeHandler {

    public class CastingPair extends CachedRecipe {

        public CastingPair(ItemStack cast, ItemStack ingred, ItemStack result, FluidStack ingredFS) {
            this.ingred = new PositionedStack(ingred, 43, 25, true);
            this.cast = new PositionedStack(cast, 43, 4, true);
            this.result = new PositionedStack(result, 114, 25, true);
            this.ingredFS = ingredFS;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, Collections.singletonList(ingred));
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        @Override
        public PositionedStack getOtherStack() {
            return cast;
        }

        public PositionedStack cast;
        public PositionedStack ingred;
        public PositionedStack result;
        public FluidStack ingredFS;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(64, 8, 48, 48), Primal.MODID + ".casting"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(Primal.MODID + ".casting") && getClass() == NEICastingHandler.class) {
            List<CastingRecipe> recipes = CastingRecipe.getRecipeList();
            for (CastingRecipe recipe : recipes) {
                arecipes.add(new CastingPair(recipe.cast, recipe.inputBlock, recipe.output, recipe.input));
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<CastingRecipe> recipes = CastingRecipe.getRecipeList();
        for (CastingRecipe recipe : recipes) {
            if (Utils.equalsStack(result, recipe.output)) {
                arecipes.add(new CastingPair(recipe.cast, recipe.inputBlock, recipe.output, recipe.input));
            }
        }
    }

    @Override
    public void loadUsageRecipes(String inputID, Object... ingredients) {
        if (inputID.equals("liquid")) {
            if (ingredients[0] instanceof FluidStack stack) {
                List<CastingRecipe> recipes = CastingRecipe.getRecipeList();
                for (CastingRecipe recipe : recipes) {
                    if (Utils.equalsStack(stack, recipe.input)) {
                        arecipes.add(new CastingPair(recipe.cast, recipe.inputBlock, recipe.output, recipe.input));
                    }
                }
            }
        } else super.loadUsageRecipes(inputID, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<CastingRecipe> recipes = CastingRecipe.getRecipeList();
        for (CastingRecipe recipe : recipes) {
            if (Utils.equalsStack(ingredient, recipe.input) || Utils.equalsStack(ingredient, recipe.inputBlock)
                || Utils.equalsStack(ingredient, recipe.cast)) {
                arecipes.add(new CastingPair(recipe.cast, recipe.inputBlock, recipe.output, recipe.input));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return NEICompat.translateNEI("casting");
    }

    @Override
    public String getGuiTexture() {
        return Primal.MODID + ":textures/gui/nei/casting.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return Primal.MODID + ".casting";
    }

    @Override
    public boolean mouseClicked(GuiRecipe<?> gui, int button, int recipe) {
        boolean isUsage = button == 1;
        if (arecipes.get(recipe) instanceof CastingPair cruciblePair) {
            if (cruciblePair.ingredFS != null) {
                if (NEICompat.isHovering(cruciblePair.ingred, gui, recipe)) {
                    return NEICompat.transferFluid(isUsage, cruciblePair.ingredFS);
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
        if (arecipes.get(recipe) instanceof CastingPair cruciblePair) {
            if (cruciblePair.ingredFS != null) {
                if (NEICompat.isHovering(cruciblePair.ingred, gui, recipe)) {
                    currenttip.add(EnumChatFormatting.GRAY + "" + cruciblePair.ingredFS.amount + " mB");
                }
            }
        }

        return currenttip;
    }
}
