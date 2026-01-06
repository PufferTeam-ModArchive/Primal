package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.client.models.ModelPrimal;
import net.pufferlab.primal.client.models.ModelWaterWheel;

public class ItemWaterWheelRenderer extends ItemPrimalRenderer {

    public ItemWaterWheelRenderer() {
        super(0.0F, 0.5F, 0.0F);
    }

    ModelWaterWheel[] modelWaterWheel = new ModelWaterWheel[] { new ModelWaterWheel() };
    ModelPrimal[] modelWaterWheelWithAxle = new ModelPrimal[] { new ModelAxle(), new ModelWaterWheel() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelWaterWheel;
    }

    @Override
    public ModelPrimal[] getModelToRender(int index) {
        return modelWaterWheelWithAxle;
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
