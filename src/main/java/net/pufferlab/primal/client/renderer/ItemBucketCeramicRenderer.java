package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;

public class ItemBucketCeramicRenderer extends ItemPrimalRenderer {

    public static final int[] metaBlacklist = new int[] { 0, 1, 2 };

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

    @Override
    public int[] getMetaBlacklist() {
        return metaBlacklist;
    }

    @Override
    public boolean handleContainerRendering() {
        return true;
    }

}
