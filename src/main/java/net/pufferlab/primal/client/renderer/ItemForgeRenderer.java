package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelForge;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemForgeRenderer extends ItemPrimalRenderer {

    ModelForge[] modelForge = new ModelForge[] { new ModelForge() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelForge;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

}
