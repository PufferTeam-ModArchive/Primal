package net.pufferlab.primal.tileentities;

public class TileEntityAshPile extends TileEntityInventory {

    public TileEntityAshPile() {
        super(8);
    }

    @Override
    public int getLayerAmount() {
        return 8;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
