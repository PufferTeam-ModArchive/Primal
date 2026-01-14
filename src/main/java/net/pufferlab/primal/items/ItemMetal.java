package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;

public class ItemMetal extends ItemMetaHeatable {

    public MetalType[] metalTypes;

    public ItemMetal(MetalType[] materials, String type) {
        super(MetalType.getNames(materials), type);
        this.setHasSuffix();
        this.metalTypes = materials;
    }

    @Override
    public int getMeltingTemperature(ItemStack stack) {
        return metalTypes[stack.getItemDamage()].meltingTemperature;
    }

    @Override
    public int getWeldingTemperature(ItemStack stack) {
        return metalTypes[stack.getItemDamage()].weldingTemperature;
    }
}
