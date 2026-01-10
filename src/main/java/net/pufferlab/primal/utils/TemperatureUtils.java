package net.pufferlab.primal.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.events.ticks.WorldTickingData;

public class TemperatureUtils {

    public static NBTTagCompound getTemperatureTagList(NBTTagCompound nbt) {
        if (nbt.hasKey("TemperatureInfo")) {
            return (NBTTagCompound) nbt.getTag("TemperatureInfo");
        } else {
            NBTTagCompound tag = new NBTTagCompound();
            nbt.setTag("TemperatureInfo", tag);
            return tag;
        }
    }

    public static int getTemperatureFromNBT(NBTTagCompound nbt) {
        if (nbt == null) return 0;
        NBTTagCompound tag = getTemperatureTagList(nbt);
        if (tag.hasKey("temperature")) {
            return tag.getInteger("temperature");
        }
        return 0;
    }

    public static void setTemperatureToNBT(NBTTagCompound nbt, int temperature) {
        if (nbt == null) return;
        NBTTagCompound tag = getTemperatureTagList(nbt);
        tag.setInteger("temperature", temperature);
    }

    public static int getMaxTemperatureFromNBT(NBTTagCompound nbt) {
        if (nbt == null) return 0;
        NBTTagCompound tag = getTemperatureTagList(nbt);
        if (tag.hasKey("maxTemperature")) {
            return tag.getInteger("maxTemperature");
        }
        return 0;
    }

    public static void setMaxTemperatureToNBT(NBTTagCompound nbt, int temperature) {
        if (nbt == null) return;
        NBTTagCompound tag = getTemperatureTagList(nbt);
        tag.setInteger("maxTemperature", temperature);
    }

    public static long getWorldTimeFromNBT(NBTTagCompound nbt) {
        if (nbt == null) return 0;
        NBTTagCompound tag = getTemperatureTagList(nbt);
        if (tag.hasKey("worldTime")) {
            return tag.getLong("worldTime");
        }
        return 0;
    }

    public static void setWorldTimeToNBT(NBTTagCompound nbt, long worldTime) {
        if (nbt == null) return;
        NBTTagCompound tag = getTemperatureTagList(nbt);
        tag.setLong("worldTime", worldTime);
    }

    public static float getMultiplierFromNBT(NBTTagCompound nbt) {
        if (nbt == null) return 0.0F;
        NBTTagCompound tag = getTemperatureTagList(nbt);
        if (tag.hasKey("multiplier")) {
            return tag.getFloat("multiplier");
        }
        return 0.0F;
    }

    public static void setMultiplierToNBT(NBTTagCompound nbt, float multiplier) {
        if (nbt == null) return;
        NBTTagCompound tag = getTemperatureTagList(nbt);
        tag.setFloat("multiplier", multiplier);
    }

    public static void setInterpolatedTemperatureToNBT(NBTTagCompound tag, World world, float multiplier,
        int maxTemperature) {
        setMaxTemperatureToNBT(tag, maxTemperature);
        setTemperatureToNBT(tag, getInterpolatedTemperature(WorldTickingData.getTickTime(world), tag));
        setWorldTimeToNBT(tag, WorldTickingData.getTickTime(world));
        setMultiplierToNBT(tag, multiplier);
    }

    public static void getInterpolatedTemperatureFromNBT(NBTTagCompound tag) {
        getMaxTemperatureFromNBT(tag);
        getTemperatureFromNBT(tag);
        getWorldTimeFromNBT(tag);
        getMultiplierFromNBT(tag);
    }

    public static int getInterpolatedTemperature(long currentTime, NBTTagCompound tag) {
        if (tag != null) {
            long worldTime = getWorldTimeFromNBT(tag);
            int timePassed = Utils.toInt(currentTime - worldTime);
            int lastTemperature = getTemperatureFromNBT(tag);
            float multiplier = getMultiplierFromNBT(tag);
            int newTemperature = (int) (lastTemperature + ((timePassed / 20) * multiplier));
            int maxTemperature = getMaxTemperatureFromNBT(tag);
            if (newTemperature < 0) {
                return 0;
            }
            if (newTemperature > maxTemperature) {
                return maxTemperature;
            }
            return newTemperature;
        }
        return 0;
    }
}
