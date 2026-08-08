package net.pufferlab.primal.world.terrafirma.gen.filler;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.Mth;
import net.pufferlab.primal.utils.SoilType;
import net.pufferlab.primal.world.terrafirma.BiomesTF;
import net.pufferlab.primal.world.terrafirma.ChunkBlockData;
import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;

public class SoilFiller {

    public World world;
    public long seed;

    public SoilFiller(World world) {
        this.world = world;
        this.seed = world.getSeed();
    }

    public void genSoil(ChunkBlockData data, ChunkNoiseData dataNoise, int chunkX, int chunkZ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunkX << 4) + x;
                int worldZ = (chunkZ << 4) + z;

                float detail = dataNoise.getDetail(x, z);

                float temperature = dataNoise.getTemperature(x, z) + (detail * 0.02F);
                float rainfall = dataNoise.getRainfall(x, z) + (detail * 0.02F);
                float vegetation = dataNoise.getVegetation(x, z) + (detail * 0.02F);

                int topY = dataNoise.getHeight(x, z);

                float elevationValue = dataNoise.getRockiness(x, z) + (detail * 0.02F);

                int depthBlocks = Mth.floor(((1 - elevationValue)) * 5.0F);

                Block block = Registry.dirt;
                Block blockTop = null;
                for (int i = 0; i < depthBlocks; i++) {
                    int y = topY - 1 - i;

                    if (i == 0) {
                        block = Registry.grass;
                    }
                    if (vegetation < 0.3F) {
                        block = Registry.dirt;
                    }
                    if (rainfall < 0.3F && temperature > 0.6F) {
                        block = Blocks.sand;
                    }
                    if (rainfall < 0.3F && temperature < 0.4F) {
                        blockTop = Blocks.snow_layer;
                    }
                    if (elevationValue > 0.65F) {
                        block = Blocks.gravel;
                    }

                    if (dataNoise.getBiome(x, z) == BiomesTF.ocean) {
                        block = Blocks.gravel;
                        blockTop = null;
                    }

                    Block blockReplacing = data.getBlock(x, y, z);
                    if (BlockUtils.isNaturalStone(blockReplacing)) {
                        SoilType soil = SoilType.pickOneSoilType(world, rainfall);
                        int meta = SoilType.getMeta(soil);

                        if (BlockUtils.isGrassBlock(data.getBlock(x, y + 1, z))) {
                            block = Registry.dirt;
                        }
                        data.setBlock(x, y, z, block, meta);
                    }
                }
                if (blockTop != null) {
                    data.setBlock(x, topY, z, blockTop, 0);
                }
            }
        }
    }
}
