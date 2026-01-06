package net.pufferlab.primal.client.renderer;

import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.client.models.ModelWaterWheel;
import net.pufferlab.primal.tileentities.TileEntityMotion;
import net.pufferlab.primal.tileentities.TileEntityWaterWheel;

public class TileEntityWaterWheelRenderer extends TileEntityMotionRenderer {

    ModelAxle modelAxle = new ModelAxle();
    ModelWaterWheel modelWaterWheel = new ModelWaterWheel();

    @Override
    public void renderModel(TileEntity tileEntity, double x, double y, double z, float partialTicks, float angle) {
        if (tileEntity instanceof TileEntityWaterWheel tef) {
            if (!tef.isExtension) {
                int axis = tef.axisMeta;
                modelAxle.setAxis(axis);
                modelAxle.render();
                modelWaterWheel.setAxis(axis);
                modelWaterWheel.render();
            }
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
