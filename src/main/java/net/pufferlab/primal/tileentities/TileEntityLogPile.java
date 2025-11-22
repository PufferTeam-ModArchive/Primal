package net.pufferlab.primal.tileentities;

public class TileEntityLogPile extends TileEntityInventory {

    public TileEntityLogPile() {
        super(9);
    }

    @Override
    public int getLayerAmount() {
        return 3;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
