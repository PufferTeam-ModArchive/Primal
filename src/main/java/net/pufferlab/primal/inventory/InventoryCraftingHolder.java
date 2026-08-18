package net.pufferlab.primal.inventory;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryCraftingHolder extends InventoryCrafting {

    public ItemStack output;

    public InventoryCraftingHolder(ItemStack output, ItemStack[] stackList) {
        super(null, 3, 3);
        this.stackList = stackList;
        this.output = output;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.stackList[index] != null) {
            ItemStack itemstack;

            if (this.stackList[index].stackSize <= count) {
                itemstack = this.stackList[index];
                this.stackList[index] = null;
                return itemstack;
            } else {
                itemstack = this.stackList[index].splitStack(count);

                if (this.stackList[index].stackSize == 0) {
                    this.stackList[index] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }
}
