package net.pufferlab.primal.events;

import java.util.Arrays;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class LargeVesselHandler {

    private final Item itemLargeVessel = Item.getItemFromBlock(Registry.large_vessel);

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (event.itemStack.getItem() == itemLargeVessel) {
            if (event.itemStack.hasTagCompound()) {
                String[] tooltips = Utils.getItemStackNameListFromNBT(event.itemStack.getTagCompound());
                event.toolTip.addAll(Arrays.asList(tooltips));
                String tooltip = Utils.getFluidInfoFromNBT(event.itemStack.getTagCompound());
                if (tooltip != null) {
                    event.toolTip.add(tooltip);
                }
            }
        }
    }
}
