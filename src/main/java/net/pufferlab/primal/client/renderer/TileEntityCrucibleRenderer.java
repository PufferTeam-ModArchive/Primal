package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.blocks.BlockForge;
import net.pufferlab.primal.client.models.ModelCrucible;
import net.pufferlab.primal.tileentities.TileEntityCrucible;

import org.lwjgl.opengl.GL11;

public class TileEntityCrucibleRenderer extends TileEntitySpecialRenderer {

    ModelCrucible model = new ModelCrucible();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        if (tileEntity instanceof TileEntityCrucible tef) {
            Block blockBelow = tef.getWorldObj()
                .getBlock(tef.xCoord, tef.yCoord - 1, tef.zCoord);
            int blockBelowMeta = tef.getWorldObj()
                .getBlockMetadata(tef.xCoord, tef.yCoord - 1, tef.zCoord);
            float offsetY = 0.0F;
            if (blockBelow instanceof BlockForge) {
                offsetY = 0.125F + 0.0625F * (4 - blockBelowMeta);
            }
            GL11.glTranslated(x + 0.5, y - offsetY, z + 0.5);
            RenderTemperature.renderTemperature(model, tef.temperature);
        }
        GL11.glPopMatrix();
    }
}
