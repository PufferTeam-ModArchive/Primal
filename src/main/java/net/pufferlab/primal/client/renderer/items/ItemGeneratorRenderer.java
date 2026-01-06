package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.models.ModelAxle;
import net.pufferlab.primal.client.models.ModelGenerator;
import net.pufferlab.primal.client.models.ModelPrimal;

public class ItemGeneratorRenderer extends ItemPrimalRenderer {

    public ItemGeneratorRenderer() {
        super(0.0F, 0.5F, 0.0F);
    }

    ModelGenerator[] modelGenerator = new ModelGenerator[] { new ModelGenerator() };
    ModelPrimal[] modelGeneratorWithAxle = new ModelPrimal[] { new ModelAxle(), new ModelGenerator() };

    @Override
    public ModelPrimal[] getModel(ItemStack stack) {
        return modelGenerator;
    }

    @Override
    public ModelPrimal[] getModelToRender(int index) {
        return modelGeneratorWithAxle;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }
}
