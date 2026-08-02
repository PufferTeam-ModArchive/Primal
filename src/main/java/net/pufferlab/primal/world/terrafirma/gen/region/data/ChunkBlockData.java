package net.pufferlab.primal.world.terrafirma.gen.region.data;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.pufferlab.primal.utils.HashUtils;

public class ChunkBlockData {

    public Random random = new Random();
    public final Block[] blocks = new Block[65536];
    public final byte[] metas = new byte[65536];
    public int chunkX;
    public int chunkZ;

    public ChunkBlockData(long seed, int chunkX, int chunkZ) {
        this.random.setSeed(HashUtils.packChunkCoord(seed, chunkX, chunkZ));
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public void setBlock(int x, int y, int z, Block block, int meta) {
        int index = HashUtils.packChunkBlockCoord(x, y, z);

        blocks[index] = block;
        metas[index] = (byte) meta;
    }

    public Block getBlock(int x, int y, int z) {
        int index = HashUtils.packChunkBlockCoord(x, y, z);
        Block block = blocks[index];
        if (block == null) {
            block = Blocks.air;
        }
        return block;
    }

    public int getBlockMetadata(int x, int y, int z) {
        int index = HashUtils.packChunkBlockCoord(x, y, z);
        return metas[index];
    }
}
