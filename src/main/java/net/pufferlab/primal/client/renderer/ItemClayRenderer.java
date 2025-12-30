package net.pufferlab.primal.client.renderer;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.client.models.ModelCrucible;
import net.pufferlab.primal.client.models.ModelLargeVessel;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemClayRenderer extends ItemPrimalRenderer {

    ModelLargeVessel modelLargeVessel = new ModelLargeVessel();
    public final int largeVesselMeta = Utils.getIndex(Constants.clayItems, "clay_large_vessel");
    ModelCrucible modelCrucible = new ModelCrucible();
    public final int crucibleMeta = Utils.getIndex(Constants.clayItems, "clay_crucible");

    ModelPrimal[] models;
    int[] modelsMeta;

    public ItemClayRenderer() {
        modelLargeVessel.setType(1);
        modelCrucible.setType(1);
        models = new ModelPrimal[] { modelLargeVessel, modelCrucible };
        modelsMeta = new int[] { largeVesselMeta, crucibleMeta };
    }

    @Override
    public ModelPrimal[] getItemBlockModel(ItemStack stack) {
        return models;
    }

    @Override
    public int[] getItemBlockMeta() {
        return modelsMeta;
    }

    @Override
    public boolean isItemBlock(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasBigModel(ItemStack stack) {
        if (stack.getItemDamage() == crucibleMeta) {
            return true;
        }
        return false;
    }

    @Override
    public boolean needsNormalItemRender() {
        return true;
    }
}
