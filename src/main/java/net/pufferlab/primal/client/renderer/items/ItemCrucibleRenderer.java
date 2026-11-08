package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelCrucible;
import net.pufferlab.primal.client.models.ModelPrimal;
import net.pufferlab.primal.items.IHeatableItem;

public class ItemCrucibleRenderer extends ItemPrimalRenderer {

    public IHeatableItem impl;

    ModelPrimal[] modelCrucible = { new ModelCrucible() };

    public ItemCrucibleRenderer(IHeatableItem impl) {
        this.impl = impl;
    }

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

    @Override
    public IHeatableItem getHeatableInterface() {
        return this.impl;
    }
}
