package net.pufferlab.primal.compat.baubles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import baubles.api.BaubleType;
import baubles.api.expanded.IBaubleExpanded;

public interface IBaubleItem extends IBaubleExpanded {

    default BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.UNIVERSAL;
    }

    default void onWornTick(ItemStack itemstack, EntityLivingBase player) {}

    default void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

    default void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

    default boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    default boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}
