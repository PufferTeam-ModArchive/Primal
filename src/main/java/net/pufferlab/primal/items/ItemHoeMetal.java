package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.MetalType;

public class ItemHoeMetal extends ItemHoePrimitive implements IHeatableItem {

    public MetalType metalType;

    public ItemHoeMetal(MetalType metalType) {
        super(metalType.toolMaterial, metalType.name + "_hoe");
        this.metalType = metalType;
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return metalType;
    }
}
