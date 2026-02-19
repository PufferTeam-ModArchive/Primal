package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.recipes.QuernRecipe;

public class TileEntityQuern extends TileEntityMotionInventory {

    public static final float maxSpeed = 8F;
    public static final float speedAcceleration = 2F;
    public static final float speedDeceleration = 0.6F;
    public static final int useTime = 20 * 10;
    public static final int grindTime = 20 * 5;

    public static int slotHandstone = 0;
    public static int slotInput = 1;
    public static int slotOutput = 2;

    public TileEntityQuern() {
        super(3);
        this.setInputSlots(slotInput);
        this.setOutputSlots(slotOutput);
    }

    public float rotation;
    public float lastSpeed;
    public int pressed;
    public boolean isMoving;
    public int timeUsed;
    public int timePassed;
    public int timeGround;
    public boolean hasNetwork;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.rotation = tag.getFloat("rotation");
        this.lastSpeed = tag.getFloat("lastSpeed");
        this.isMoving = tag.getBoolean("isMoving");
        this.timeUsed = tag.getInteger("timeUsed");
        this.timePassed = tag.getInteger("timePassed");
        this.timeGround = tag.getInteger("timeGround");
        this.hasNetwork = tag.getBoolean("hasNetwork");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("rotation", this.rotation);
        tag.setFloat("lastSpeed", this.lastSpeed);
        tag.setBoolean("isMoving", this.isMoving);
        tag.setInteger("timeUsed", this.timeUsed);
        tag.setInteger("timePassed", this.timePassed);
        tag.setInteger("timeGround", this.timeGround);
        tag.setBoolean("hasNetwork", this.hasNetwork);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.timeGround = tag.getInteger("timeGround");
        this.isMoving = tag.getBoolean("isMoving");
        this.hasNetwork = tag.getBoolean("hasNetwork");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setInteger("timeGround", this.timeGround);
        tag.setBoolean("isMoving", this.isMoving);
        tag.setBoolean("hasNetwork", this.hasNetwork);
    }

    public float getPercentageSpeed() {
        return Math.abs(this.speed) / maxSpeed;
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
        super.updateEntity();

        if (this.getSpeed() != 0) {
            timePassed++;
            if (getInventoryStack(slotInput) != null) {
                timeUsed++;
            }
        }
        if (!worldObj.isRemote) {
            if (!this.hasNetwork) {
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
                    sendClientUpdate();
                }
            }
        }

        TileEntity teAbove = worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
        if (teAbove instanceof IMotion) {
            this.hasNetwork = true;
        } else {
            if (this.hasNetwork) {
                this.hasNetwork = false;
                scheduleUpdate();
            }
        }

        if (this.timePassed > 1) {
            this.timePassed = 0;
            if (getInventoryStack(slotInput) != null) {
                this.timeGround = this.timeGround + Utils.floor(4 * getPercentageSpeed());
            } else {
                this.timeGround = 0;
            }
        }

        if (this.timeGround > getGrindTime()) {
            this.timeGround = 0;
            ItemStack output = QuernRecipe.getOutput(getInventoryStack(slotInput));
            if (output != null) {
                addItemInSlotUpdate(slotOutput, output.copy());
                decrStackSize(slotInput, 1);
            }
        }

        if (timeUsed > useTime) {
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

        if (worldObj.isRemote) {
            this.rotation = (this.rotation + this.speed) % 360.0F;

            if (this.speed != 0) {
                if (blockMetadata == 1) {
                    Primal.proxy.renderFX(this, 0.5, 1.1, 0.5, getInventoryStack(slotInput));
                    if (!this.isMoving) {
                        this.isMoving = true;
                        Primal.proxy.playClientSound(this);
                    }
                }
            } else {
                this.isMoving = false;
            }

        }
    }

    public int getGrindTime() {
        return grindTime;
    }

    @Override
    public float getTorque() {
        return -5.0F;
    }

    @Override
    public boolean hasConnection(int side) {
        return side == ForgeDirection.UP.ordinal();
    }

    @Override
    public boolean hasNetwork() {
        return this.hasNetwork;
    }

}
