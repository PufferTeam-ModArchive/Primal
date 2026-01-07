package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.client.models.ModelPrimal;
import net.pufferlab.primal.client.models.ModelWindmill;

public class ItemWindmillRenderer extends ItemPrimalRenderer {

    ModelWindmill[] modelWindmill = new ModelWindmill[] { new ModelWindmill(2) };
    ModelPrimal[] modelWindmillWithAxle = new ModelPrimal[] { new ModelAxle(), new ModelWindmill(2) };

    public ItemWindmillRenderer() {
        super(0.0F, 0.5F, 0.0F);
    }

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelWindmill;
    }

    @Override
    public ModelPrimal[] getModelToRender(int index) {
        return modelWindmillWithAxle;
    }

    @Override
    public boolean shouldScaleModel(ItemStack stack) {
        return true;
    }

    @Override
    public float getScale() {
        return 0.5F;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }
}
