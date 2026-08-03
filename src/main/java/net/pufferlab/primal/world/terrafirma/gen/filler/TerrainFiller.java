package net.pufferlab.primal.world.terrafirma.gen.filler;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.world.terrafirma.ChunkBlockData;
import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;

public class TerrainFiller {

    public TerrainFiller(World world) {

    }

    public void genTerrain(ChunkBlockData data, ChunkNoiseData dataNoise, int chunkX, int chunkZ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

                float height = dataNoise.getHeight(x, z);

                for (int y = Constants.minHeight; y <= Constants.maxHeight; y++) {
                    if (y == Constants.minHeight) {
                        data.setBlock(x, y, z, Blocks.bedrock, 0);
                    } else {
                        if (y < height) {
                            data.setBlock(x, y, z, Blocks.stone, 0);
                        } else {
                            if (y < Config.seaLevelTF.getInt()) {
                                data.setBlock(x, y, z, Blocks.water, 0);
                            }
                        }
                    }
                }
            }
        }
    }
}
