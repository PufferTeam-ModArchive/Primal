package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.world.GlobalTickingData;

public class ItemHeatableRenderer extends ItemPrimalRenderer {

    public Item renderItem;

    public ItemHeatableRenderer() {
        renderItem = null;
    }

    public ItemHeatableRenderer(Item renderItem) {
        this.renderItem = renderItem;
    }

    @Override
    public boolean handleRendering(ItemStack stack) {
        return true;
    }

    @Override
    public boolean handleTemperatureRendering() {
        return true;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (handleTemperatureRendering()) {
            if (HeatUtils.hasImpl(item)) {
                IHeatableItem impl = HeatUtils.getImpl(item);
                if (!impl.shouldRenderTemperature(item)) return false;
                if (item.hasTagCompound()) {
                    int temperature = impl.getTemperature(item, GlobalTickingData.getClientTickTime());
                    if (temperature <= Config.temperatureCap.getInt()) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return super.handleRenderType(item, type);
    }

    @Override
    public Item getMaskItem() {
        return this.renderItem;
    }
}
