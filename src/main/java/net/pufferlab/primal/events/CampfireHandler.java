package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
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
                    tef.setFired(true);
                }
            }
        }

        if (Utils.containsStack(heldItem, Utils.getModItem("misc", "icon", "campfire", 1))) {
            int x = Utils.getBlockX(event.face, event.x);
            int y = Utils.getBlockY(event.face, event.y);
            int z = Utils.getBlockZ(event.face, event.z);
            Utils.placeSilent(heldItem, event.world, x, y, z, Registry.campfire, 5, event.entityPlayer);
            TileEntity te = event.world.getTileEntity(x, y, z);
            if (te instanceof TileEntityCampfire tef) {
                tef.isBuilt = true;
                if (!event.entityPlayer.capabilities.isCreativeMode) {
                    heldItem.stackSize--;
                }
                tef.setInventorySlotContentsUpdate(1, Utils.getModItem("misc", "item", "straw_kindling", 1));
                for (int i = 2; i < 6; i++) {
                    tef.setInventorySlotContentsUpdate(i, Utils.getModItem("misc", "item", "firewood", 1));
                }
            }
        }

        Block block = event.world.getBlock(event.x, event.y, event.z);
        if (!block.hasTileEntity(event.world.getBlockMetadata(event.x, event.y, event.z))) {
            if (Utils.containsOreDict(heldItem, "kindling")) {
                int x = Utils.getBlockX(event.face, event.x);
                int y = Utils.getBlockY(event.face, event.y);
                int z = Utils.getBlockZ(event.face, event.z);
                Block blockBelow = event.world.getBlock(x, y - 1, z);
                if (blockBelow.isSideSolid(event.world, x, y - 1, z, ForgeDirection.UP)) {
                    Utils.placeSilent(heldItem, event.world, x, y, z, Registry.campfire, 0, event.entityPlayer);
                    Block block1 = event.world.getBlock(x, y, z);
                    block1.onBlockActivated(event.world, x, y, z, event.entityPlayer, event.face, 0.5F, 0.5F, 0.5F);
                }
            }
        }

    }
}
