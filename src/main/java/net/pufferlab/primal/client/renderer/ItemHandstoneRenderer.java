package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelHandstone;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemHandstoneRenderer extends ItemPrimalRenderer {

    ModelHandstone[] modelHandstone = new ModelHandstone[] { new ModelHandstone() };

    @Override
    public ModelPrimal[] getItemBlockModel(ItemStack stack) {
        return modelHandstone;
    }

    @Override
    public boolean hasBigModel(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isItemBlock(ItemStack stack) {
        return true;
    }

    @Override
    public boolean needsNormalItemRender() {
        return true;
    }
}
