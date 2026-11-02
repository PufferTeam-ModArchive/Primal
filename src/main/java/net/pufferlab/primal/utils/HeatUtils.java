package net.pufferlab.primal.utils;

import java.util.*;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.world.GlobalTickingData;

public class HeatUtils {

    private static final Map<Item, IHeatableItem> heatableItems = new HashMap<>();
    private static final Map<Item, int[]> heatableMetaItems = new HashMap<>();
    private static final Map<Item, Item> heatableMaskItems = new HashMap<>();
    private static final List<Item> heatableList = new ArrayList<>();

    public static final String tagTemperature = "temperature";
    public static final String tagMaxTemperature = "maxTemperature";
    public static final String tagWorldTime = "worldTime";
    public static final String tagMultiplier = "multiplier";

    public static List<Item> getBuiltinHeatableItems() {
        List<Item> itemList = new ArrayList<>();

        for (Object obj : ItemUtils.getItemRegistry()) {
            if (obj instanceof Item item) {
                if (item instanceof IHeatableItem heat) {
                    if (heat.hasItemRendering()) {
                        itemList.add(item);
                    }
                }
            }
        }
        return itemList;
    }

    public static List<Item> getHeatableItems() {
        return heatableList;
    }

    public static int[] getHeatableMeta(Item item) {
        return heatableMetaItems.get(item);
    }

    public static Item getHeatableMask(Item item) {
        return heatableMaskItems.get(item);
    }

    public static class ImplInfo {

        int[] metas;
        MetalType[] metalTypes;

        public ImplInfo(int meta, MetalType metalType) {
            metas = new int[] { meta };
            metalTypes = new MetalType[] { metalType };
        }

        public void addItem(int meta, MetalType metalType) {
            metas = Utils.append(metas, meta);
            metalTypes = Utils.append(metalTypes, metalType);
        }
    }

    public static final Map<Item, ImplInfo> implInfo = new HashMap<>();

    public static void registerCompatImpl(Item item, int meta, MetalType metal) {
        ImplInfo impl = implInfo.get(item);
        if (impl == null) {
            impl = new ImplInfo(meta, metal);
            implInfo.put(item, impl);
        } else {
            impl.addItem(meta, metal);
        }
    }

    public static void setupAllImpl() {
        for (String name : Config.metalHeatingOverride.getStringList()) {
            String[] split = name.split("=");
            ItemStack item = ItemUtils.getItem(split[0]);
            MetalType metal = MetalType.getMetal(split[1]);
            registerCompatImpl(item.getItem(), item.getItemDamage(), metal);
        }

        for (Map.Entry<Item, ImplInfo> entry : implInfo.entrySet()) {
            Item item = entry.getKey();
            ImplInfo info = entry.getValue();
            int[] metas = info.metas;
            MetalType[] metalTypes = info.metalTypes;

            registerImpl(item, metas, null, new IHeatableItem() {

                @Override
                public MetalType getMetal(ItemStack stack) {
                    int index = Utils.getIndex(metas, stack.getItemDamage());
                    if (index < 0) return null;
                    return metalTypes[index];
                }
            });
        }
    }

    public static void registerImpl(Item item, Item mask, MetalType metal) {
        registerImpl(item, new int[] { 0 }, mask, new IHeatableItem() {

            @Override
            public MetalType getMetal(ItemStack stack) {
                return metal;
            }
        });
    }

    public static void registerImpl(Item item, int[] meta, Item mask, IHeatableItem impl) {
        heatableItems.put(item, impl);
        heatableMetaItems.put(item, meta);
        if (mask != null) {
            heatableMaskItems.put(item, mask);
            heatableList.add(item);
        }
    }

