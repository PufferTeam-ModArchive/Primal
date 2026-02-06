package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.MetalType;

public class ItemShovelMetal extends ItemShovelPrimitive implements IHeatableItem {

    public MetalType metalType;

    public ItemShovelMetal(MetalType metalType) {
        super(metalType.toolMaterial, metalType.name + "_shovel");
        this.metalType = metalType;
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return metalType;
    }
}
