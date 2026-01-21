package net.pufferlab.primal.world.gen;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.SoilType;
import net.pufferlab.primal.utils.WorldUtils;

import cpw.mods.fml.common.registry.GameRegistry;

public class WorldGenSoil {

    private final List<Block> blockList = new ArrayList<>();
    private final List<Block> dirtList = new ArrayList<>();
    private final List<Block> grassList = new ArrayList<>();
    private final Map<Block, Block> blockReplacement = new HashMap<>();

    private final NoiseGeneratorPerlin[] noiseBiomeGen = new NoiseGeneratorPerlin[1];
    private final double[] noiseBiome = new double[1];
    private final int[] offsetB = new int[1];

    public World lastWorld;

    public void initNoiseSeed(World world) {
        for (int i = 0; i < noiseBiomeGen.length; i++) {
            this.noiseBiomeGen[i] = new NoiseGeneratorPerlin(new Random(world.getSeed() + (i * 100L)), 2);
        }
        lastWorld = world;
    }

    public void initBlockList() {
        if (Mods.bop.isLoaded()) {
            dirtList.add(GameRegistry.findBlock(Mods.bop.MODID, "newBopDirt"));
            dirtList.add(GameRegistry.findBlock(Mods.bop.MODID, "hardDirt"));
            grassList.add(GameRegistry.findBlock(Mods.bop.MODID, "newBopGrass"));
        }
        dirtList.add(Blocks.dirt);
        grassList.add(Blocks.grass);
        blockList.addAll(dirtList);
        blockList.addAll(grassList);
        for (Block block : dirtList) {
            blockReplacement.put(block, Registry.dirt);
        }
        for (Block block : grassList) {
            blockReplacement.put(block, Registry.grass);
        }
    }

    public void genSoil(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;
                for (int i = 0; i < noiseBiomeGen.length; i++) {
                    double noise1 = WorldUtils.getPerlin(noiseBiomeGen[i], worldX, worldZ, 0.002D);
                    noiseBiome[i] = noise1;
                    offsetB[i] = WorldUtils.getPerlinQuad(noiseBiome[i]);
                }

                for (int y = 0; y < Constants.maxHeight; y++) {
                    ExtendedBlockStorage array = chunk.getBlockStorageArray()[y >> 4];
                    if (array == null) continue;

                    Block currentBlock = WorldUtils.getChunkBlock(array, x, y, z);
                    if (currentBlock.getMaterial() == Material.air) continue;
                    if (blockList.contains(currentBlock)) {
                        Block nextBlock = blockReplacement.get(currentBlock);

                        SoilType type = SoilType.getDirtForBiome(WorldUtils.getBiome(chunk, x, z), x, y, z);
                        int meta = SoilType.getMeta(Constants.soilTypes, type);

                        WorldUtils.setChunkBlock(array, x, y, z, nextBlock, meta);
                    }
                }
            }
        }
        chunk.isModified = true;
    }

}
