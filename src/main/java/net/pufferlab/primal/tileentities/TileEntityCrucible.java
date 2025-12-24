package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.Utils;

public class TileEntityCrucible extends TileEntityFluidInventory {

    public int temperature;
    public int timeUpdate;
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
        this.timeUpdate = tag.getInteger("timeUpdate");
        this.timeHeat = tag.getInteger("timeHeat");
        this.lastLevel = tag.getInteger("lastLevel");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("timeUpdate", this.timeUpdate);
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
        if (teBelow instanceof TileEntityForge tef) {
            if (tef.temperature > this.temperature) {
                if (tef.isFired) {
                    this.timeHeat++;
                } else {
                    this.timeHeat--;
                }
            }
        } else {
            if (this.temperature > 0) {
                this.timeHeat--;
            }
        }

        if (timeHeat > 60) {
            timeHeat = 0;
            this.temperature = this.temperature + 20;
        }
        if (timeHeat < -60) {
            timeHeat = 0;
            this.temperature = this.temperature - 20;
        }

        timeUpdate++;
        if (timeUpdate > 20) {
            timeUpdate = 0;
            if (Utils.getHeatingLevel(this.temperature) != this.lastLevel) {
                this.lastLevel = Utils.getHeatingLevel(this.temperature);
                updateTEState();
            }
        }
    }
}
