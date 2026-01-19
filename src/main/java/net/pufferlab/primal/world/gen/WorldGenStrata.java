package net.pufferlab.primal.world.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;
import net.pufferlab.primal.utils.WorldUtils;

import cpw.mods.fml.common.registry.GameRegistry;

public class WorldGenStrata {

    private final List<Block> blockList = new ArrayList<>();
    private final List<Block> stoneList = new ArrayList<>();
    private final List<Block> gravelList = new ArrayList<>();
    private final List<Block> dirtList = new ArrayList<>();
    private final List<Block> grassList = new ArrayList<>();

    private final NoiseGeneratorPerlin[] noiseLayerGen = new NoiseGeneratorPerlin[5];
    private final double[] noiseLayer = new double[5];
    private final int[] offsetY = new int[5];

    private final NoiseGeneratorPerlin[] noiseBiomeGen = new NoiseGeneratorPerlin[5];
    private final double[] noiseBiome = new double[5];
    private final int[] offsetB = new int[5];

    public WorldGenStrata() {
        for (int i = 0; i < noiseLayerGen.length; i++) {
            this.noiseLayerGen[i] = new NoiseGeneratorPerlin(new Random(), 2);
        }
        for (int i = 0; i < noiseBiomeGen.length; i++) {
            this.noiseBiomeGen[i] = new NoiseGeneratorPerlin(new Random(), 2);
        }

    }

    public void initBlockList() {
        stoneList.add(Blocks.stone);
        gravelList.add(Blocks.gravel);
        if (Mods.efr.isLoaded()) {
            stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "stone"));
            stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "deepslate"));
            stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "tuff"));
        }
        dirtList.add(Blocks.dirt);
        grassList.add(Blocks.grass);

        blockList.add(Registry.ground_rock);

        blockList.addAll(stoneList);
        blockList.addAll(gravelList);
        if (Config.strataSoilTypes.getBoolean()) {
            blockList.addAll(dirtList);
            blockList.addAll(grassList);
        }
    }

    public void genStrata(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;
                for (int i = 0; i < noiseLayerGen.length; i++) {
                    noiseLayer[i] = WorldUtils.getPerlin(noiseLayerGen[i], worldX, worldZ, 0.03D);
                    offsetY[i] = (int) (noiseLayer[i] * 6);
                }
                for (int i = 0; i < noiseBiomeGen.length; i++) {
                    noiseBiome[i] = WorldUtils.getPerlin(noiseBiomeGen[i], worldX, worldZ, 0.003D);
                }

                for (int y = 0; y < 128; y++) {
                    ExtendedBlockStorage array = chunk.getBlockStorageArray()[y >> 4];
                    if (array == null) continue;
                    for (int i = 0; i < noiseBiomeGen.length; i++) {
                        double div = (double) y / 200;
                        offsetB[i] = WorldUtils.getPerlinQuad(noiseBiome[i] + div);
                    }
                    double adjustedY = y;

                    Block currentBlock = WorldUtils.getChunkBlock(array, x, y, z);
                    if (blockList.contains(currentBlock)) {
                        Block nextBlock = getCorrectBlock(currentBlock);
                        int layerMeta = 0;
                        if ((adjustedY + offsetY[0]) < 20) {
                            layerMeta = 0;
                        } else if ((adjustedY + offsetY[1]) < 40) {
                            layerMeta = 1;
                        } else if ((adjustedY - offsetY[2]) < 60) {
                            layerMeta = 2;
                        } else if ((adjustedY + offsetY[3]) < 100) {
                            layerMeta = 3;
                        } else if ((adjustedY - offsetY[4]) < 120) {
                            layerMeta = 4;
                        }
                        StoneType type = StoneType.pickOneStoneType(
                            Constants.stoneTypes,
                            (int) (adjustedY + offsetY[layerMeta]),
                            offsetB[layerMeta]);
                        int meta = StoneType.getMeta(Constants.stoneTypes, type);

                        WorldUtils.setChunkBlock(array, x, y, z, nextBlock, meta);
                    }
                }
            }
        }
        chunk.isModified = true;
    }

    public Block getCorrectBlock(Block currentBlock) {
        boolean isGravel = gravelList.contains(currentBlock);
        boolean isDirt = dirtList.contains(currentBlock);
        boolean isGrass = grassList.contains(currentBlock);
        Block nextBlock = Registry.stone;
        if (isGravel) {
            nextBlock = Registry.gravel;
        }
        if (isDirt) {
            nextBlock = Registry.dirt;
        }
        if (isGrass) {
            nextBlock = Registry.grass;
        }
        if (currentBlock == Registry.ground_rock) {
            return Registry.ground_rock;
        }
        return nextBlock;
    }
}
