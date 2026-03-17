package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.blocks.ModelPrimal;
import net.pufferlab.primal.client.models.blocks.ModelQuern;

public class ItemQuernRenderer extends ItemPrimalRenderer {

    ModelPrimal[] modelQuern = { new ModelQuern() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelQuern;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

}
