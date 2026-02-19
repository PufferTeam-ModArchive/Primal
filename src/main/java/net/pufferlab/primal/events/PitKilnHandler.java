package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.blocks.BlockPitKiln;
import net.pufferlab.primal.network.packets.PacketPitKilnPlace;
import net.pufferlab.primal.recipes.PitKilnRecipe;
import net.pufferlab.primal.utils.BlockUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PitKilnHandler implements IEventHandler {

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;
        ItemStack heldItem = event.entityPlayer.getHeldItem();
        if (heldItem != null) {
            if (PitKilnRecipe.hasRecipe(heldItem) && event.face == ForgeDirection.UP.ordinal()) {
                int x = BlockUtils.getBlockX(event.face, event.x);
                int y = BlockUtils.getBlockY(event.face, event.y);
                int z = BlockUtils.getBlockZ(event.face, event.z);
                Block actualBlock = event.world.getBlock(x, y, z);
                if (actualBlock.getMaterial() == Material.air) {
                    boolean hasWalls = BlockUtils.hasSolidWalls(event.world, x, y, z);
                    if (hasWalls) {
                        if (event.isCancelable()) event.setCanceled(true);
                        placePitKiln(event.entityPlayer);
                        sendPitKilnPacket(event.entityPlayer);
                    }
                }
            }
        }
    }

    public static void placePitKiln(EntityPlayer player) {
        MovingObjectPosition mop = BlockUtils.getMovingObjectPositionFromPlayer(player.worldObj, player, false);
        if (mop != null) {
            int x = BlockUtils.getBlockX(mop.sideHit, mop.blockX);
            int y = BlockUtils.getBlockY(mop.sideHit, mop.blockY);
            int z = BlockUtils.getBlockZ(mop.sideHit, mop.blockZ);
            player.worldObj.setBlock(x, y, z, Registry.pit_kiln, 0, 3);
            float hitX = (float) (mop.hitVec.xCoord - x);
            float hitY = (float) (mop.hitVec.yCoord - y);
            float hitZ = (float) (mop.hitVec.zCoord - z);
            Block actualBlock2 = player.worldObj.getBlock(x, y, z);
            if (actualBlock2 instanceof BlockPitKiln) {
                actualBlock2.onBlockActivated(player.worldObj, x, y, z, player, mop.sideHit, hitX, hitY, hitZ);
            }
        }
    }

    public void sendPitKilnPacket(EntityPlayer player) {
        if (player.worldObj.isRemote) {
            Primal.proxy.sendPacketToServer(new PacketPitKilnPlace());
        }
    }
}
