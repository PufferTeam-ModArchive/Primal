package net.pufferlab.primal.events;

import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.pufferlab.primal.world.scheduling.ChunkPlacerData;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldHandler implements IEventHandler {

    @SubscribeEvent
    public void onPopulate(PopulateChunkEvent.Post event) {
        World world = event.world;
        int cx = event.chunkX;
        int cz = event.chunkZ;
        ChunkPlacerData.tickPlacement(world, cx, cz);
    }
}
