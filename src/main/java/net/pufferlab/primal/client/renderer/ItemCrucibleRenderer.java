package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelCrucible;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemCrucibleRenderer extends ItemPrimalRenderer {

    ModelCrucible[] modelCrucible = new ModelCrucible[] { new ModelCrucible() };

    int[] crucibleMeta = new int[] { 0 };

    @Override
    public ModelPrimal[] getItemBlockModel(ItemStack stack) {
        return modelCrucible;
    }

    @Override
    public int[] getItemBlockMeta() {
        return crucibleMeta;
    }

    @Override
    public boolean isItemBlock(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasBigModel(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasTemperature() {
        return true;
    }
}
