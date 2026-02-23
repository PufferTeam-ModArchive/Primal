package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.network.NetworkMotion;
import net.pufferlab.primal.utils.BlockUtils;

public abstract class TileEntityMotionInventory extends TileEntityInventory implements IMotion {

    float torque;
    float speed;
    float speedModifier = 1;
    boolean needsUpdate;
    boolean needsSpreadUpdate;
    boolean needsRemovalUpdate;
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
        this.needsSpreadUpdate = tag.getBoolean("needsSpreadUpdate");
        this.needsRemovalUpdate = tag.getBoolean("needsRemovalUpdate");
        this.hasOffset = tag.getBoolean("hasOffset");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("torque", this.torque);
        tag.setFloat("speed", this.speed);
        tag.setFloat("speedModifier", this.speedModifier);
        tag.setBoolean("needsUpdate", this.needsUpdate);
        tag.setBoolean("needsSpreadUpdate", this.needsSpreadUpdate);
        tag.setBoolean("needsRemovalUpdate", this.needsRemovalUpdate);
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

    @Override
    public void updateEntity() {
        if (this.needsRemovalUpdate) {
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
        }
        if (this.needsUpdate) {
            this.needsUpdate = false;
            NetworkMotion.sendUpdate(this);
        }
        if (this.needsSpreadUpdate) {
            this.needsSpreadUpdate = false;
            NetworkMotion.sendSpreadUpdate(this);
        }
    }

    @Override
    public void sendClientUpdate() {
        Primal.proxy.packet.sendMotionSpeedPacket(this);
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
        NetworkMotion.sendStrongUpdate(this);
    }

    @Override
    public void scheduleSpreadUpdate() {
        this.needsSpreadUpdate = true;
    }

    @Override
    public void scheduleRemoval() {
        this.needsRemovalUpdate = true;
    }

    @Override
    public boolean hasConnection(int side) {
        int axis = BlockUtils.getAxis(side);
        if (axis == this.axisMeta) {
            return true;
        }
        return false;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        if (Config.extendMechanicalPowerRendering.getBoolean()) {
            return Primal.proxy.getClientMaxRenderDistanceSquared();
        }
        return super.getMaxRenderDistanceSquared();
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
