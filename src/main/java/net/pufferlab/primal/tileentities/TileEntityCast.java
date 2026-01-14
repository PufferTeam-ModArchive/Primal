package net.pufferlab.primal.tileentities;

import net.minecraft.item.Item;

public class TileEntityCast extends TileEntityFluidInventory {

    public TileEntityCast() {
        super(432, 1);
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
