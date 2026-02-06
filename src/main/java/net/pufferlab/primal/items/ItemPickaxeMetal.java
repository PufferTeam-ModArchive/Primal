package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.MetalType;

public class ItemPickaxeMetal extends ItemPickaxePrimitive implements IHeatableItem {

    public MetalType metalType;

    public ItemPickaxeMetal(MetalType metalType) {
        super(metalType.toolMaterial, metalType.name + "_pickaxe");
        this.metalType = metalType;
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return metalType;
    }
}
