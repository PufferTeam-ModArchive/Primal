package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.MetalType;

public class ItemBloom extends ItemMetaHeatable {

    public MetalType metalType;

    public ItemBloom(String[] elements, MetalType metal, String type) {
        super(elements, type);
        this.metalType = metal;
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return metalType;
    }
}
