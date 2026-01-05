package net.pufferlab.primal.client.renderer;

import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.tileentities.IMotion;

import org.lwjgl.opengl.GL11;

public class TileEntityMotionRenderer extends TileEntityPrimalRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        renderTileEntityMotionAt(tileEntity, x, y, z, partialTicks);
    }

    public void renderTileEntityMotionAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        if (tileEntity instanceof IMotion tef) {
            float offset = 0.0F;
            if (tef.getSpeedModifier() < 1) {
                offset = 0.5F;
            }
            float angle = RenderMotion.getInterpolatedRotationDeg(tef.getSpeed(), offset, partialTicks);
            int axis = getAxisToRotate(tileEntity);
            if (axis != -1) {
                if (axis == 0) {
                    GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
                } else if (axis == 1) {
                    GL11.glRotatef(angle, 0.0F, 0.0F, 1.0F);
                } else if (axis == 2) {
                    GL11.glRotatef(angle, 1.0F, 0.0F, 0.0F);
                }
            }
            renderModel(tileEntity, x, y, z, partialTicks, angle);
        }
        GL11.glPopMatrix();
    }

    public int getAxisToRotate(TileEntity tileEntity) {
        return -1;
    }

    public void renderModel(TileEntity tileEntity, double x, double y, double z, float partialTicks, float angle) {

    }
}
