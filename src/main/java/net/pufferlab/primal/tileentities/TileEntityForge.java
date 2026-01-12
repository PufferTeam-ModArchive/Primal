package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.utils.TemperatureUtils;

public class TileEntityForge extends TileEntityInventory implements IHeatable {

    public int temperature;
    public int timeFired;
    public int timeHeat;
    public int timeUpdate;
    public int lastLevel;

    public TileEntityForge() {
        super(8);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.timeFired = tag.getInteger("timeFired");
        this.timeHeat = tag.getInteger("timeHeat");
        this.timeUpdate = tag.getInteger("timeUpdate");
        this.temperature = tag.getInteger("temperature");
        this.lastLevel = tag.getInteger("lastLevel");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("timeFired", this.timeFired);
        tag.setInteger("timeHeat", this.timeHeat);
        tag.setInteger("timeUpdate", this.timeUpdate);
        tag.setInteger("temperature", this.temperature);
        tag.setInteger("lastLevel", this.lastLevel);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.temperature = tag.getInteger("temperature");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setInteger("temperature", this.temperature);
    }

    public int burnTime = 60 * 20;

    @Override
    public void updateEntity() {
        if (this.getMaxTemperature() > this.temperature) {
            if (this.isFired()) {
                this.timeHeat++;
            } else {
                this.timeHeat--;
            }
        }
        if (timeHeat > 5) {
            timeHeat = 0;
            this.temperature++;
        }
        if (timeHeat < -3) {
            timeHeat = 0;
            if (this.temperature > 0) {
                this.temperature--;
            }
        }

        timeUpdate++;
        if (timeUpdate > 20) {
            timeUpdate = 0;
            if (TemperatureUtils.getHeatingLevel(this.temperature) != this.lastLevel) {
                this.lastLevel = TemperatureUtils.getHeatingLevel(this.temperature);
                updateTEState();
            }
        }

        if (isFired) {
            timeFired++;
            if (this.blockMetadata == 0) {
                setFired(false);
            }
        }

        if (timeFired > burnTime) {
            timeFired = 0;
            int i = findLastFuel();
            if (i != -1) {
                if (blockMetadata > 0) {
                    markDirty();
                    setInventorySlotContentsUpdate(i);
                    this.worldObj
                        .setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.blockMetadata - 1, 2);
                    markDirty();
                }

            }
        }
    }

    public int findLastFuel() {
        int last = -1;

        for (int i = 0; i < 5; i++) {
            ItemStack stack = getInventoryStack(i);
            if (stack != null && !Utils.containsOreDict(stack, "ash")) {
                last = i;
            }
        }

        return last;
    }

    @Override
    public boolean canBeFired() {
        return true;
    }

    @Override
    public void setFired(boolean state) {
        if (this.isFired != state) {
            this.isFired = state;
        }
        this.updateTEState();
    }

    @Override
    public boolean isFired() {
        return this.isFired;
    }

    @Override
    public int getMaxTemperature() {
        return 1300;
    }

    @Override
    public int getTemperature() {
        return this.temperature;
    }

    @Override
    public boolean isHeatProvider() {
        return true;
    }
}
