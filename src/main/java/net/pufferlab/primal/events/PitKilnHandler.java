package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.PitKilnRecipe;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PitKilnHandler {

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;
        ItemStack heldItem = event.entityPlayer.getHeldItem();
        if (heldItem != null) {
            if (PitKilnRecipe.hasRecipe(heldItem) && event.face == ForgeDirection.UP.ordinal()) {
                boolean placedBlock = false;
                int x = Utils.getBlockX(event.face, event.x);
                int y = Utils.getBlockY(event.face, event.y);
                int z = Utils.getBlockZ(event.face, event.z);
                Block actualBlock = event.world.getBlock(x, y, z);
                if (actualBlock.getMaterial() == Material.air) {
                    boolean hasWalls = Utils.hasSolidWalls(event.world, x, y, z);
                    if (hasWalls) {
                        event.world.setBlock(x, y, z, Registry.pit_kiln, 0, 2);
                        placedBlock = true;
                    }
                }
                if (placedBlock) {
                    MovingObjectPosition mop = Utils
                        .getMovingObjectPositionFromPlayer(event.world, event.entityPlayer, false);
                    if (mop != null) {
                        int bx = mop.blockX;
                        int by = mop.blockY;
                        int bz = mop.blockZ;

                        float hitX = (float) (mop.hitVec.xCoord - bx);
                        float hitY = (float) (mop.hitVec.yCoord - by);
                        float hitZ = (float) (mop.hitVec.zCoord - bz);
                        Block actualBlock2 = event.world.getBlock(x, y, z);
                        actualBlock2
                            .onBlockActivated(event.world, x, y, z, event.entityPlayer, event.face, hitX, hitY, hitZ);
                        event.entityPlayer.swingItem();
                    }
                }
            }
        }
    }
}
