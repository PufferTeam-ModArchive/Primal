package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.MetalType;

public class ItemHammerMetal extends ItemHammerPrimitive implements IHeatableItem {

    public MetalType metalType;

    public ItemHammerMetal(MetalType metalType) {
        super(metalType.toolMaterial, metalType.name + "_hammer");
        this.metalType = metalType;
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return metalType;
    }
}
