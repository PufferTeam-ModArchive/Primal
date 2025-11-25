package net.pufferlab.primal.events;

import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pufferlab.primal.tileentities.TileEntityCampfire;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CampfireHandler {

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;
        ItemStack heldItem = event.entityLiving.getHeldItem();
        if (heldItem != null) {
            if (heldItem.getItem() instanceof ItemFlintAndSteel) {
                TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
                if (te instanceof TileEntityCampfire tef) {
                    tef.isFired = true;
                    event.world.markBlockRangeForRenderUpdate(event.x, event.y, event.z, event.x, event.y, event.z);
                    event.world.markBlockForUpdate(event.x, event.y, event.z);
                    tef.markDirty();
                }
            }
        }
    }
}
