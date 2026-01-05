package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.events.PacketSpeedUpdate;
import net.pufferlab.primal.network.NetworkMotion;

public abstract class TileEntityMotion extends TileEntityMetaFacing implements IMotion {

    float torque;
    float speed;
    float speedModifier = 1;
    boolean needsUpdate;
    boolean needsStrongUpdate;
    boolean needsSpeedUpdate;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.torque = tag.getFloat("torque");
        this.speed = tag.getFloat("speed");
        this.speedModifier = tag.getFloat("speedModifier");
        this.needsUpdate = tag.getBoolean("needsUpdate");
        this.needsStrongUpdate = tag.getBoolean("needsStrongUpdate");
        this.needsSpeedUpdate = tag.getBoolean("needsSpeedUpdate");
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
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        this.speed = tag.getFloat("speed");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        tag.setFloat("speed", this.speed);
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
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                for (ForgeDirection direction2 : ForgeDirection.VALID_DIRECTIONS) {
                    TileEntity te = getWorld().getTileEntity(
                        getX() + direction.offsetX + direction2.offsetX,
                        getY() + direction.offsetY + direction2.offsetY,
                        getZ() + direction.offsetZ + direction2.offsetZ);
                    if (te instanceof IMotion tef) {
                        tef.scheduleUpdate();
                    }
                }
            }
            this.scheduleUpdate();
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
}
