package net.pufferlab.primitivelife.events;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pufferlab.primitivelife.CommonProxy;
import net.pufferlab.primitivelife.PrimitiveLife;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class KnappingHandler {

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;
        ItemStack heldItem = event.entityPlayer.getHeldItem();
        if (heldItem != null) {
            if (heldItem.getItem() == Items.clay_ball && heldItem.stackSize >= 5) {
                event.entityPlayer.openGui(
                    PrimitiveLife.instance,
                    CommonProxy.GUI_KNAPPING_CLAY,
                    event.world,
                    event.x,
                    event.y,
                    event.z);
                event.entityPlayer.swingItem();
            }
            if (heldItem.getItem() == Items.flint && heldItem.stackSize >= 2) {
                event.entityPlayer.openGui(
                    PrimitiveLife.instance,
                    CommonProxy.GUI_KNAPPING_FLINT,
                    event.world,
                    event.x,
                    event.y,
                    event.z);
                event.entityPlayer.swingItem();
            }
        }
    }
}
