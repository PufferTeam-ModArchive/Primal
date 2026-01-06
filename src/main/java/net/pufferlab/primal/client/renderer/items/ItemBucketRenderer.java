package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;

public class ItemBucketRenderer extends ItemPrimalRenderer {

    public boolean handleRendering(ItemStack stack) {
        return true;
    }

    @Override
    public boolean handleContainerRendering() {
        return true;
    }

}
