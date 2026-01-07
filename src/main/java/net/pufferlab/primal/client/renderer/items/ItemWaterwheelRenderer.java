package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.client.models.ModelPrimal;
import net.pufferlab.primal.client.models.ModelWaterwheel;

public class ItemWaterwheelRenderer extends ItemPrimalRenderer {

    public ItemWaterwheelRenderer() {
        super(0.0F, 0.5F, 0.0F);
    }

    ModelWaterwheel[] modelWaterwheelWheel = new ModelWaterwheel[] { new ModelWaterwheel() };
    ModelPrimal[] modelWaterwheelWithAxle = new ModelPrimal[] { new ModelAxle(), new ModelWaterwheel() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelWaterwheelWheel;
    }

    @Override
    public ModelPrimal[] getModelToRender(int index) {
        return modelWaterwheelWithAxle;
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
