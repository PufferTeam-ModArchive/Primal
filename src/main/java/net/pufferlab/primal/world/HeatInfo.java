package net.pufferlab.primal.world;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.utils.HeatUtils;

public class HeatInfo {

    public long worldTime;
    public int maxTemperature;
    public float multiplier;
    public int lastTemperature;

    public HeatInfo(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void writeToNBT(NBTTagCompound tag) {
        HeatUtils.setWorldTimeToNBT(tag, this.worldTime);
        HeatUtils.setMaxTemperatureToNBT(tag, this.maxTemperature);
        HeatUtils.setMultiplierToNBT(tag, this.multiplier);
        HeatUtils.setTemperatureToNBT(tag, this.lastTemperature);
    }

    public void readFromNBT(NBTTagCompound tag) {
        this.worldTime = HeatUtils.getWorldTimeFromNBT(tag);
        this.maxTemperature = HeatUtils.getMaxTemperatureFromNBT(tag);
        this.multiplier = HeatUtils.getMultiplierFromNBT(tag);
        this.lastTemperature = HeatUtils.getTemperatureFromNBT(tag);
    }

    public void setTemperatureInfo(long worldTime, int maxTemperature, float multiplier, int lastTemperature) {
        this.worldTime = worldTime;
        this.maxTemperature = maxTemperature;
        this.multiplier = multiplier;
        this.lastTemperature = lastTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getLastTemperature() {
        return this.lastTemperature;
    }

    public int getMaxTemperature() {
        return this.maxTemperature;
    }

    public int getTemperature() {
        return HeatUtils.getInterpolatedTemperature(
            GlobalTickingData.getTickTime(),
            this.worldTime,
            this.lastTemperature,
            this.multiplier,
            this.maxTemperature);
    }
}
