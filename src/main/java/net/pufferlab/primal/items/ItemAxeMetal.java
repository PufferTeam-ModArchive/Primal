package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.MetalType;

public class ItemAxeMetal extends ItemAxePrimitive implements IHeatableItem {

    public MetalType metalType;

    public ItemAxeMetal(MetalType metalType) {
        super(metalType.toolMaterial, metalType.name + "_axe");
        this.metalType = metalType;
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return metalType;
    }
}
