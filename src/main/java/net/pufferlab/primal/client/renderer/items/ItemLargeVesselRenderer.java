package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelLargeVessel;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemLargeVesselRenderer extends ItemPrimalRenderer {

    ModelLargeVessel[] modelLargeVessel = new ModelLargeVessel[] { new ModelLargeVessel() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelLargeVessel;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

}
