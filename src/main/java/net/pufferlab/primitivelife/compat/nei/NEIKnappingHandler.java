package net.pufferlab.primitivelife.compat.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.recipes.KnappingPattern;
import net.pufferlab.primitivelife.recipes.KnappingRecipes;
import net.pufferlab.primitivelife.recipes.KnappingType;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIKnappingHandler extends TemplateRecipeHandler {

    public class KnappingPair extends CachedRecipe {

        public KnappingPair(int type, boolean[][] icons, ItemStack result) {
            ItemStack ingred = new ItemStack(Items.feather, 0, 1);
            if (type == KnappingType.clay) {
                ingred = new ItemStack(Items.clay_ball, 0, 1);
            } else if (type == KnappingType.flint) {
                ingred = new ItemStack(Items.flint, 0, 1);
            }
            this.ingred = new PositionedStack(ingred, 138, 10, true);
            this.result = new PositionedStack(result, 138, 40, false);
            this.type = type;
            this.icons = icons;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Collections.singletonList(ingred));
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        final PositionedStack ingred;
        final PositionedStack result;
        final int type;
        final boolean[][] icons;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(80, 8, 48, 48), PrimitiveLife.MODID + ".knapping"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(PrimitiveLife.MODID + ".knapping") && getClass() == NEIKnappingHandler.class) {
            List<KnappingPattern> recipes = KnappingRecipes.getRecipeList();
            for (KnappingPattern pattern : recipes) {
                arecipes.add(new KnappingPair(pattern.type, pattern.pattern, pattern.output));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        List<KnappingPattern> recipes = KnappingRecipes.getRecipeList();
        for (KnappingPattern pattern : recipes) {
            if (NEIServerUtils.areStacksSameType(pattern.output, result)) {
                arecipes.add(new KnappingPair(pattern.type, pattern.pattern, pattern.output));
            }
        }
    }

    @Override
    public void loadUsageRecipes(String inputID, Object... ingredients) {
        super.loadUsageRecipes(inputID, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {}

    @Override
    public String getRecipeName() {
        return "Knapping";
    }

    @Override
    public String getGuiTexture() {
        return PrimitiveLife.MODID + ":textures/gui/container/knapping.png";
    }

    @Override
    public String getOverlayIdentifier() {
        return PrimitiveLife.MODID + ".knapping";
    }

    @Override
    public void drawBackground(int recipe) {
        KnappingPair pair = (KnappingPair) arecipes.get(recipe);
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(-1, 0, 4, 5, 176, 95);
        this.drawIcons(0, 0, pair.type, pair.icons);
    }

    public void drawIcons(int guiX, int guiY, int type, boolean[][] icons) {
        final int offsetX = 10;
        final int offsetY = 10;
        final int iconSize = 16;
        final int step = 16;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                boolean currentState = icons[x][y];
                int absoluteScreenX = guiX + offsetX + (x * step);
                int absoluteScreenY = guiY + offsetY + (y * step);
                int offsetY2 = 0;
                if (currentState) {
                    offsetY2 = 16;
                }
                GuiDraw.drawTexturedModalRect(
                    absoluteScreenX,
                    absoluteScreenY,
                    180 + (type * 16),
                    offsetY2,
                    iconSize,
                    iconSize);
            }
        }
    }
}
