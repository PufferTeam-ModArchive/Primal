package net.pufferlab.primal.client.renderer;

import net.pufferlab.primal.client.models.ModelBarrel;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemBarrelRenderer extends ItemPrimalRenderer {

    ModelBarrel modelBarrel = new ModelBarrel();

    int barrelMeta = 0;

    @Override
    public ModelPrimal getItemBlockModel() {
        return modelBarrel;
    }

    @Override
    public int getItemBlockMeta() {
        return barrelMeta;
    }

    @Override
    public boolean isItemBlock() {
        return true;
    }

}
