package net.pufferlab.primal.world.terrafirma.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.WorldUtils;
import net.pufferlab.primal.world.structures.StructureFile;
import net.pufferlab.primal.world.terrafirma.BiomesTF;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;

public class WorldGenVegetationTF {

    public WorldGenVegetationTF() {

    }

    public long lastSeed;

    public void initNoiseSeed(long seed) {
        if (lastSeed != seed) {
            lastSeed = seed;
        }
    }

    public void genVegetation(Chunk chunk, Random rand) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;

                ChunkDataTF data = ChunkDataTF.get(chunk);
                int y = data.getHeight(x, z);

                BiomesTF biome = data.getBiome(x, z);
                if (biome != BiomesTF.ocean) {
                    float vegetation = data.getVegetation(x, z);

                    Block blockBelow = WorldUtils.getChunkBlock(chunk, x, y - 1, z);
                    if (BlockUtils.isGrassBlock(blockBelow)) {
                        if (vegetation > 0.3F) {
                            if (rand.nextFloat() > 0.5F) {
                                WorldUtils.setChunkBlock(chunk, x, y, z, Blocks.tallgrass, 1);
                            }
                        }
                        if (vegetation > 0.6F) {
                            // Only try 4 times
                            if (x % 4 == 0 && z % 4 == 0) {
                                if (rand.nextFloat() > vegetation) {
                                    int num = rand.nextInt(2) + 1;
                                    //This is really temporary, it just spawns oak tree
                                    StructureFile.loadStructure(
                                        "oak_tree_" + num,
                                        worldX,
                                        y,
                                        worldZ,
                                        chunk.worldObj,
                                        rand.nextInt(4),
                                        StructureFile.LoadingPosition.ground);
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}
