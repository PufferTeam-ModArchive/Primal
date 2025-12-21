package net.pufferlab.primal.client.renderer;

import net.pufferlab.primal.client.models.ModelOven;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemOvenRenderer extends ItemPrimalRenderer {

    ModelOven modelOven = new ModelOven();

    int ovenMeta = 0;

    @Override
    public ModelPrimal getItemBlockModel() {
        return modelOven;
    }

    @Override
    public int getItemBlockMeta() {
        return ovenMeta;
    }

    @Override
    public boolean isItemBlock() {
        return true;
    }
}
