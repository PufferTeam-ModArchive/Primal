package net.pufferlab.primal.client.renderer.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.client.models.ModelWindmill;
import net.pufferlab.primal.tileentities.TileEntityWindmill;

public class TileEntityWindmillRenderer extends TileEntityMotionRenderer {

    ModelAxle modelAxle = new ModelAxle();
    ModelWindmill modelWindmill = new ModelWindmill(4);

    @Override
    public void renderModel(TileEntity tileEntity, double x, double y, double z, float partialTicks, float angle) {
        if (tileEntity instanceof TileEntityWindmill tef) {
            int axis = tef.axisMeta;
            modelAxle.setAxis(axis);
            modelAxle.render();
            modelWindmill.setAxis(axis);
            modelWindmill.render();
        }
    }
}
