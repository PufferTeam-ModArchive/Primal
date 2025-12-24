package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelForge;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemForgeRenderer extends ItemPrimalRenderer {

    ModelForge modelForge = new ModelForge();

    int forgeMeta = 0;

    @Override
    public ModelPrimal getItemBlockModel(ItemStack stack) {
        return modelForge;
    }

    @Override
    public int getItemBlockMeta() {
        return forgeMeta;
    }

    @Override
    public boolean isItemBlock() {
        return true;
    }

}
