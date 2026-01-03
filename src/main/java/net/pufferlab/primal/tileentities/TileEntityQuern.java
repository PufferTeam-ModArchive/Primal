package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.events.PacketSpeedUpdate;
import net.pufferlab.primal.recipes.QuernRecipe;

public class TileEntityQuern extends TileEntityInventory {

    public static final float maxSpeed = 8F;
    public static final float speedAcceleration = 2F;
    public static final float speedDeceleration = 0.6F;
    int timeToUse = 20 * 10;
    int timeToGrind = 20 * 5;

    public static int slotHandstone = 0;
    public static int slotInput = 1;
    public static int slotOutput = 2;

    public TileEntityQuern() {
        super(3);
        this.setInputSlots(slotInput);
        this.setOutputSlots(slotOutput);
    }

    public float rotation;
    public float speed;
    public float lastSpeed;
    public int pressed;
    public boolean isMoving;
    public int timeUsed;
    public int timePassed;
    public int timeGround;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.rotation = tag.getFloat("rotation");
        this.speed = tag.getFloat("speed");
        this.lastSpeed = tag.getFloat("lastSpeed");
        this.isMoving = tag.getBoolean("isMoving");
        this.timeUsed = tag.getInteger("timeUsed");
        this.timePassed = tag.getInteger("timePassed");
        this.timeGround = tag.getInteger("timeGround");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("rotation", this.rotation);
        tag.setFloat("speed", this.speed);
        tag.setFloat("lastSpeed", this.lastSpeed);
        tag.setBoolean("isMoving", this.isMoving);
        tag.setInteger("timeUsed", this.timeUsed);
        tag.setInteger("timePassed", this.timePassed);
        tag.setInteger("timeGround", this.timeGround);
    }

    public void sendUpdate() {
        if (!worldObj.isRemote) {
            Primal.proxy.sendPacketToClient(new PacketSpeedUpdate(this));
            this.markDirty();
        }
    }

    public float getPercentageSpeed() {
        return this.speed / maxSpeed;
    }

    public void addSpeed() {
        if (this.blockMetadata == 1) {
            if (!worldObj.isRemote) {
                if (this.pressed < 20) {
                    this.pressed++;
                }
            }
        }
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {

            float newSpeed = this.speed;

            if (newSpeed > 0) {
                newSpeed = Math.max(0, this.speed - speedDeceleration);
                timeUsed++;
                timePassed++;
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

            if (timeUsed > timeToUse) {
                timeUsed = 0;
                ItemStack handstone = getInventoryStack(slotHandstone);
                if (handstone != null) {
                    boolean broke = handstone.attemptDamageItem(1, worldObj.rand);
                    if (broke) {
                        this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 2);
                        setInventorySlotContentsUpdate(slotHandstone);
                    }
                }
            }

            int newTimeGround = this.timeGround;
            if (this.timePassed > 20) {
                this.timePassed = 0;
                if (getInventoryStack(slotInput) != null) {
                    newTimeGround = this.timeGround + (int) Math.floor(20 * getPercentageSpeed());
                } else {
                    newTimeGround = 0;
                }
            }

            if (newTimeGround != this.timeGround) {
                this.timeGround = newTimeGround;
            }

            if (this.timeGround > timeToGrind) {
                this.timeGround = 0;
                ItemStack output = QuernRecipe.getOutput(getInventoryStack(slotInput));
                if (output != null) {
                    addItemInSlotUpdate(slotOutput, output);
                    decrStackSize(slotInput, 1);
                }
            }
        }

        if (worldObj.isRemote) {
            this.rotation = (this.rotation + this.speed) % 360.0F;

            if (this.speed > 0) {
                Primal.proxy.renderFX(this, 0.5, 1.1, 0.5, getInventoryStack(slotInput));
                if (!this.isMoving) {
                    this.isMoving = true;
                    Primal.proxy.playClientSound(this);
                }
            } else {
                this.isMoving = false;
            }

        }
    }
}
