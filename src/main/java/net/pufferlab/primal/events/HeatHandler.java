package net.pufferlab.primal.events;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.events.ticks.GlobalTickingData;
import net.pufferlab.primal.utils.TemperatureUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HeatHandler {

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (event.itemStack.hasTagCompound()) {
            NBTTagCompound tag = event.itemStack.getTagCompound();
            if (tag != null) {
                event.toolTip.add(
                    Utils.getTemperatureTooltip(
                        TemperatureUtils
                            .getInterpolatedTemperature(GlobalTickingData.getTickTime(event.entity.worldObj), tag)));
            }
        }
    }
}
