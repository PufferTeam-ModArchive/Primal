package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.events.ticks.WorldTickingData;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.utils.TemperatureUtils;

public class TileEntityCrucible extends TileEntityFluidInventory implements IHeatable {

    public int timeHeat;
    public int lastLevel;
    public boolean isHeating;

    public int temperature;
    public int maxTemperature;
    public int needsTemperatureUpdate;

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
        this.temperature = tag.getInteger("temperature");
        this.maxTemperature = tag.getInteger("maxTemperature");
        this.needsTemperatureUpdate = tag.getInteger("needsTemperatureUpdate");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("timeHeat", this.timeHeat);
        tag.setInteger("lastLevel", this.lastLevel);
        tag.setBoolean("isHeating", this.isHeating);
        tag.setInteger("temperature", this.temperature);
        tag.setInteger("maxTemperature", this.maxTemperature);
        tag.setInteger("needsTemperatureUpdate", this.needsTemperatureUpdate);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.isHeating = tag.getBoolean("isHeating");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setBoolean("isHeating", this.isHeating);
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
                this.maxTemperature = tef.getMaxTemperature();
                if (tef.getTemperature() > this.temperature) {
                    if (tef.isFired()) {
                        this.timeHeat++;
                    } else {
                        this.timeHeat--;
                    }
                } else {
                    if (this.temperature > 0 && (this.temperature != tef.getTemperature())) {
                        this.timeHeat--;
                    }
                }
                this.isHeating = tef.isFired();
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
                    float modifier2 = modifier;
                    if (TemperatureUtils
                        .getInterpolatedTemperature(WorldTickingData.getTickTime(getWorld()), stack.getTagCompound())
                        > this.temperature) {
                        modifier2 = -0.5F;
                    }
                    item.updateHeat(stack, this.getWorld(), modifier2, maxTemperature);
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
