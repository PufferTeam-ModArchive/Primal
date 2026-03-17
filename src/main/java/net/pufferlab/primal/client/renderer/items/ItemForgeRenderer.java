package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.blocks.ModelForge;
import net.pufferlab.primal.client.models.blocks.ModelPrimal;

public class ItemForgeRenderer extends ItemPrimalRenderer {

    ModelPrimal[] modelForge = { new ModelForge() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelForge;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

}
