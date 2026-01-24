package net.pufferlab.primal.events;

import static net.minecraftforge.event.entity.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK;

import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GroundRockHandler extends PileHandler {

    @Override
    public Block getPileBlock() {
        return Registry.ground_rock;
    }

    @Override
    public String[] getPileOreDicts() {
        return Constants.groundRockOreDicts;
    }

    @Override
    public boolean keepItemMeta() {
        return true;
    }

    @SubscribeEvent
    public void playerInteractEventHandler2(PlayerInteractEvent event) {
        if (event.action == RIGHT_CLICK_BLOCK) {
            Block block = event.world.getBlock(event.x, event.y, event.z);
            if (block == getPileBlock()) {
                event.world.setBlockToAir(event.x, event.y, event.z);
                event.entityPlayer.swingItem();
            }
        }
    }
}
