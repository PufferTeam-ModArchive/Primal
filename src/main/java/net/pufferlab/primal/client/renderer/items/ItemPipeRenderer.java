package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelPipe;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemPipeRenderer extends ItemPrimalRenderer {

    ModelPipe[] modelPipe = { new ModelPipe() };

    public ItemPipeRenderer() {
        super(0.0F, 0.5F, 0.0F);
        modelPipe[0].tube[0].isHidden = false;
        modelPipe[0].tube[2].isHidden = false;
    }

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelPipe;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }
}
