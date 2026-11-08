package net.pufferlab.primal.client.renderer.items;

import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.world.GlobalTickingData;

public class ItemHeatableRenderer extends ItemPrimalRenderer {

    public IHeatableItem impl;

    public ItemHeatableRenderer(IHeatableItem impl) {
        this.impl = impl;
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
            IHeatableItem impl = getHeatableInterface();
            if (impl != null) {
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
    public IHeatableItem getHeatableInterface() {
        return this.impl;
    }
}
