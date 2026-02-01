package net.pufferlab.primal.inventory.gui;

import net.minecraft.client.gui.GuiScreen;

public class GuiScreenPrimal extends GuiScreen {

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.thePlayer.closeScreen();
        }
    }
}
