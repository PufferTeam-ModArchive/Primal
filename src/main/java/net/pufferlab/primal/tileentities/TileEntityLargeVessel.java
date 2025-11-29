package net.pufferlab.primal.tileentities;

public class TileEntityLargeVessel extends TileEntityInventory {

    public TileEntityLargeVessel() {
        super(9);
    }

    @Override
    public String getInventoryName() {
        return "Large Vessel";
    }
}
