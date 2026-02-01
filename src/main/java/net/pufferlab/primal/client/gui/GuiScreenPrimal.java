package net.pufferlab.primal.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

public class GuiScreenPrimal extends GuiScreen {

    protected List<net.minecraft.client.gui.GuiButton> invButtonList = new ArrayList<>();

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.thePlayer.closeScreen();
        }
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
        ;
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
