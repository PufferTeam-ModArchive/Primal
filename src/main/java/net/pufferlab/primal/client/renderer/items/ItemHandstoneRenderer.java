package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.blocks.ModelHandstone;
import net.pufferlab.primal.client.models.blocks.ModelPrimal;

public class ItemHandstoneRenderer extends ItemPrimalRenderer {

    ModelPrimal[] modelHandstone = { new ModelHandstone() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelHandstone;
    }

    @Override
    public boolean shouldScaleModel(ItemStack stack) {
        return true;
    }

    @Override
    public float getScale() {
        return 1.25F;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }
}
