package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.blocks.BlockAxle;
import net.pufferlab.primal.utils.BlockUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MotionHandler implements IEventHandler {

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;
        ItemStack heldItem = event.entityPlayer.getHeldItem();
        if (heldItem == null) return;
        if (heldItem.getItem() == Item.getItemFromBlock(Registry.axle) && heldItem.getItemDamage() == 2) {
            if (!event.world.isRemote) {
                placeAxle(event.entityPlayer, event.face);
            } else {
                Primal.proxy.packet.sendAxlePacket(event.entityPlayer, event.face);
            }
        }
    }

    public static void placeAxle(EntityPlayer player, int side) {
        boolean flag = false;
        World world = player.worldObj;
        MovingObjectPosition mop = BlockUtils.getMovingObjectPositionFromPlayer(player.worldObj, player, false);
        if (mop != null) {
            int x = mop.blockX;
            int y = mop.blockY;
            int z = mop.blockZ;
            Block block = world.getBlock(x, y, z);
            if (!(block instanceof BlockAxle)) {
                int blockX = BlockUtils.getBlockX(side, x);
                int blockY = BlockUtils.getBlockY(side, y);
                int blockZ = BlockUtils.getBlockZ(side, z);
                float hitX = (float) (mop.hitVec.xCoord - blockX);
                float hitY = (float) (mop.hitVec.yCoord - blockY);
                float hitZ = (float) (mop.hitVec.zCoord - blockZ);
                Block block2 = world.getBlock(blockX, blockY, blockZ);
                if (block2 instanceof BlockAxle block3) {
                    flag = block3.onBlockActivated(world, blockX, blockY, blockZ, player, side, hitX, hitY, hitZ);
                }
            }
        }
        if (flag) {
            Primal.proxy.packet.sendSwingPacket(player);
        }
    }

}
