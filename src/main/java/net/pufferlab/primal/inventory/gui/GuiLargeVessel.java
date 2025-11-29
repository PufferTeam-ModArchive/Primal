package net.pufferlab.primal.inventory.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.inventory.container.ContainerLargeVessel;
import net.pufferlab.primal.tileentities.TileEntityLargeVessel;

import org.lwjgl.opengl.GL11;

public class GuiLargeVessel extends GuiContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/large_vessel.png");
    public TileEntityLargeVessel tileLargeVessel;

    public GuiLargeVessel(InventoryPlayer inv, TileEntityLargeVessel te) {
        super(new ContainerLargeVessel(inv, te));
        this.tileLargeVessel = te;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.tileLargeVessel.hasCustomInventoryName() ? this.tileLargeVessel.getInventoryName()
            : I18n.format(this.tileLargeVessel.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj
            .drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
            .bindTexture(TEXTURE);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
