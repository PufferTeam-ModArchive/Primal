package net.pufferlab.primal.events;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.pufferlab.primal.world.ChunkDataManager;
import net.pufferlab.primal.world.scheduling.ChunkPlacerData;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldChunkHandler implements IEventHandler {

    @SubscribeEvent
    public void onPopulate(PopulateChunkEvent.Post event) {
        World world = event.world;
        int cx = event.chunkX;
        int cz = event.chunkZ;
        ChunkPlacerData.tickPlacement(world, cx, cz);
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkDataEvent.Load event) {
        NBTTagCompound data = event.getData();

        ChunkDataManager manager = ChunkDataManager.getChunkDataManager(event.world);
        manager.readFromNBT(data, event.getChunk());
    }

    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save event) {
        NBTTagCompound data = event.getData();

        ChunkDataManager manager = ChunkDataManager.getChunkDataManager(event.world);
        manager.writeToNBT(data, event.getChunk());
    }

}
