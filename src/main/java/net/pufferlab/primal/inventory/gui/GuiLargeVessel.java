package net.pufferlab.primal.inventory.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.inventory.container.ContainerLargeVessel;
import net.pufferlab.primal.tileentities.TileEntityLargeVessel;

import org.lwjgl.opengl.GL11;

public class GuiLargeVessel extends GuiContainerPrimal {

    private static final ResourceLocation texture = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/large_vessel.png");
    private static final ResourceLocation textureLocked = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/large_vessel_locked.png");
    public TileEntityLargeVessel tileLargeVessel;
    public boolean isOpen;

    public GuiLargeVessel(InventoryPlayer inv, TileEntityLargeVessel te) {
        super(new ContainerLargeVessel(inv, te));
        this.tileLargeVessel = te;
        this.isOpen = te.isOpen;
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
        if (this.isOpen) {
            this.mc.getTextureManager()
                .bindTexture(texture);
        } else {
            this.mc.getTextureManager()
                .bindTexture(textureLocked);
        }
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
