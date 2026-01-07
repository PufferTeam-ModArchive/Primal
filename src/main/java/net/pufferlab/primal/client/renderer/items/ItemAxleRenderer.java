package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.client.models.ModelBracket;
import net.pufferlab.primal.client.models.ModelGear;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemAxleRenderer extends ItemPrimalRenderer {

    public ItemAxleRenderer() {
        super(0.0F, 0.5F, 0.0F);
    }

    ModelPrimal[] modelAxle = new ModelPrimal[] { new ModelAxle(), new ModelGear(), new ModelBracket() };

    public static final int[] modelMeta = new int[] { 0, 1, 2 };

    @Override
    public boolean shouldRenderOffset(ItemStack stack) {
        if (stack.getItemDamage() == 1 || stack.getItemDamage() == 2) {
            return false;
        }
        return true;
    }

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelAxle;
    }

    @Override
    public int[] getMeta() {
        return modelMeta;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }
}
