package net.pufferlab.primal.inventory.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.events.PacketSpeedChange;
import net.pufferlab.primal.tileentities.TileEntityGenerator;

import org.lwjgl.opengl.GL11;

public class GuiGenerator extends GuiScreen {

    private GuiButton addButton;
    private GuiButton removeButton;
    TileEntityGenerator te;

    public GuiGenerator(TileEntityGenerator te) {
        this.te = te;
    }

    @Override
    public void initGui() {
        buttonList.clear();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        addButton = new GuiButton(0, centerX - 25, centerY - 24 - 4, 50, 20, "+");

        removeButton = new GuiButton(1, centerX - 25, centerY + 24 - 4, 50, 20, "-");

        buttonList.add(addButton);
        buttonList.add(removeButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                sendSpeedPacket(true);
                break;
            case 1:
                sendSpeedPacket(false);
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void sendSpeedPacket(boolean addSpeed) {
        if (Primal.proxy.getClientWorld().isRemote) {
            Primal.proxy.sendPacketToServer(new PacketSpeedChange(te, addSpeed, isShiftKeyDown()));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        String s = Float.toString(te.getGeneratedSpeed());
        float scale = 2.0F;

        int w = fontRendererObj.getStringWidth(s);

        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);

        this.fontRendererObj
            .drawString(s, (int) ((centerX - (w * scale) / 2) / scale), (int) (centerY / scale), 0xFFFFFF);

        GL11.glPopMatrix();
    }

    @Override
    public void drawBackground(int tint) {

    }
}
