package net.pufferlab.primal.client.renderer.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.client.models.ModelWaterwheel;
import net.pufferlab.primal.tileentities.TileEntityWaterwheel;

public class TileEntityWaterwheelRenderer extends TileEntityMotionRenderer {

    ModelAxle modelAxle = new ModelAxle();
    ModelWaterwheel modelWaterWheel = new ModelWaterwheel();

    @Override
    public void renderModel(TileEntity tileEntity, double x, double y, double z, float partialTicks, float angle) {
        if (tileEntity instanceof TileEntityWaterwheel tef) {
            if (!tef.isExtension) {
                int axis = tef.axisMeta;
                modelAxle.setAxis(axis);
                modelAxle.render();
                modelWaterWheel.setAxis(axis);
                modelWaterWheel.render();
            }
        }
    }
}
