package net.pufferlab.primal.world.terrafirma.gen.region;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.Mth;
import net.pufferlab.primal.utils.PosMap;
import net.pufferlab.primal.utils.WorldUtils;
import net.pufferlab.primal.world.scheduling.ChunkPlacerData;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;
import net.pufferlab.primal.world.terrafirma.gen.region.data.ChunkBlockData;
import net.pufferlab.primal.world.terrafirma.gen.region.data.ChunkNoiseData;

public class RegionProvider {

    public PosMap.Single<Region> regionMap = new PosMap.Single<>();

    public PosMap.Single<ChunkBlockData> chunkBlockMap = new PosMap.Single<>();

    public ConcurrentLinkedQueue<ChunkNoiseData> completedNoise = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<ChunkBlockData> completedBlock = new ConcurrentLinkedQueue<>();

    public ExecutorService executor;

    public static int getThreadAmount() {
        int threadAmount = Runtime.getRuntime()
            .availableProcessors();
        if (Config.useAllThreadsTF.getBoolean()) {
            return threadAmount;
        }
        return Mth.clamp(threadAmount / 2, 1, 8);
    }

    public synchronized void generateRegion(World world, int chunkX, int chunkZ) {
        int regionX = Region.getRegionCoord(chunkX);
        int regionZ = Region.getRegionCoord(chunkZ);
        if (executor == null) {
            executor = Executors.newFixedThreadPool(getThreadAmount());
        }

        Region region = regionMap.get(regionX, regionZ);
        if (region == null) {
            region = new Region(this, world, regionX, regionZ);
            regionMap.put(regionX, regionZ, region);
            region.generateAsync(executor);
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
        }

        ChunkBlockData blockData;
        while ((blockData = completedBlock.poll()) != null) {
            placeChunkBlocks(world, blockData, blockData.chunkX, blockData.chunkZ);
        }
    }

    public void tickPendingBlocks(World world, int chunkX, int chunkZ) {
        ChunkPlacerData.tickPlacement(world, chunkX, chunkZ);
    }

    public ChunkBlockData getChunkBlockData(int chunkX, int chunkZ) {
        return chunkBlockMap.remove(chunkX, chunkZ);
    }

    public void placeChunkBlocks(World world, ChunkBlockData data, int chunkX, int chunkZ) {
        IChunkProvider provider = world.getChunkProvider();

        if (provider.chunkExists(chunkX, chunkZ)) {
            Chunk chunk = provider.provideChunk(chunkX, chunkZ);

            WorldUtils.setBulkChunkBlock(chunk, data.blocks, data.metas);
        } else {
            chunkBlockMap.put(data.chunkX, data.chunkZ, data);
        }
    }

}
