package net.pufferlab.primal.tileentities;

public class TileEntityCast extends TileEntityFluidInventory {

    public TileEntityCast() {
        super(1000, 1);
    }

    public void setCapacity(int capacity) {
        this.tank.setCapacity(capacity);
    }
}
