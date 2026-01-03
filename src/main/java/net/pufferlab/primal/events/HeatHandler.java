package net.pufferlab.primal.events;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.pufferlab.primal.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HeatHandler {

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (event.itemStack.hasTagCompound()) {
            if (event.itemStack.getTagCompound() != null) {
                int temperature = Utils.getTemperatureFromNBT(event.itemStack.getTagCompound());
                if (temperature > 30) {
                    event.toolTip.add(Utils.getTemperatureTooltip(temperature));
                }
            }
        }
    }
}
