package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelOven;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemOvenRenderer extends ItemPrimalRenderer {

    ModelOven[] modelOven = new ModelOven[] { new ModelOven() };

    int[] ovenMeta = new int[] { 0 };

    @Override
    public ModelPrimal[] getItemBlockModel(ItemStack stack) {
        return modelOven;
    }

    @Override
    public int[] getItemBlockMeta() {
        return ovenMeta;
    }

    @Override
    public boolean isItemBlock(ItemStack stack) {
        return true;
    }
}
