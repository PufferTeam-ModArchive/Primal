package net.pufferlab.primal.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.helper.VertexModifier;
import net.pufferlab.primal.utils.HashUtils;

import org.lwjgl.opengl.GL11;

public abstract class GuiContainerPrimal extends GuiContainer {

    protected List<net.minecraft.client.gui.GuiButton> invButtonList = new ArrayList<>();

    public GuiContainerPrimal(Container p_i1072_1_) {
        super(p_i1072_1_);
    }

    public void drawSimpleItemStack(int x, int y, ItemStack stack, int color, VertexModifier modifier) {
        itemRender.renderWithColor = false;
        float r = HashUtils.getR(color);
        float g = HashUtils.getG(color);
        float b = HashUtils.getB(color);
        if (modifier != null) {
            r = modifier.getR(r);
            g = modifier.getG(g);
            b = modifier.getB(b);
        }
        GL11.glColor4f(r, g, b, 1.0F);
        drawSimpleItemStack(x, y, stack);
        itemRender.renderWithColor = true;
    }

    public int drawStringWithShadow(String text, int x, int y, int color, VertexModifier modifier) {
        float r = HashUtils.getR(color);
        float g = HashUtils.getG(color);
        float b = HashUtils.getB(color);
        if (modifier != null) {
            r = modifier.getR(r);
            g = modifier.getG(g);
            b = modifier.getB(b);
        }
        int newColor = HashUtils.getRGB(r, g, b);
        return drawStringWithShadow(text, x, y, newColor);
    }

    public int drawStringWithShadow(String text, int x, int y, int color) {
        return fontRendererObj.drawString(text, x, y, color, true);
    }

    public void drawSimpleItemStack(int x, int y, ItemStack stack) {
        GL11.glPushMatrix();
        itemRender.zLevel = 200.0F;
        this.zLevel = 200.0F;
        GL11.glEnable(GL11.GL_LIGHTING);
        FontRenderer font = null;
        if (stack != null) font = stack.getItem()
            .getFontRenderer(stack);
        if (font == null) font = fontRendererObj;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
        GL11.glPopMatrix();
    }

    public void drawTexturedModalRect16(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.0625F;
        float f1 = 0.03125F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(
            (double) (x + 0),
            (double) (y + height),
            (double) this.zLevel,
            (double) ((float) (textureX + 0) * f),
            (double) ((float) (textureY + height) * f1));
        tessellator.addVertexWithUV(
            (double) (x + width),
            (double) (y + height),
            (double) this.zLevel,
            (double) ((float) (textureX + width) * f),
            (double) ((float) (textureY + height) * f1));
        tessellator.addVertexWithUV(
            (double) (x + width),
            (double) (y + 0),
            (double) this.zLevel,
            (double) ((float) (textureX + width) * f),
            (double) ((float) (textureY + 0) * f1));
        tessellator.addVertexWithUV(
            (double) (x + 0),
            (double) (y + 0),
            (double) this.zLevel,
            (double) ((float) (textureX + 0) * f),
            (double) ((float) (textureY + 0) * f1));
        tessellator.draw();
    }

    public void drawTexturedModalRect(float x, float y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(
            (double) (x + 0),
            (double) (y + height),
            (double) this.zLevel,
            (double) ((float) (textureX + 0) * f),
            (double) ((float) (textureY + height) * f1));
        tessellator.addVertexWithUV(
            (double) (x + width),
            (double) (y + height),
            (double) this.zLevel,
            (double) ((float) (textureX + width) * f),
            (double) ((float) (textureY + height) * f1));
        tessellator.addVertexWithUV(
            (double) (x + width),
            (double) (y + 0),
            (double) this.zLevel,
            (double) ((float) (textureX + width) * f),
            (double) ((float) (textureY + 0) * f1));
        tessellator.addVertexWithUV(
            (double) (x + 0),
            (double) (y + 0),
            (double) this.zLevel,
            (double) ((float) (textureX + 0) * f),
            (double) ((float) (textureY + 0) * f1));
        tessellator.draw();
    }

    public void drawTooltip(List stringList, int x, int y) {
        this.func_146283_a(stringList, x, y);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (int k = 0; k < this.invButtonList.size(); ++k) {
            ((GuiButton) this.invButtonList.get(k)).drawButton(this.mc, mouseX, mouseY);
        }
    }

    public void renderButtonTooltip(int mouseX, int mouseY) {
        for (GuiButton button : this.buttonList) {
            if (button.func_146115_a()) { // hovered
                button.func_146111_b(mouseX, mouseY);
            }
        }
        for (GuiButton button : this.invButtonList) {
            if (button.func_146115_a()) { // hovered
                button.func_146111_b(mouseX, mouseY);
            }
        }
    }
}
