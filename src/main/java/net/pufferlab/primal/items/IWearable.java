package net.pufferlab.primal.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.entities.IWearableModel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IWearable {

    public enum WearableType {
        ITEM,
        BAUBLES
    }

    default IWearableModel getWearableModel(EntityLivingBase entity, ItemStack stack) {
        return null;
    }

    default IWearableModel getWearableModelExtra(EntityLivingBase entity, ItemStack stack) {
        return null;
    }

    default WearableType getWearableType() {
        return WearableType.ITEM;
    }
}
