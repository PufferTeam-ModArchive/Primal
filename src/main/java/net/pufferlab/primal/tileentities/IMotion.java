package net.pufferlab.primal.tileentities;

import net.minecraft.world.World;

public interface IMotion {

    public boolean hasConnection(int side);

    public boolean hasGear(int side);

    public float getSpeed();

    public float getSpeedModifier();

    public void setSpeed(float speed);

    public void setSpeedModifier(float speed);

    public void setHasNetwork(boolean state);

    public void setHasOffset(boolean state);

    public float getTorque();

    public void scheduleUpdate();

    public void scheduleStrongUpdate();

    public void scheduleSpreadUpdate();

    public void scheduleRemoval();

    public float getGeneratedSpeed();

    public World getWorld();

    public int getX();

    public int getY();

    public int getZ();

    public void sendClientUpdate();

    public boolean hasNetwork();

    public boolean hasOffset();
}
