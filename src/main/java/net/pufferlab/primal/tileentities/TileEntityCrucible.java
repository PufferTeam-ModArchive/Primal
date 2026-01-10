package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.utils.TemperatureUtils;

public class TileEntityCrucible extends TileEntityFluidInventory implements IHeatable {

    public int timeHeat;
    public int lastLevel;
    public boolean isHeating;

    public int temperature;
    public int maxTemperature;

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
        this.isHeating = tag.getBoolean("isHeating");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("timeHeat", this.timeHeat);
        tag.setInteger("lastLevel", this.lastLevel);
        tag.setBoolean("isHeating", this.isHeating);
    }

    @Override
    public void readFromNBTInventory(NBTTagCompound tag) {
        super.readFromNBTInventory(tag);
        this.temperature = TemperatureUtils.getTemperatureFromNBT(tag);
    }

    @Override
    public void writeToNBTInventory(NBTTagCompound tag) {
        super.writeToNBTInventory(tag);
        TemperatureUtils.setTemperatureToNBT(tag, this.temperature);
    }

    @Override
    public void updateEntity() {
        TileEntity teBelow = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if (teBelow instanceof IHeatable tef) {
            if (tef.isHeatProvider()) {
                this.maxTemperature = tef.getMaxTemperature();
                if (tef.getTemperature() > this.temperature) {
                    if (tef.isFired()) {
                        this.timeHeat++;
                    } else {
                        this.timeHeat--;
                    }
                    this.isHeating = tef.isFired();
                } else {
                    if (this.temperature > 0) {
                        this.timeHeat--;
                    }
                }
            }
        } else {
            if (this.temperature > 0) {
                this.timeHeat--;
            }
            this.isHeating = false;
        }

        if (timeHeat > 3) {
            timeHeat = 0;
            this.temperature++;
        }
        if (timeHeat < -3) {
            timeHeat = 0;
            this.temperature--;
        }

        if (this.needsInventoryUpdate) {
            this.needsInventoryUpdate = false;
            float modifier = 1.0F;
            if (!this.isHeating) {
                modifier = -1.0F;
            }
            updateHeatInventory(modifier, this.maxTemperature);
        }
    }

    @Override
    public void onItemRemoved(ItemStack stack) {
        if (stack != null) {
            if (stack.getItem() instanceof IHeatableItem item) {
                item.updateHeat(stack, this.getWorld(), -1.0F, this.maxTemperature);
            }
        }
    }

    public void updateHeatInventory(float modifier, int maxTemperature) {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getInventoryStack(i);
            if (stack != null) {
                if (stack.getItem() instanceof IHeatableItem item) {
                    item.updateHeat(stack, this.getWorld(), modifier, maxTemperature);
                }
            }
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

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
