package net.pufferlab.primal.tileentities;

public class TileEntityGenerator extends TileEntityMotion {

    @Override
    public float getGeneratedSpeed() {
        return 10F;
    }

    @Override
    public float getSpeed() {
        return getGeneratedSpeed();
    }

    @Override
    public float getTorque() {
        return 1000.0F;
    }
}
