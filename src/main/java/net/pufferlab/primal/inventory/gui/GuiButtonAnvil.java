package net.pufferlab.primal.inventory.gui;

import java.util.Collections;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.pufferlab.primal.recipes.AnvilAction;

import org.lwjgl.opengl.GL11;

public class GuiButtonAnvil extends GuiButtonPrimal {

    private final GuiAnvilWork gui;
    AnvilAction action;

    public GuiButtonAnvil(GuiAnvilWork gui, int stateName, int id, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_,
        String p_i1021_6_) {
        super(stateName, id, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
        this.action = AnvilAction.get(this.id);
        this.gui = gui;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition
                && mouseX < this.xPosition + this.width
                && mouseY < this.yPosition + this.height;
            int k = 0;

            // Button Under
            if (this.field_146123_n) {
                k += this.height;
            }

            int u = 219;
            if (this.id > 3) {
                u += this.width;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, u, k, this.width, this.height);

            // Icon On TOP
            int k2 = 240;
            int u2 = this.id * 16;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, u2, k2, this.width, this.height);
        }
    }

    @Override
    public void renderTooltip(int mouseX, int mouseY) {
        this.gui.drawTooltip(Collections.singletonList(this.action.getTranslatedName()), mouseX, mouseY);
    }

    @Override
    public void func_146113_a(SoundHandler soundHandlerIn) {}
}
