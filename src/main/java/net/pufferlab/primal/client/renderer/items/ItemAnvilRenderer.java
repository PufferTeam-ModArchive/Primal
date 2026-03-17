package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.blocks.ModelAnvil;
import net.pufferlab.primal.client.models.blocks.ModelPrimal;

public class ItemAnvilRenderer extends ItemPrimalRenderer {

    ModelPrimal[] modelAnvil = { new ModelAnvil() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        modelAnvil[0].setType(stack.getItemDamage());
        return modelAnvil;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }
}
