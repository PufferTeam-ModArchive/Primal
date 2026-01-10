package net.pufferlab.primal.inventory.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.tileentities.TileEntityInventory;

public class SlotInventory extends Slot {

    public TileEntityInventory tileInventory;

    public SlotInventory(IInventory inv, TileEntityInventory tileInventory, int index, int x, int y) {
        super(inv, index, x, y);
        this.tileInventory = tileInventory;
    }

    @Override
    public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) {
        super.onSlotChange(p_75220_1_, p_75220_2_);
        onSlotChanged();
    }
}
