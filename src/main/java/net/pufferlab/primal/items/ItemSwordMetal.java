package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.MetalType;

public class ItemSwordMetal extends ItemSwordPrimitive implements IHeatableItem {

    public MetalType metalType;

    public ItemSwordMetal(MetalType metalType) {
        super(metalType.toolMaterial, metalType.name + "_sword");
        this.metalType = metalType;
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return metalType;
    }
}
