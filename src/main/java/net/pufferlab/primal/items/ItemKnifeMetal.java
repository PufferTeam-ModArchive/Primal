package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.MetalType;

public class ItemKnifeMetal extends ItemKnifePrimitive implements IHeatableItem {

    public MetalType metalType;

    public ItemKnifeMetal(MetalType metalType) {
        super(metalType.toolMaterial, metalType.name + "_knife");
        this.metalType = metalType;
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return metalType;
    }
}
