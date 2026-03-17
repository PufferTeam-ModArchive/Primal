package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.MetalType;

public class ItemArmorMetal extends ItemArmorPrimitive implements IHeatableItem {

    public MetalType metalType;

    public ItemArmorMetal(MetalType metalType, String armorType) {
        super(metalType.armorMaterial, metalType.name + "_" + armorType, armorType);
        this.metalType = metalType;
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return metalType;
    }
}
