package net.pufferlab.primal.tileentities;

import net.minecraft.item.Item;

public class TileEntityCast extends TileEntityFluidInventory {

    public TileEntityCast() {
        super(1000, 1);
    }

    public void setCapacity(int capacity) {
        this.tank.setCapacity(capacity);
    }

    @Override
    public Item getLastItem() {
        return getInventoryStack(0).getItem();
    }

    @Override
    public int getLastItemMeta() {
        return getInventoryStack(0).getItemDamage();
    }
}
