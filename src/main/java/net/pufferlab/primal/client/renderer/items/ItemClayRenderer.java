package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.utils.Utils;
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
    public ModelPrimal[] getModel(ItemStack stack) {
        return models;
    }

    @Override
    public int[] getMeta() {
        return modelsMeta;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

    @Override
    public boolean shouldScaleModel(ItemStack stack) {
        if (stack.getItemDamage() == crucibleMeta) {
            return true;
        }
        return false;
    }
}