    public static boolean hasImpl(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof IHeatableItem) return true;
        if (heatableItems.containsKey(item)) {
            int[] array = heatableMetaItems.get(item);
            if (array == null) return true;
            if (Utils.contains(array, stack.getItemDamage()) || item.isDamageable()) {
                return true;
            }
        }
        return false;
    }

    public static IHeatableItem getImpl(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof IHeatableItem item2) return item2;
        if (heatableItems.containsKey(item)) {
            int[] array = heatableMetaItems.get(item);
            if (Utils.contains(array, stack.getItemDamage()) || item.isDamageable()) {
                return heatableItems.get(item);
            }
        }
        return null;
    }

    public static int getTemperatureFromNBT(NBTTagCompound tag) {
        if (tag == null) return 0;
        if (tag.hasKey(tagTemperature)) {
            return tag.getInteger(tagTemperature);
        }
        return 0;
    }

    public static void setTemperatureToNBT(NBTTagCompound tag, int temperature) {
        if (tag == null) return;
        tag.setInteger(tagTemperature, temperature);
    }

    public static int getMaxTemperatureFromNBT(NBTTagCompound tag) {
        if (tag == null) return 0;
        if (tag.hasKey(tagMaxTemperature)) {
            return tag.getInteger(tagMaxTemperature);
        }
        return 0;
    }

    public static void setMaxTemperatureToNBT(NBTTagCompound tag, int temperature) {
        if (tag == null) return;
        tag.setInteger(tagMaxTemperature, temperature);
    }

    public static long getWorldTimeFromNBT(NBTTagCompound tag) {
        if (tag == null) return 0;
        if (tag.hasKey(tagWorldTime)) {
            return tag.getLong(tagWorldTime);
        }
        return 0;
    }

    public static void setWorldTimeToNBT(NBTTagCompound tag, long worldTime) {
        if (tag == null) return;
        tag.setLong(tagWorldTime, worldTime);
    }

    public static float getMultiplierFromNBT(NBTTagCompound tag) {
        if (tag == null) return 0.0F;
        if (tag.hasKey(tagMultiplier)) {
            return tag.getFloat(tagMultiplier);
        }
        return 0.0F;
    }

    public static void setMultiplierToNBT(NBTTagCompound tag, float multiplier) {
        if (tag == null) return;
        tag.setFloat(tagMultiplier, multiplier);
    }

    public static void setInterpolatedTemperatureToNBT(NBTTagCompound tag, World world, float multiplier,
        int maxTemperature) {
        setInterpolatedTemperatureToNBT(
            tag,
            world,
            multiplier,
            HeatUtils.getInterpolatedTemperature(GlobalTickingData.getTickTime(world), tag),
            maxTemperature);
    }

    public static void setInterpolatedTemperatureToNBT(NBTTagCompound tag, World world, float multiplier) {
        setInterpolatedTemperatureToNBT(
            tag,
            world,
            multiplier,
            HeatUtils.getInterpolatedTemperature(GlobalTickingData.getTickTime(world), tag),
            getMaxTemperatureFromNBT(tag));
    }

    public static void setInterpolatedTemperatureToNBT(NBTTagCompound tag, World world, float multiplier,
        int currentTemperature, int maxTemperature) {
        setMaxTemperatureToNBT(tag, maxTemperature);
        setTemperatureToNBT(tag, currentTemperature);
        setWorldTimeToNBT(tag, GlobalTickingData.getTickTime(world));
        setMultiplierToNBT(tag, multiplier);
    }

    public static void transferInterpolatedTemperatureToNBT(NBTTagCompound to, NBTTagCompound from) {
        setMaxTemperatureToNBT(to, getMaxTemperatureFromNBT(from));
        setTemperatureToNBT(to, getTemperatureFromNBT(from));
        setWorldTimeToNBT(to, getWorldTimeFromNBT(from));
        setMultiplierToNBT(to, getMultiplierFromNBT(from));
    }

    public static void resetTemperatureToNBT(NBTTagCompound tag) {
        if (tag == null) return;
        if (tag.hasKey(tagMaxTemperature)) {
            tag.removeTag(tagMaxTemperature);
        }
        if (tag.hasKey(tagMultiplier)) {
            tag.removeTag(tagMultiplier);
        }
        if (tag.hasKey(tagWorldTime)) {
            tag.removeTag(tagWorldTime);
        }
        if (tag.hasKey(tagTemperature)) {
            tag.removeTag(tagTemperature);
        }

    }

    public static int getInterpolatedTemperature(long currentTime, NBTTagCompound tag) {
        long worldTime = getWorldTimeFromNBT(tag);
        int lastTemperature = getTemperatureFromNBT(tag);
        float multiplier = getMultiplierFromNBT(tag);
        int maxTemperature = getMaxTemperatureFromNBT(tag);
        return getInterpolatedTemperature(currentTime, worldTime, lastTemperature, multiplier, maxTemperature);
    }

    public static int getInterpolatedTemperature(long currentTime, long worldTime, int lastTemperature,
        float multiplier, int maxTemperature) {
        int timePassed = Utils.toInt(currentTime - worldTime);
        int newTemperature = (int) (lastTemperature + ((timePassed / 5) * multiplier));
        if (newTemperature < 0) {
            return 0;
        }
        if (newTemperature > maxTemperature) {
            return maxTemperature;
        }
        return newTemperature;
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

    public static short getHeatingColor(int temperature) {
        int heatingLevel = HeatUtils.getHeatingLevel(temperature);
        if (heatingLevel > 0) {
            if (heatingLevel < 3) {
                return Constants.lightHeat1;
            }
            if (heatingLevel < 5) {
                return Constants.lightHeat2;
            }
            if (heatingLevel < 8) {
                return Constants.lightHeat3;
            }
        }
        return Constants.lightNone;
    }

    public static String getFiredTooltip(boolean isFired) {
        if (isFired) {
            return Utils.translate("heat." + Primal.MODID + ".fired.desc");
        } else {
            return Utils.translate("heat." + Primal.MODID + ".unfired.desc");
        }
    }

    public static String getTemperatureTooltip(int temperature) {
        return Utils.translate("heat." + Primal.MODID + ".temperature.desc", temperature);
    }
}
