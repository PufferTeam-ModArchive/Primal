package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.network.NetworkMotion;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.world.ScheduleManager;
import net.pufferlab.primal.world.Tasks;

public abstract class TileEntityMotion extends TileEntityMetaFacing implements IMotion, IScheduledTile {

    float torque;
    float speed;
    float speedModifier = 1;
    boolean hasOffset;

    public ScheduleManager manager = new ScheduleManager(
        Tasks.network,
        Tasks.networkSpread,
        Tasks.removal,
        Tasks.generator,
        Tasks.generatorLate,
        Tasks.flow,
        Tasks.wind);

    public TileEntityMotion() {}

    public TileEntityMotion(int facingMeta, int axisMeta) {
        super(facingMeta, axisMeta);
    }

    public ScheduleManager getManager() {
        return manager;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.torque = tag.getFloat("torque");
        this.speed = tag.getFloat("speed");
        this.speedModifier = tag.getFloat("speedModifier");
        this.hasOffset = tag.getBoolean("hasOffset");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("torque", this.torque);
        tag.setFloat("speed", this.speed);
        tag.setFloat("speedModifier", this.speedModifier);
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
    public void onScheduleTask(Tasks task) {
        IScheduledTile.super.onScheduleTask(task);

        if (task == Tasks.network) {
            NetworkMotion.sendUpdate(this);
        }
        if (task == Tasks.networkSpread) {
            NetworkMotion.sendSpreadUpdate(this);
        }
        if (task == Tasks.removal) {
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
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
        addSchedule(0, Tasks.network);
    }

    @Override
    public void scheduleStrongUpdate() {
        NetworkMotion.sendStrongUpdate(this);
    }

    @Override
    public void scheduleSpreadUpdate() {
        addSchedule(0, Tasks.networkSpread);
    }

    @Override
    public void scheduleRemoval() {
        addSchedule(0, Tasks.removal);
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
