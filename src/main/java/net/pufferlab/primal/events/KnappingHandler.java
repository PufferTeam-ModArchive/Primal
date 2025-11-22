package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.KnappingType;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class KnappingHandler {

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;
        ItemStack heldItem = event.entityPlayer.getHeldItem();
        if (heldItem != null) {
            KnappingType type = KnappingType.getType(heldItem);
            Block block = event.world.getBlock(event.x, event.y, event.z);
            int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
            if (block.hasTileEntity(meta)) return;
            if (type != null) {
                int containerId = KnappingType.getHandler(type);
                event.entityPlayer.openGui(Primal.instance, containerId, event.world, event.x, event.y, event.z);
                event.entityPlayer.swingItem();
            }

        }
    }
}
