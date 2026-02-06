package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.utils.MetalType;

public class ItemArmorMetal extends ItemArmorPrimitive implements IHeatableItem {

    public MetalType metalType;

    public ItemArmorMetal(MetalType metalType, int armorType) {
        super(metalType.armorMaterial, metalType.name + "_" + getArmorName(armorType), armorType);
        this.metalType = metalType;
    }

    public static String getArmorName(int armorType) {
        return switch (armorType) {
            case Constants.helmet -> "helmet";
            case Constants.chestplate -> "chestplate";
            case Constants.leggings -> "leggings";
            case Constants.boots -> "boots";
            default -> "";
        };
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return metalType;
    }
}
