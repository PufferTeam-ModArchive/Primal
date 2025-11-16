package net.pufferlab.primitivelife.inventory.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.events.PacketKnappingClick;
import net.pufferlab.primitivelife.inventory.container.ContainerKnapping;

import org.lwjgl.opengl.GL11;

public class GuiKnapping extends GuiContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
        PrimitiveLife.MODID,
        "textures/gui/container/knapping.png");

    ContainerKnapping containerKnapping;

    public GuiKnapping(Container p_i1072_1_) {
        super(p_i1072_1_);
        this.containerKnapping = (ContainerKnapping) p_i1072_1_;
        this.xSize = 176;
        this.ySize = 186;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); // Reset color
        this.mc.getTextureManager()
            .bindTexture(TEXTURE);
        int u = (width - xSize) / 2;
        int v = (height - ySize) / 2;
        this.drawTexturedModalRect(u, v, 0, 0, xSize, ySize);

        this.drawIcons();
    }

    final int offsetX = 10;
    final int offsetY = 10;
    final int iconSize = 16;
    final int step = 16;
    final int gridSize = 5;

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);

        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;

        int relativeX = mouseX - (guiX + offsetX);
        int relativeY = mouseY - (guiY + offsetY);

        int totalGridWidth = gridSize * iconSize;

        if (relativeX >= 0 && relativeX < totalGridWidth && relativeY >= 0 && relativeY < totalGridWidth) {
            int clickedX = relativeX / step;
            int clickedY = relativeY / step;

            if (!this.containerKnapping.knappingIcons[clickedX][clickedY]) {
                this.containerKnapping.knappingIcons[clickedX][clickedY] = true;
                this.sendKnappingPacket(clickedX, clickedY);
            }
        }
    }

    public void sendKnappingPacket(int x, int y) {
        if (this.containerKnapping.invPlayer.player.worldObj.isRemote) {
            PrimitiveLife.networkWrapper.sendToServer(new PacketKnappingClick(x, y));
        }
    }

    public void drawIcons() {
        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                boolean currentState = this.containerKnapping.knappingIcons[x][y];
                int absoluteScreenX = guiX + offsetX + (x * step);
                int absoluteScreenY = guiY + offsetY + (y * step);
                int offsetY = 0;
                if (currentState) {
                    offsetY = 16;
                }
                this.drawTexturedModalRect(
                    absoluteScreenX,
                    absoluteScreenY,
                    180 + (this.containerKnapping.type * 16),
                    offsetY,
                    iconSize,
                    iconSize);
            }
        }
    }

}
