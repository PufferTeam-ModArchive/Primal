package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.utils.TemperatureUtils;

public interface IHeatableItem {

    default void updateHeat(ItemStack stack, World world, float multiplier, int maxTemperature) {
        NBTTagCompound tag = Utils.getOrCreateTagCompound(stack);
        TemperatureUtils.setInterpolatedTemperatureToNBT(tag, world, multiplier, maxTemperature);
    };

    default void onUpdateHeat(ItemStack stack, World worldIn) {
        if (stack != null) {
            NBTTagCompound tag = stack.getTagCompound();
            if (stack.getItem() instanceof IHeatableItem) {
                float multiplier = TemperatureUtils.getMultiplierFromNBT(tag);
                if (multiplier > 0) {
                    updateHeat(stack, worldIn, -1.0F, 1300);
                }
            }
        }
    }
}
