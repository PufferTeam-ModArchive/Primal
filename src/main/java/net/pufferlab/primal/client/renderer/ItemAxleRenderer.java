package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemAxleRenderer extends ItemPrimalRenderer {

    public ItemAxleRenderer() {
        super(0.0F, 0.5F, 0.0F);
    }

    ModelAxle[] modelAxle = new ModelAxle[] { new ModelAxle() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelAxle;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }
}
