package net.pufferlab.primal.world.terrafirma;

import java.util.Random;

import net.pufferlab.primal.utils.HashUtils;
import net.pufferlab.primal.world.ChunkBlockStorage;

public class ChunkBlockData extends ChunkBlockStorage {

    public Random random = new Random();
    public int chunkX;
    public int chunkZ;
    public long createdTime;

    public ChunkBlockData(long seed, int chunkX, int chunkZ) {
        this.random.setSeed(HashUtils.packChunkCoord(seed, chunkX, chunkZ));
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

}
