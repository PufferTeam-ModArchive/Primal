package net.pufferlab.primal.client.gui;

import java.util.Collections;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.pufferlab.primal.recipes.AnvilAction;
import net.pufferlab.primal.recipes.AnvilOrder;

public class GuiButtonAnvilStep extends GuiButtonPrimal {

    private final GuiAnvilWork gui;
    AnvilAction action;
    AnvilOrder order;

    public GuiButtonAnvilStep(GuiAnvilWork gui, AnvilAction action, AnvilOrder order, int stateName, int id,
        int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
        super(stateName, id, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
        this.gui = gui;
        this.action = action;
        this.order = order;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition
            && mouseX < this.xPosition + this.width
            && mouseY < this.yPosition + this.height;
    }

    @Override
    public void renderTooltip(int mouseX, int mouseY) {
        if (this.action == null) return;
        if (this.order == null) {
            this.gui.drawTooltip(Collections.singletonList(this.action.getTranslatedName()), mouseX, mouseY);
        } else {
            this.gui.drawTooltip(
                Collections
                    .singletonList(this.action.getRecipeTranslatedName() + ", " + this.order.getTranslatedName()),
                mouseX,
                mouseY);
        }
    }

    @Override
    public void func_146113_a(SoundHandler soundHandlerIn) {}

}
