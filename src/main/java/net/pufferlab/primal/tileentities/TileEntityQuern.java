package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.events.PacketSpeedUpdate;

public class TileEntityQuern extends TileEntityInventory {

    public static final float maxSpeed = 8F;
    public static final float speedAcceleration = 2F;
    public static final float speedDeceleration = 0.6F;

    public TileEntityQuern() {
        super(3);
        this.setInputSlots(1);
        this.setOutputSlots(2);
    }

    public float rotation;
    public float speed;
    public float lastSpeed;
    public int pressed;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.rotation = tag.getFloat("rotation");
        this.speed = tag.getFloat("speed");
        this.lastSpeed = tag.getFloat("lastSpeed");
        this.pressed = tag.getInteger("pressed");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("rotation", this.rotation);
        tag.setFloat("speed", this.speed);
        tag.setFloat("lastSpeed", this.lastSpeed);
        tag.setInteger("pressed", this.pressed);
    }

    public void sendUpdate() {
        if (!worldObj.isRemote) {
            Primal.network.sendToAll(new PacketSpeedUpdate(this));
        }
    }

    public void addSpeed() {
        if (!worldObj.isRemote) {
            if (this.pressed < 20) {
                this.pressed++;
            }
        }
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            float newSpeed = this.speed;

            if (newSpeed > 0) {
                newSpeed = Math.max(0, this.speed - speedDeceleration);
            }

            if (this.pressed > 0) {
                if (newSpeed < maxSpeed) {
                    newSpeed = newSpeed + speedAcceleration;
                } else {
                    this.pressed--;
                }
            }

            if (newSpeed != speed) {
                this.speed = newSpeed;
                sendUpdate();
            }
        }

        if (worldObj.isRemote) {
            this.rotation = (this.rotation + this.speed) % 360.0F;
        }
    }
}
