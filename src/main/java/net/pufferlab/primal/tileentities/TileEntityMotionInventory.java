package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.events.PacketSpeedUpdate;
import net.pufferlab.primal.network.NetworkMotion;

public abstract class TileEntityMotionInventory extends TileEntityInventory implements IMotion {

    float torque;
    float speed;
    float speedModifier = 1;
    boolean needsUpdate;
    boolean needsStrongUpdate;
    boolean needsSpeedUpdate;
    boolean hasOffset;

    public TileEntityMotionInventory(int slots) {
        super(slots);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.torque = tag.getFloat("torque");
        this.speed = tag.getFloat("speed");
        this.speedModifier = tag.getFloat("speedModifier");
        this.needsUpdate = tag.getBoolean("needsUpdate");
        this.needsStrongUpdate = tag.getBoolean("needsStrongUpdate");
        this.needsSpeedUpdate = tag.getBoolean("needsSpeedUpdate");
        this.hasOffset = tag.getBoolean("hasOffset");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("torque", this.torque);
        tag.setFloat("speed", this.speed);
        tag.setFloat("speedModifier", this.speedModifier);
        tag.setBoolean("needsUpdate", this.needsUpdate);
        tag.setBoolean("needsStrongUpdate", this.needsStrongUpdate);
        tag.setBoolean("needsSpeedUpdate", this.needsSpeedUpdate);
        tag.setBoolean("hasOffset", this.hasOffset);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        this.speed = tag.getFloat("speed");
        this.hasOffset = tag.getBoolean("hasOffset");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        tag.setFloat("speed", this.speed);
        tag.setBoolean("hasOffset", this.hasOffset);
    }

    int ticks;

    @Override
    public void updateEntity() {
        if (this.needsUpdate) {
            this.needsUpdate = false;
            NetworkMotion.sendUpdate(this);
        }
        if (this.needsStrongUpdate) {
            this.needsStrongUpdate = false;
            NetworkMotion.sendStrongUpdate(this);
        }
        if (this.needsSpeedUpdate) {
            this.needsSpeedUpdate = false;
            NetworkMotion.sendSpeedUpdate(this);
        }
    }

    @Override
    public void sendClientUpdate() {
        if (!worldObj.isRemote) {
            Primal.proxy.sendPacketToClient(new PacketSpeedUpdate(this));
            this.markDirty();
        }
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getSpeedModifier() {
        return this.speedModifier;
    }

    @Override
    public int getX() {
        return this.xCoord;
    }

    @Override
    public int getY() {
        return this.yCoord;
    }

    @Override
    public int getZ() {
        return this.zCoord;
    }

    @Override
    public World getWorld() {
        return getWorldObj();
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void setSpeedModifier(float speedModifier) {
        this.speedModifier = speedModifier;
    }

    @Override
    public void scheduleUpdate() {
        this.needsUpdate = true;
    }

    @Override
    public void scheduleStrongUpdate() {
        this.needsStrongUpdate = true;
    }

    @Override
    public void scheduleSpeedUpdate() {
        this.needsSpeedUpdate = true;
    }

    @Override
    public boolean hasConnection(int side) {
        int axis = Utils.getAxis(side);
        if (axis == this.axisMeta) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasGear(int side) {
        return false;
    }

    @Override
    public float getTorque() {
        return 0.0F;
    }

    @Override
    public float getGeneratedSpeed() {
        return 0.0F;
    }

    @Override
    public boolean hasNetwork() {
        return true;
    }

    @Override
    public void setHasNetwork(boolean state) {}

    @Override
    public boolean hasOffset() {
        return this.hasOffset;
    }

    @Override
    public void setHasOffset(boolean state) {
        this.hasOffset = state;
    }
}
