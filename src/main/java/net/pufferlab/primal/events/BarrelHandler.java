package net.pufferlab.primal.events;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BarrelHandler {

    private final Item itemBarrel = Item.getItemFromBlock(Registry.barrel);

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (event.itemStack.getItem() == itemBarrel) {
            if (event.itemStack.hasTagCompound()) {
                String tooltip = Utils.getFluidInfoFromNBT(event.itemStack.getTagCompound());
                if (tooltip != null) {
                    event.toolTip.add(tooltip);
                }
            }
        }
    }
}
