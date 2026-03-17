package net.pufferlab.primal.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.entities.ModelBipedPrimal;

public interface IWearable {

    public enum WearableType {
        ITEM,
        BAUBLES
    }

    default ModelBipedPrimal getWearableModel(EntityLivingBase entity, ItemStack stack) {
        return null;
    }

    default ModelBipedPrimal getWearableModelExtra(EntityLivingBase entity, ItemStack stack) {
        return null;
    }

    default WearableType getWearableType() {
        return WearableType.ITEM;
    }
}
