package net.pufferlab.primal.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotKnapping extends Slot {

    public ContainerKnapping container;

    public SlotKnapping(ContainerKnapping container, IInventory inventory, int slotIndex, int x, int y) {
        super(inventory, slotIndex, x, y);
        this.container = container;
    }

    public boolean isItemValid(ItemStack stack) {
        return false;
    }

}
