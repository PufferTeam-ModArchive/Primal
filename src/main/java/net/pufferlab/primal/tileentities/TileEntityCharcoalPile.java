package net.pufferlab.primal.tileentities;

public class TileEntityCharcoalPile extends TileEntityInventory {

    public TileEntityCharcoalPile() {
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
