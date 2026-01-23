package net.pufferlab.primal.events;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneCategory;
import net.pufferlab.primal.utils.StoneType;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class StoneHandler implements IEventHandler {

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (event.itemStack != null) {
            if (event.itemStack.getItem() == Item.getItemFromBlock(Registry.stone)) {
                StoneType type = StoneType.getStoneType(Registry.stone, event.itemStack.getItemDamage());
                if (type != null) {
                    StoneCategory category = type.category;
                    event.toolTip.add(category.getTranslatedName());
                }
            }
        }
    }
}
