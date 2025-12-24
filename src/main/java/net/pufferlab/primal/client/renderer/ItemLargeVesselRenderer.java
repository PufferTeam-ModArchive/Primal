package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelLargeVessel;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemLargeVesselRenderer extends ItemPrimalRenderer {

    ModelLargeVessel modelLargeVessel = new ModelLargeVessel();

    int largeVesselMeta = 0;

    @Override
    public ModelPrimal getItemBlockModel(ItemStack stack) {
        return modelLargeVessel;
    }

    @Override
    public int getItemBlockMeta() {
        return largeVesselMeta;
    }

    @Override
    public boolean isItemBlock() {
        return true;
    }

}
