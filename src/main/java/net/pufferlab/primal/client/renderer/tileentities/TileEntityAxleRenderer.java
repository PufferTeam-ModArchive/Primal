package net.pufferlab.primal.client.renderer.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.client.models.ModelGear;
import net.pufferlab.primal.tileentities.TileEntityAxle;

public class TileEntityAxleRenderer extends TileEntityMotionRenderer {

    ModelAxle modelAxle = new ModelAxle();
    ModelGear modelGearPos = new ModelGear();
    ModelGear modelGearNeg = new ModelGear();

    @Override
    public void renderModel(TileEntity tileEntity, double x, double y, double z, float partialTicks, float angle) {
        if (tileEntity instanceof TileEntityAxle tef) {
            int axis = tef.axisMeta;
            if (tef.hasGearPos) {
                modelGearPos.setAxis(axis);
                modelGearPos.render();
            }
            if (tef.hasGearNeg) {
                modelGearNeg.setAxisReversed(axis);
                modelGearNeg.render();
            }
            modelAxle.setAxis(axis);
            modelAxle.render();
        }
    }
}
