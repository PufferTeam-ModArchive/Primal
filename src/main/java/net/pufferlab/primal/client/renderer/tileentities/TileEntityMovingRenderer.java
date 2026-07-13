package net.pufferlab.primal.client.renderer.tileentities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.tileentities.TileEntityMoving;

import org.lwjgl.opengl.GL11;

public class TileEntityMovingRenderer extends TileEntityPrimalRenderer {

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double x, double y, double z, float partialTicks) {
        if (p_147500_1_ instanceof TileEntityMoving tef) {
            if (tef.virtualBlock != null && renderBlocks != null) {
                Tessellator tessellator = Tessellator.instance;
                this.bindTexture(TextureMap.locationBlocksTexture);
                RenderHelper.disableStandardItemLighting();
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDisable(GL11.GL_CULL_FACE);

                if (Minecraft.isAmbientOcclusionEnabled()) {
                    GL11.glShadeModel(GL11.GL_SMOOTH);
                } else {
                    GL11.glShadeModel(GL11.GL_FLAT);
                }

                // tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);

                int xi = tef.xCoord;
                int yi = tef.yCoord;
                int zi = tef.zCoord;

                tef.virtualBlock.renderISBRH(tef.getWorld(), xi, yi, zi, renderBlocks, partialTicks);
                // tef.virtualBlock.renderTESR(tef.getWorld(), xi, yi, zi, partialTicks);
                RenderHelper.enableStandardItemLighting();
            }
        }

    }
}
