package net.pufferlab.primal.events;

import static net.minecraftforge.event.entity.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.BlockLogPile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CharcoalPileHandler {

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.action == RIGHT_CLICK_BLOCK) {
            Block block = event.world.getBlock(event.x, event.y, event.z);
            int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
            ItemStack heldItem = event.entityPlayer.inventory.getCurrentItem();
            if (heldItem == null || block == null) return;
            if (heldItem.getItem() == null) return;
            if (!Utils.containsOreDict(heldItem, Constants.charcoalPileOreDicts)) return;
            if (!(block instanceof BlockLogPile)) {
                if (!block.hasTileEntity(meta) && event.face == ForgeDirection.UP.ordinal()) {
                    int x2 = Utils.getBlockX(event.face, event.x);
                    int y2 = Utils.getBlockY(event.face, event.y);
                    int z2 = Utils.getBlockZ(event.face, event.z);
                    Utils.place(heldItem, event.world, x2, y2, z2, Registry.charcoal_pile, 0, event.entityPlayer);
                }
            }
        }
    }

}
