package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.client.models.ModelCrucible;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemCrucibleRenderer extends ItemPrimalRenderer {

    ModelCrucible modelCrucible = new ModelCrucible();

    int crucibleMeta = 0;

    @Override
    public ModelPrimal getItemBlockModel(ItemStack stack) {
        int heat = Utils.getHeatingLevel(Utils.getTemperatureFromNBT(stack.getTagCompound()));
        modelCrucible.setType(heat + 2);
        return modelCrucible;
    }

    @Override
    public int getItemBlockMeta() {
        return crucibleMeta;
    }

    @Override
    public boolean isItemBlock() {
        return true;
    }

    @Override
    public boolean hasBigModel() {
        return true;
    }
}
