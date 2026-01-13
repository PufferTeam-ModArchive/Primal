package net.pufferlab.primal.utils;

import java.util.*;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.events.ticks.GlobalTickingData;
import net.pufferlab.primal.items.IHeatableItem;

public class TemperatureUtils {

    private static final Map<Item, IHeatableItem> heatableItems = new HashMap<>();
    private static final Map<Item, List<Integer>> heatableMetaItems = new HashMap<>();
    private static final Map<Item, Item> heatableMaskItems = new HashMap<>();
    private static final List<Item> heatableList = new ArrayList<>();
    private static final IHeatableItem basicImpl = new IHeatableItem() {};

    public static List<Item> getHeatableItems() {
        return heatableList;
    }

    public static List<Integer> getHeatableMeta(Item item) {
        return heatableMetaItems.get(item);
    }

    public static Item getHeatableMask(Item item) {
        return heatableMaskItems.get(item);
    }

    public static void registerImpl(Item item, List<Integer> meta, Item mask) {
        registerImpl(item, meta, mask, basicImpl);
    }

    public static void registerImpl(Item item, List<Integer> meta, Item mask, IHeatableItem impl) {
        heatableItems.put(item, impl);
        heatableMetaItems.put(item, meta);
        heatableMaskItems.put(item, mask);
        heatableList.add(item);
    }

    public static boolean hasImpl(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof IHeatableItem) return true;
        if (heatableItems.containsKey(item)) {
            if (item.isDamageable()) return true;
            if (heatableMetaItems.get(item) == null) return true;
            if (heatableMetaItems.get(item)
                .contains(stack.getItemDamage())) {
                return true;
            }
        }
        return false;
    }

    public static IHeatableItem getImpl(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof IHeatableItem item2) return item2;
        if (heatableItems.containsKey(item)) {
            if (heatableMetaItems.get(item)
                .contains(stack.getItemDamage())) {
                return heatableItems.get(item);
            }
        }
        return null;
    }

    public static int getTemperatureFromNBT(NBTTagCompound tag) {
        if (tag == null) return 0;
        if (tag.hasKey("temperature")) {
            return tag.getInteger("temperature");
        }
        return 0;
    }

    public static void setTemperatureToNBT(NBTTagCompound tag, int temperature) {
        if (tag == null) return;
        tag.setInteger("temperature", temperature);
    }

    public static int getMaxTemperatureFromNBT(NBTTagCompound tag) {
        if (tag == null) return 0;
        if (tag.hasKey("maxTemperature")) {
            return tag.getInteger("maxTemperature");
        }
        return 0;
    }

    public static void setMaxTemperatureToNBT(NBTTagCompound tag, int temperature) {
        if (tag == null) return;
        tag.setInteger("maxTemperature", temperature);
    }

    public static long getWorldTimeFromNBT(NBTTagCompound tag) {
        if (tag == null) return 0;
        if (tag.hasKey("worldTime")) {
            return tag.getLong("worldTime");
        }
        return 0;
    }

    public static void setWorldTimeToNBT(NBTTagCompound tag, long worldTime) {
        if (tag == null) return;
        tag.setLong("worldTime", worldTime);
    }

    public static float getMultiplierFromNBT(NBTTagCompound tag) {
        if (tag == null) return 0.0F;
        if (tag.hasKey("multiplier")) {
            return tag.getFloat("multiplier");
        }
        return 0.0F;
    }

    public static void setMultiplierToNBT(NBTTagCompound tag, float multiplier) {
        if (tag == null) return;
        tag.setFloat("multiplier", multiplier);
    }

    public static void setInterpolatedTemperatureToNBT(NBTTagCompound tag, World world, float multiplier,
        int maxTemperature) {
        setInterpolatedTemperatureToNBT(
            tag,
            world,
            multiplier,
            TemperatureUtils.getInterpolatedTemperature(GlobalTickingData.getTickTime(world), tag),
            maxTemperature);
    }

    public static void setInterpolatedTemperatureToNBT(NBTTagCompound tag, World world, float multiplier,
        int currentTemperature, int maxTemperature) {
        setMaxTemperatureToNBT(tag, maxTemperature);
        setTemperatureToNBT(tag, currentTemperature);
        setWorldTimeToNBT(tag, GlobalTickingData.getTickTime(world));
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
            int newTemperature = (int) (lastTemperature + ((timePassed / 10) * multiplier));
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

    public static int getHeatingLevel(int temperature) {
        if (temperature > 1 && temperature < 200) {
            return 1;
        } else if (temperature >= 200 && temperature < 400) {
            return 2;
        } else if (temperature >= 400 && temperature < 600) {
            return 3;
        } else if (temperature >= 600 && temperature < 800) {
            return 4;
        } else if (temperature >= 800 && temperature < 1000) {
            return 5;
        } else if (temperature >= 1000 && temperature < 1200) {
            return 6;
        } else if (temperature >= 1200) {
            return 7;
        } else {
            return 0;
        }
    }
}
