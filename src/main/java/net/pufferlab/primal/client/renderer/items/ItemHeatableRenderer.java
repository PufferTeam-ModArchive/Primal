package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;

public class ItemHeatableRenderer extends ItemPrimalRenderer {

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

    @Override
    public boolean handleTemperatureRendering() {
        return true;
    }
}
