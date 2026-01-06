package net.pufferlab.primal.client.renderer.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.tileentities.TileEntityMotion;

public class TileEntityGeneratorRenderer extends TileEntityMotionRenderer {

    ModelAxle modelAxle = new ModelAxle();

    @Override
    public void renderModel(TileEntity tileEntity, double x, double y, double z, float partialTicks, float angle) {
        if (tileEntity instanceof TileEntityMotion tef) {
            int axis = tef.axisMeta;
            modelAxle.setAxis(axis);
            modelAxle.render();
        }
    }

    @Override
    public int getAxisToRotate(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityMotion tef) {
            return tef.axisMeta;
        }
        return -1;
    }
}
