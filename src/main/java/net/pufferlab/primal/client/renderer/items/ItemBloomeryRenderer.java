package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelBloomery;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemBloomeryRenderer extends ItemPrimalRenderer {

    ModelPrimal[] modelBloomery = { new ModelBloomery() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelBloomery;
    }

    @Override
    public boolean shouldScaleModel(ItemStack stack) {
        return true;
    }

    @Override
    public float getScale() {
        return 0.85F;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }
}
