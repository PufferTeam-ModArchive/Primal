package net.pufferlab.primal.client.renderer.items;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.world.GlobalTickingData;

public class ItemHeatableRenderer extends ItemPrimalRenderer {

    public Item renderItem;
    public List<Integer> whitelistMeta;
    public boolean hasWhitelist;

    public ItemHeatableRenderer() {
        renderItem = null;
        this.hasWhitelist = false;
    }

    public ItemHeatableRenderer(Item renderItem, List<Integer> whitelist) {
        this.renderItem = renderItem;
        this.hasWhitelist = true;
        this.whitelistMeta = whitelist;
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
        if (this.hasWhitelist) {
            if (!whitelistMeta.contains(item.getItemDamage())) return false;
        }
        if (handleTemperatureRendering()) {
            if (item.hasTagCompound()) {
                int temperature = HeatUtils
                    .getInterpolatedTemperature(GlobalTickingData.getClientTickTime(), item.getTagCompound());
                if (temperature <= Config.temperatureCap.getInt()) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return super.handleRenderType(item, type);
    }

    @Override
    public Item getMaskItem() {
        return this.renderItem;
    }
}
