package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.events.ticks.GlobalTickingData;
import net.pufferlab.primal.utils.TemperatureUtils;

public interface IHeatableItem {

    default void updateHeat(ItemStack stack, World world, float multiplier, int maxTemperature) {
        NBTTagCompound tag = Utils.getOrCreateTagCompound(stack);
        TemperatureUtils.setInterpolatedTemperatureToNBT(tag, world, multiplier, maxTemperature);
    };

    default void updateHeat(ItemStack stack, World world, float multiplier, int temperature, int maxTemperature) {
        NBTTagCompound tag = Utils.getOrCreateTagCompound(stack);
        TemperatureUtils.setInterpolatedTemperatureToNBT(tag, world, multiplier, temperature, maxTemperature);
    };

    default void onUpdateHeat(ItemStack stack, World worldIn) {
        if (stack != null) {
            NBTTagCompound tag = stack.getTagCompound();
            if (TemperatureUtils.hasImpl(stack)) {
                float multiplier = TemperatureUtils.getMultiplierFromNBT(tag);
                if (multiplier > 0) {
                    updateHeat(stack, worldIn, -1.0F, 1300);
                }
                if (TemperatureUtils.getInterpolatedTemperature(GlobalTickingData.getTickTime(worldIn), tag) == 0) {
                    // TemperatureUtils.resetTemperatureToNBT(tag);
                }
            }
        }
    }

    default int getMeltingTemperature(ItemStack stack) {
        return -1;
    }

    default int getWeldingTemperature(ItemStack stack) {
        return -1;
    }
}
