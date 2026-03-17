package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.blocks.ModelOven;
import net.pufferlab.primal.client.models.blocks.ModelPrimal;

public class ItemOvenRenderer extends ItemPrimalRenderer {

    ModelPrimal[] modelOven = { new ModelOven() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelOven;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }
}
