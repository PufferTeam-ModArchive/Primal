package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.tileentities.TileEntityCast;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CastHandler implements IEventHandler {

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;
        ItemStack heldItem = event.entityLiving.getHeldItem();

        Block block = event.world.getBlock(event.x, event.y, event.z);
        if (!block.hasTileEntity(event.world.getBlockMetadata(event.x, event.y, event.z))) {
            if (Utils.containsOreDict(heldItem, "mold")) {
                int x = BlockUtils.getBlockX(event.face, event.x);
                int y = BlockUtils.getBlockY(event.face, event.y);
                int z = BlockUtils.getBlockZ(event.face, event.z);
                Block blockBelow = event.world.getBlock(x, y - 1, z);
                if (blockBelow.isSideSolid(event.world, x, y - 1, z, ForgeDirection.UP)) {
                    BlockUtils.placeNoConsume(heldItem, event.world, x, y, z, Registry.cast, 0, event.entityPlayer);
                    Block block1 = event.world.getBlock(x, y, z);
                    block1.onBlockActivated(event.world, x, y, z, event.entityPlayer, event.face, 0.5F, 0.5F, 0.5F);
                    TileEntity te = event.world.getTileEntity(x, y, z);
                    if (te instanceof TileEntityCast tef) {
                        tef.castIndex = heldItem.getItemDamage();
                    }
                }
            }
        }

    }
}
