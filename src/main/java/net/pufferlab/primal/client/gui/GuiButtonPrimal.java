package net.pufferlab.primal.client.gui;

import net.minecraft.client.gui.GuiButton;

public class GuiButtonPrimal extends GuiButton {

    public GuiButtonPrimal(int stateName, int id, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
        super(stateName, id, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
    }

    public boolean isHovered() {
        return func_146115_a();
    }

    @Override
    public void func_146111_b(int mouseX, int mouseY) {
        renderTooltip(mouseX, mouseY);
    }

    public void renderTooltip(int mouseX, int mouseY) {}
}
