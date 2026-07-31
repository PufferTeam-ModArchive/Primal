package net.pufferlab.primal.events;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.pufferlab.primal.Primal;
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
    public void onChunkDataLoad(ChunkDataEvent.Load event) {
        NBTTagCompound data = event.getData();

        ChunkDataManager manager = ChunkDataManager.getDataManager(event.world);
        manager.readFromNBT(data, event.getChunk());
    }

    @SubscribeEvent
    public void onChunkDataSave(ChunkDataEvent.Save event) {
        NBTTagCompound data = event.getData();

        ChunkDataManager manager = ChunkDataManager.getDataManager(event.world);
        manager.writeToNBT(data, event.getChunk());

        // Might sometimes forgot to save some chunks (i guess)
        // manager.remove(event.getChunk());
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        Primal.proxy.packet.sendChunkData(event.getChunk());
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event) {
        ChunkDataManager manager = ChunkDataManager.getClientDataManager();

        // Causes crashes because
        // manager.remove(event.getChunk());
    }

}
