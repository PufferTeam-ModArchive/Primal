package net.pufferlab.primal.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.inventory.ContainerCrucible;
import net.pufferlab.primal.tileentities.TileEntityCrucible;

import org.lwjgl.opengl.GL11;

public class GuiCrucible extends GuiContainerPrimal {

    private static final ResourceLocation texture = new ResourceLocation(
        Primal.MODID,
        "textures/gui/container/crucible.png");
    public TileEntityCrucible tileCrucible;

    public GuiCrucible(InventoryPlayer inv, TileEntityCrucible te) {
        super(new ContainerCrucible(inv, te));
        this.tileCrucible = te;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.tileCrucible.hasCustomInventoryName() ? this.tileCrucible.getInventoryName()
            : I18n.format(this.tileCrucible.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj
            .drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
            .bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
