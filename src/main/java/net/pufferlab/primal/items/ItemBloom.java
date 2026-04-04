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

    @Override
    public int getMeltingTemperature(ItemStack stack) {
        return metalType.meltingTemperature;
    }

    @Override
    public int getWeldingTemperature(ItemStack stack) {
        return metalType.weldingTemperature;
    }

    @Override
    public int getForgingTemperature(ItemStack stack) {
        return metalType.forgingTemperature;
    }
}
