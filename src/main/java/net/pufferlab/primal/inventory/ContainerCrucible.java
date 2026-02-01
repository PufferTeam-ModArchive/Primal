package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.TileEntityCrucible;

public class ContainerCrucible extends Container {

    public TileEntityCrucible tileCrucible;

    public ContainerCrucible(IInventory inv, TileEntityCrucible te) {
        for (int j = 0; j < 5; ++j) {
            this.addSlotToContainer(new SlotCrucible(te, j, 44 + j * 18, 19));
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new SlotInventory(inv, te, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new SlotInventory(inv, te, i, 8 + i * 18, 142));
        }

        this.tileCrucible = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileCrucible.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (Utils.containsOreDict(itemstack, "itemContainer")) return null;

            if (index < 9) {
                if (!this.mergeItemStack(itemstack1, 9, 41, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 9, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}
