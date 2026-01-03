package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrucible extends TileEntityFluidInventory implements IHeatable {

    public int temperature;
    public int timeHeat;
    public int lastLevel;

    public TileEntityCrucible() {
        super(3000, 5);
    }

    @Override
    public String getInventoryName() {
        return "Crucible";
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.timeHeat = tag.getInteger("timeHeat");
        this.lastLevel = tag.getInteger("lastLevel");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("timeHeat", this.timeHeat);
        tag.setInteger("lastLevel", this.lastLevel);
    }

    @Override
    public void readFromNBTInventory(NBTTagCompound tag) {
        super.readFromNBTInventory(tag);
        this.temperature = tag.getInteger("temperature");
    }

    @Override
    public void writeToNBTInventory(NBTTagCompound tag) {
        super.writeToNBTInventory(tag);
        tag.setInteger("temperature", this.temperature);
    }

    @Override
    public void updateEntity() {
        TileEntity teBelow = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if (teBelow instanceof IHeatable tef) {
            if (tef.isHeatProvider()) {
                if (tef.getTemperature() > this.temperature) {
                    if (tef.isFired()) {
                        this.timeHeat++;
                    } else {
                        this.timeHeat--;
                    }
                }
            }
        } else {
            if (this.temperature > 0) {
                this.timeHeat--;
            }
        }

        if (timeHeat > 3) {
            timeHeat = 0;
            this.temperature++;
        }
        if (timeHeat < -3) {
            timeHeat = 0;
            this.temperature--;
        }
    }

    @Override
    public boolean canBeFired() {
        return false;
    }

    @Override
    public void setFired(boolean state) {}

    @Override
    public boolean isFired() {
        return this.isFired;
    }

    @Override
    public int getMaxTemperature() {
        return 0;
    }

    @Override
    public int getTemperature() {
        return this.temperature;
    }

    @Override
    public boolean isHeatProvider() {
        return false;
    }
}
