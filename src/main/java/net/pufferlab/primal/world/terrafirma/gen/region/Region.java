package net.pufferlab.primal.world.terrafirma.gen.region;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import net.minecraft.world.World;
import net.pufferlab.primal.world.terrafirma.ChunkBlockData;
import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;

public class Region {

    public World world;
    public static int regionSize = 4;

    // Minimum chunk coordinate
    public int minX;
    public int minZ;
    // Maximum chunk coordinate
    public int maxX;
    public int maxZ;

    public RegionProvider provider;
    public RegionGenerator regionGenerator;

    public Region(RegionProvider provider, World world, int regionX, int regionZ) {
        this.provider = provider;
        this.world = world;

        this.minX = regionX * regionSize;
        this.minZ = regionZ * regionSize;

        this.maxX = this.minX + regionSize - 1;
        this.maxZ = this.minZ + regionSize - 1;
        this.regionGenerator = new RegionGenerator(world, this, provider);
    }

    public static int getRegionCoord(int chunkX) {
        return Math.floorDiv(chunkX, Region.regionSize);
    }

    public static int getRegionMaxCoord(int chunkX) {
        int regionCoord = getRegionCoord(chunkX);
        return (regionCoord * Region.regionSize) + (Region.regionSize - 1);
    }

    public void generateAsync(ExecutorService executor) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        ConcurrentLinkedQueue<ChunkBlockData> noise = new ConcurrentLinkedQueue<>();
        for (int cx = minX; cx <= maxX; cx++) {
            for (int cz = minZ; cz <= maxZ; cz++) {
                final int x = cx;
                final int z = cz;

                futures.add(
                    CompletableFuture.supplyAsync(() -> generateChunk(x, z), executor)
                        .thenApply(data -> {
                            provider.addNoiseData(data);
                            return data;
                        })
                        .thenApply(data -> generateBlockChunk(data, data.chunkX, data.chunkZ))
                        .thenAccept(data -> {
                            provider.addBlockData(data);
                            noise.add(data);
                        }));
            }
        }

        // Wait for all of the tasks to be done
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .join();

        for (ChunkBlockData chunkBlockData : noise) {
            provider.chunkBlockMap.remove(chunkBlockData.chunkX, chunkBlockData.chunkZ);
        }
        provider.tickTasks(world);
    }

    public ChunkNoiseData generateChunk(int x, int z) {
        ChunkNoiseData noiseData = new ChunkNoiseData(x, z);
        regionGenerator.genNoise(noiseData, x, z);

        return noiseData;
    }

    public ChunkBlockData generateBlockChunk(ChunkNoiseData noiseData, int x, int z) {
        ChunkBlockData blockData = new ChunkBlockData(world.getSeed(), x, z);

        regionGenerator.genBlocks(blockData, noiseData, x, z);
        return blockData;
    }
}
