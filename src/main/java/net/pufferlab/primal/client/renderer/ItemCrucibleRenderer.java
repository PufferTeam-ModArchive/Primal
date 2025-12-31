package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelCrucible;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemCrucibleRenderer extends ItemPrimalRenderer {

    ModelCrucible[] modelCrucible = new ModelCrucible[] { new ModelCrucible() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelCrucible;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

    @Override
    public boolean shouldScaleModel(ItemStack stack) {
        return true;
    }

    @Override
    public boolean handleTemperatureRendering() {
        return true;
    }
}
