package net.pufferlab.primal.inventory.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Registry;

public class SlotLargeVessel extends Slot {

    public SlotLargeVessel(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    public boolean isItemValid(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Item.getItemFromBlock(Registry.large_vessel)) {
            return false;
        }
        return true;
    }
}
