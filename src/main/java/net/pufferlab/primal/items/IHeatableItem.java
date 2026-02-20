package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.MetalType;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.GlobalTickingData;

public interface IHeatableItem {

    default void updateHeat(ItemStack stack, World world, float multiplier, int maxTemperature) {
        NBTTagCompound tag = Utils.getOrCreateTagCompound(stack);
        HeatUtils.setInterpolatedTemperatureToNBT(tag, world, multiplier, maxTemperature);
    };

    default void updateHeat(ItemStack stack, World world, float multiplier, int temperature, int maxTemperature) {
        NBTTagCompound tag = Utils.getOrCreateTagCompound(stack);
        HeatUtils.setInterpolatedTemperatureToNBT(tag, world, multiplier, temperature, maxTemperature);
    };

    default void onUpdateHeat(ItemStack stack, World worldIn) {
        onUpdateHeat(stack, worldIn, -1.0F);
    }

    default void onUpdateHeat(ItemStack stack, World worldIn, float newModifier) {
        if (stack != null) {
            NBTTagCompound tag = stack.getTagCompound();
            if (HeatUtils.hasImpl(stack)) {
                int temperature = HeatUtils.getInterpolatedTemperature(GlobalTickingData.getTickTime(worldIn), tag);
                if (temperature <= 0) {
                    HeatUtils.resetTemperatureToNBT(tag);
                    if (tag != null) {
                        if (tag.hasNoTags()) {
                            stack.setTagCompound(null);
                        }
                    }
                }

                float multiplier = HeatUtils.getMultiplierFromNBT(tag);
                if (multiplier > 0) {
                    updateHeat(stack, worldIn, newModifier, 1300);
                }
            }
        }
    }

    default MetalType getMetal(ItemStack stack) {
        return null;
    };

    default int getMeltingTemperature(ItemStack stack) {
        return -1;
    }

    default int getForgingTemperature(ItemStack stack) {
        return -1;
    }

    default int getWeldingTemperature(ItemStack stack) {
        return -1;
    }
}
