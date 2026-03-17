package net.pufferlab.primal.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.entities.*;

public class ItemStrawClothe extends ItemClothe implements IWearable {

    public ItemStrawClothe(String name, String baubleType) {
        super(name, baubleType);
    }

    @Override
    public IWearableModel getWearableModel(EntityLivingBase entity, ItemStack stack) {
        return switch (this.name) {
            case "straw_hat" -> ModelStrawHat.instance;
            case "straw_shirt" -> ModelStrawShirt.instance;
            case "straw_coat" -> ModelStrawCoat.instance;
            case "straw_sandals" -> ModelStrawSandals.instance;
            default -> null;
        };
    }

    @Override
    public IWearableModel getWearableModelExtra(EntityLivingBase entity, ItemStack stack) {
        return switch (this.name) {
            case "straw_coat" -> ModelStrawPants.instance;
            default -> null;
        };
    }

    @Override
    public WearableType getWearableType() {
        return WearableType.BAUBLES;
    }
}
