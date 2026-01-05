package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelGear;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemGearRenderer extends ItemPrimalRenderer {

    public ItemGearRenderer() {
        super(0.0F, 0.0F, 0.0F);
    }

    ModelGear[] modelGear = new ModelGear[] { new ModelGear() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelGear;
    }

    @Override
    public boolean isNormal() {
        return true;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

}
