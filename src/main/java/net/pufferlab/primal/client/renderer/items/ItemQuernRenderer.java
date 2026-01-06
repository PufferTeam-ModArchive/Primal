package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelPrimal;
import net.pufferlab.primal.client.models.ModelQuern;

public class ItemQuernRenderer extends ItemPrimalRenderer {

    ModelQuern[] modelQuern = new ModelQuern[] { new ModelQuern() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelQuern;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

}
