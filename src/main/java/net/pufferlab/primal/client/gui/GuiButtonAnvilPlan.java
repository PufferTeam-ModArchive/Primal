package net.pufferlab.primal.client.gui;

import net.minecraft.client.Minecraft;

public class GuiButtonAnvilPlan extends GuiButtonPrimal {

    private final GuiAnvilPlan gui;
    public int recipeID;

    public GuiButtonAnvilPlan(GuiAnvilPlan gui, int stateName, int id, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_,
        int recipeID) {
        super(stateName, id, p_i1021_3_, p_i1021_4_, p_i1021_5_, "");
        this.gui = gui;
        this.recipeID = recipeID;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.drawTexturedModalRect(this.xPosition - 1, this.yPosition - 1, 176, 0, this.width, this.height);
        }

        this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition
            && mouseX < this.xPosition + this.width
            && mouseY < this.yPosition + this.height;
    }
}
