package net.pufferlab.primal.world.terrafirma.gen.filler;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;
import net.pufferlab.primal.utils.WorldUtils;
import net.pufferlab.primal.world.terrafirma.ChunkBlockData;
import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;

public class StrataLayers implements IBlockLayer {

    public World world;
    public long seed;

    private final NoiseGeneratorPerlin[] noiseLayerGen = new NoiseGeneratorPerlin[5];
    private final NoiseGeneratorPerlin[] noiseBiomeGen = new NoiseGeneratorPerlin[5];

    public StrataLayers(World world) {
        this.world = world;
        this.seed = world.getSeed();
        for (int i = 0; i < noiseLayerGen.length; i++) {
            noiseLayerGen[i] = new NoiseGeneratorPerlin(new Random(seed + (i * 200L)), 2);
        }
        for (int i = 0; i < noiseBiomeGen.length; i++) {
            noiseBiomeGen[i] = new NoiseGeneratorPerlin(new Random(seed + (i * 100L)), 2);
        }
    }

    @Override
    public void generate(ChunkBlockData data, ChunkNoiseData dataNoise, int chunkX, int chunkZ) {
        double[] noiseLayer = new double[5];
        int[] offsetY = new int[5];

        double[] noiseBiome = new double[5];
        int[] offsetB = new int[5];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunkX << 4) + x;
                int worldZ = (chunkZ << 4) + z;
                for (int i = 0; i < noiseLayerGen.length; i++) {
                    noiseLayer[i] = WorldUtils.getPerlin(noiseLayerGen[i], worldX, worldZ, 0.016D);
                    offsetY[i] = (int) (noiseLayer[i] * 4);
                }
                for (int i = 0; i < noiseBiomeGen.length; i++) {
                    double noise1 = WorldUtils.getPerlin(noiseBiomeGen[i], worldX, worldZ, 0.0004D);
                    noiseBiome[i] = noise1;
                }

                for (int y = Constants.minHeight; y <= Constants.maxHeight; y++) {
                    for (int i = 0; i < noiseBiomeGen.length; i++) {
                        double div = (double) y / 200;
                        offsetB[i] = WorldUtils.getPerlinValue(noiseBiome[i] + div, 10);
                    }
                    double adjustedY = y;

                    Block currentBlock = data.getBlock(x, y, z);
                    Block nextBlock = Blocks.air;
                    if (currentBlock == Blocks.stone) {
                        nextBlock = Registry.stone;
                    }
                    if (currentBlock == Blocks.gravel) {
                        nextBlock = Registry.gravel;
                    }
                    if (currentBlock == Blocks.sand) {
                        nextBlock = Registry.sand;
                    }
                    if (nextBlock != Blocks.air) {
                        int layerMeta = 0;
                        if ((adjustedY + offsetY[0]) < 20 * Constants.heightMultiplier) {
                            layerMeta = 0;
                        } else if ((adjustedY + offsetY[1]) < 40 * Constants.heightMultiplier) {
                            layerMeta = 1;
                        } else if ((adjustedY - offsetY[2]) < 60 * Constants.heightMultiplier) {
                            layerMeta = 2;
                        } else if ((adjustedY + offsetY[3]) < 90 * Constants.heightMultiplier) {
                            layerMeta = 3;
                        } else if ((adjustedY - offsetY[4]) < 110 * Constants.heightMultiplier) {
                            layerMeta = 4;
                        }
                        StoneType type = StoneType
                            .pickOneStoneType(world, (int) (adjustedY + offsetY[layerMeta]), offsetB[layerMeta]);

                        int meta = StoneType.getMeta(type);

                        data.setBlock(x, y, z, nextBlock, meta);
                    }
                }
            }
        }
    }
}
