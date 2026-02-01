package net.pufferlab.primal.client.gui;

import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.inventory.ContainerKnapping;
import net.pufferlab.primal.network.packets.PacketKnappingClick;

import org.lwjgl.opengl.GL11;

public class GuiKnapping extends GuiContainerPrimal {

    private static final ResourceLocation texture = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/knapping.png");
    public static final ResourceLocation textureKnife = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/knapping_needs_knife.png");

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
            .bindTexture(texture);
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
    protected void renderToolTip(ItemStack itemIn, int x, int y) {
        super.renderToolTip(itemIn, x, y);
    }

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
                boolean newState = true;
                if (this.containerKnapping.type.needsKnife) {
                    int knifeIndex = containerKnapping.findKnifeIndex();
                    if (knifeIndex == -1) {
                        newState = false;
                    }
                }
                if (newState) {
                    this.containerKnapping.knappingIcons[clickedX][clickedY] = true;
                    this.sendKnappingPacket(clickedX, clickedY);
                }
            }
        }
    }

    public void sendKnappingPacket(int x, int y) {
        if (this.containerKnapping.invPlayer.player.worldObj.isRemote) {
            Primal.proxy.sendPacketToServer(new PacketKnappingClick(x, y));
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
                this.mc.getTextureManager()
                    .bindTexture(this.containerKnapping.type.resourceLocation);
                this.drawTexturedModalRect16(absoluteScreenX, absoluteScreenY, 0, offsetY, iconSize, iconSize);
            }
        }
    }

}
