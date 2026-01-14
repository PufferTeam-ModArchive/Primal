package net.pufferlab.primal.events;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.utils.FluidUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class InventoryHandler implements IEventHandler {

    private final Item itemBarrel = Item.getItemFromBlock(Registry.barrel);
    private final Item itemCrucible = Item.getItemFromBlock(Registry.crucible);
    private final Item itemLargeVessel = Item.getItemFromBlock(Registry.large_vessel);

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (event.itemStack.hasTagCompound()) {
            if (event.itemStack.getItem() == itemBarrel || event.itemStack.getItem() == itemCrucible) {
                String tooltip = FluidUtils.getFluidInfoFromNBT(event.itemStack.getTagCompound());
                if (tooltip != null) {
                    event.toolTip.add(tooltip);
                }
                String tooltipOutput = FluidUtils.getFluidInfoOutputFromNBT(event.itemStack.getTagCompound());
                if (tooltipOutput != null) {
                    event.toolTip.add(tooltipOutput);
                }
            }
        }

        if (event.itemStack.getItem() == itemLargeVessel) {
            if (event.itemStack.hasTagCompound()) {
                List<String> tooltips = Utils.getItemStackNameListFromNBT(event.itemStack.getTagCompound());
                event.toolTip.addAll(tooltips);
                String tooltip = FluidUtils.getFluidInfoFromNBT(event.itemStack.getTagCompound());
                if (tooltip != null) {
                    event.toolTip.add(tooltip);
                }
            }
        }
    }
}
