package net.pufferlab.primal.world.terrafirma.gen.region;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.Mth;
import net.pufferlab.primal.utils.PosMap;
import net.pufferlab.primal.world.scheduling.ChunkPlacerData;
import net.pufferlab.primal.world.terrafirma.ChunkBlockData;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;
import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;

public class RegionProvider {

    public World world;
    public RegionGenerator generator;

    public PosMap.Single<Region> regionMap = new PosMap.Single<>();

    public PosMap.Single<ChunkBlockData> chunkBlockMap = new PosMap.Single<>();

    public ConcurrentLinkedQueue<ChunkNoiseData> completedNoise = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<ChunkBlockData> completedBlock = new ConcurrentLinkedQueue<>();

    public ExecutorService executor;

    private static final long cleaningTimeTICK = 20 * 10;
    private static final long cleaningBlockTimeMS = 60_000;
    public static List<RegionProvider> providerList = new ArrayList<>();
    public static boolean limitedExecutor = false;

    public RegionProvider(World world) {
        this.world = world;
        providerList.add(this);
        this.generator = new RegionGenerator(world, this);
    }

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
            if (limitedExecutor) {
                executor = Executors.newFixedThreadPool(getThreadAmount());
            } else {
                executor = Executors.newCachedThreadPool();
            }
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

    public static int cleanupTicks;

    public static void cleanAllTasks() {
        cleanupTicks++;

        if (cleanupTicks > cleaningTimeTICK) {
            for (int i = 0; i < providerList.size(); i++) {
                RegionProvider provider = providerList.get(i);
                provider.cleanupPendingBlocks();
                cleanupTicks = 0;
            }
        }
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

            data.placeToChunk(chunk);
        } else {
            data.createdTime = System.currentTimeMillis();
            chunkBlockMap.put(data.chunkX, data.chunkZ, data);
        }
    }

    public void cleanupPendingBlocks() {
        long now = System.currentTimeMillis();
        chunkBlockMap.removeIf(data -> { return (now - data.createdTime) > cleaningBlockTimeMS; });

        regionMap.removeIf(data -> { return data.hasGenerated; });
    }

}
