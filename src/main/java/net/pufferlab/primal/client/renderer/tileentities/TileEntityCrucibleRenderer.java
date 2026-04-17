package net.pufferlab.primal.client.renderer.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.client.models.ModelCrucible;
import net.pufferlab.primal.tileentities.TileEntityCrucible;
import net.pufferlab.primal.tileentities.TileEntityForge;

import org.lwjgl.opengl.GL11;

public class TileEntityCrucibleRenderer extends TileEntityPrimalRenderer {

    ModelCrucible model = new ModelCrucible();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        if (tileEntity instanceof TileEntityCrucible tef) {
            TileEntity teBelow = tef.getWorldObj()
                .getTileEntity(tef.xCoord, tef.yCoord - 1, tef.zCoord);
            float offsetY = 0.0F;
            if (teBelow instanceof TileEntityForge tef2) {
                int blockBelowMeta = tef2.getCurrentFuelStages();
                offsetY = 0.125F + 0.0625F * (4 - blockBelowMeta);
            }
            GL11.glTranslated(x + 0.5, y - offsetY, z + 0.5);
            renderHeat.renderHeat(model, tef.getTemperature());
        }
        GL11.glPopMatrix();
    }
}
