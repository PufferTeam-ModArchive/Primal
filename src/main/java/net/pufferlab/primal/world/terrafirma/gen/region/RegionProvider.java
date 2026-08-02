package net.pufferlab.primal.world.terrafirma.gen.region;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.pufferlab.primal.utils.PosMap;
import net.pufferlab.primal.utils.WorldUtils;
import net.pufferlab.primal.world.scheduling.ChunkPlacerData;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;
import net.pufferlab.primal.world.terrafirma.gen.region.data.ChunkBlockData;
import net.pufferlab.primal.world.terrafirma.gen.region.data.ChunkNoiseData;

public class RegionProvider {

    public PosMap.Single<Region> regionMap = new PosMap.Single<>();

    public PosMap.Single<ChunkNoiseData> chunkNoiseMap = new PosMap.Single<>();
    public List<ChunkNoiseData> chunkNoiseList = new ArrayList<>();

    public PosMap.Single<ChunkBlockData> chunkBlockMap = new PosMap.Single<>();
    public List<ChunkBlockData> chunkBlockList = new ArrayList<>();

    public ConcurrentLinkedQueue<ChunkNoiseData> completedNoise = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<ChunkBlockData> completedBlock = new ConcurrentLinkedQueue<>();

    public synchronized void generateRegion(World world, int chunkX, int chunkZ) {
        int regionX = Region.getRegionCoord(chunkX);
        int regionZ = Region.getRegionCoord(chunkZ);

        Region region = regionMap.get(regionX, regionZ);
        if (region == null) {
            region = new Region(this, world, regionX, regionZ);
            regionMap.put(regionX, regionZ, region);
            region.generateRegionNoiseMaps();
        }
    }

    public void addNoiseData(ChunkNoiseData data) {
        completedNoise.add(data);
    }

    public void addBlockData(ChunkBlockData data) {
        completedBlock.add(data);
    }

    public void tickTasks(World world) {
        ChunkNoiseData noiseData;
        while ((noiseData = completedNoise.poll()) != null) {
            ChunkDataTF dataSaved = ChunkDataTF.get(world, noiseData.chunkX, noiseData.chunkZ);
            dataSaved.syncData(noiseData);
            provideNoiseData(noiseData);
        }

        ChunkBlockData blockData;
        while ((blockData = completedBlock.poll()) != null) {
            provideBlockData(blockData);
            placeChunkBlocks(world, blockData, blockData.chunkX, blockData.chunkZ);
        }
    }

    public void tickPendingBlocks(World world, int chunkX, int chunkZ) {
        ChunkPlacerData.tickPlacement(world, chunkX, chunkZ);
    }

    public void provideNoiseData(ChunkNoiseData data) {
        chunkNoiseMap.put(data.chunkX, data.chunkZ, data);
        chunkNoiseList.add(data);
    }

    public void provideBlockData(ChunkBlockData data) {
        chunkBlockMap.put(data.chunkX, data.chunkZ, data);
        chunkBlockList.add(data);
    }

    public ChunkBlockData getChunkBlockData(int chunkX, int chunkZ) {
        return chunkBlockMap.get(chunkX, chunkZ);
    }

    public void placeChunkBlocks(World world, ChunkBlockData data, int chunkX, int chunkZ) {
        IChunkProvider provider = world.getChunkProvider();

        if (provider.chunkExists(chunkX, chunkZ)) {
            Chunk chunk = provider.provideChunk(chunkX, chunkZ);

            WorldUtils.setBulkChunkBlock(chunk, data.blocks, data.metas);
        }
    }

}
