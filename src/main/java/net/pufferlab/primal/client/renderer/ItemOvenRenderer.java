package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelOven;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemOvenRenderer extends ItemPrimalRenderer {

    ModelOven[] modelOven = new ModelOven[] { new ModelOven() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelOven;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }
}
