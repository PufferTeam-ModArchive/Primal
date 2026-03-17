package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.blocks.ModelBarrel;
import net.pufferlab.primal.client.models.blocks.ModelPrimal;

public class ItemBarrelRenderer extends ItemPrimalRenderer {

    ModelPrimal[] modelBarrel = { new ModelBarrel() };

    int[] barrelMeta = new int[] { 0 };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelBarrel;
    }

    @Override
    public int[] getMeta() {
        return barrelMeta;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

}
