package net.pufferlab.primal.world.gen;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;
import net.pufferlab.primal.utils.WorldUtils;

import biomesoplenty.api.content.BOPCBiomes;
import cpw.mods.fml.common.registry.GameRegistry;

public class WorldGenStrata {

    private final List<Block> blockList = new ArrayList<>();
    private final List<Block> stoneList = new ArrayList<>();
    private final List<Block> gravelList = new ArrayList<>();
    private final List<Block> sandList = new ArrayList<>();
    private final Map<Block, Block> blockReplacement = new HashMap<>();

    private final Map<Block, List<BiomeGenBase>> stoneTypeBiomeMap = new HashMap<>();
    private final Map<Block, StoneType> stoneTypeMap = new HashMap<>();

    private final NoiseGeneratorPerlin[] noiseLayerGen = new NoiseGeneratorPerlin[5];
    private final double[] noiseLayer = new double[5];
    private final int[] offsetY = new int[5];

    private final NoiseGeneratorPerlin[] noiseBiomeGen = new NoiseGeneratorPerlin[5];
    private final double[] noiseBiome = new double[5];
    private final int[] offsetB = new int[5];

    public WorldGenStrata() {}

    public World lastWorld;

    public void initNoiseSeed(World world) {
        for (int i = 0; i < noiseLayerGen.length; i++) {
            this.noiseLayerGen[i] = new NoiseGeneratorPerlin(new Random(world.getSeed() + (i * 200L)), 2);
        }
        for (int i = 0; i < noiseBiomeGen.length; i++) {
            this.noiseBiomeGen[i] = new NoiseGeneratorPerlin(new Random(world.getSeed() + (i * 100L)), 2);
        }
        lastWorld = world;
    }

    public void initBlockList() {
        stoneList.add(Blocks.stone);
        if (!Config.enableVanillaOres.getBoolean()) {
            stoneList.add(Blocks.coal_ore);
            stoneList.add(Blocks.iron_ore);
            stoneList.add(Blocks.gold_ore);
            stoneList.add(Blocks.redstone_ore);
            stoneList.add(Blocks.lapis_ore);
            stoneList.add(Blocks.diamond_ore);
            stoneList.add(Blocks.emerald_ore);
            if (Mods.efr.isLoaded()) {
                stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "deepslate_coal_ore"));
                stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "deepslate_iron_ore"));
                stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "deepslate_gold_ore"));
                stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "deepslate_redstone_ore"));
                stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "deepslate_lapis_ore"));
                stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "deepslate_diamond_ore"));
                stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "deepslate_emerald_ore"));
            }
        }
        gravelList.add(Blocks.gravel);
        if (Mods.efr.isLoaded()) {
            stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "stone"));
            stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "deepslate"));
            stoneList.add(GameRegistry.findBlock(Mods.efr.MODID, "tuff"));
        }
        if (Mods.bop.isLoaded()) {
            Block volcanicStone = GameRegistry.findBlock(Mods.bop.MODID, "ashStone");
            Block rocks = GameRegistry.findBlock(Mods.bop.MODID, "rocks");
            stoneList.add(volcanicStone);
            stoneList.add(rocks);

            // Volcano are with basalt
            BiomeGenBase biomeVolcano = BOPCBiomes.volcano;
            stoneTypeBiomeMap.put(volcanicStone, Collections.singletonList(biomeVolcano));
            stoneTypeMap.put(volcanicStone, Constants.basalt);
        }
        // Desert Sandstone
        BiomeGenBase[] desert = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills };
        Block sandstone = Blocks.sandstone;
        Block sand = Blocks.sand;
        stoneList.add(sandstone);
        sandList.add(sand);
        stoneTypeBiomeMap.put(sandstone, Arrays.asList(desert));
        stoneTypeMap.put(sandstone, Constants.sandstone);
        stoneTypeBiomeMap.put(sand, Arrays.asList(desert));
        stoneTypeMap.put(sand, Constants.sandstone);

        blockList.add(Registry.ground_rock);
        blockReplacement.put(Registry.ground_rock, Registry.ground_rock);

        for (Block block : stoneList) {
            blockReplacement.put(block, Registry.stone);
        }
        for (Block block : sandList) {
            blockReplacement.put(block, Registry.sand);
        }
        for (Block block : gravelList) {
            blockReplacement.put(block, Registry.gravel);
        }

        blockList.addAll(stoneList);
        blockList.addAll(gravelList);
        blockList.addAll(sandList);
    }

    public void genStrata(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunk.xPosition << 4) + x;
                int worldZ = (chunk.zPosition << 4) + z;
                for (int i = 0; i < noiseLayerGen.length; i++) {
                    noiseLayer[i] = WorldUtils.getPerlin(noiseLayerGen[i], worldX, worldZ, 0.03D);
                    offsetY[i] = (int) (noiseLayer[i] * 4);
                }
                for (int i = 0; i < noiseBiomeGen.length; i++) {
                    double noise1 = WorldUtils.getPerlin(noiseBiomeGen[i], worldX, worldZ, 0.002D);
                    noiseBiome[i] = noise1;
                }

                for (int y = 0; y < Constants.maxHeight; y++) {
                    ExtendedBlockStorage array = chunk.getBlockStorageArray()[y >> 4];
                    if (array == null) continue;
                    for (int i = 0; i < noiseBiomeGen.length; i++) {
                        double div = (double) y / 200;
                        offsetB[i] = WorldUtils.getPerlinQuad(noiseBiome[i] + div);
                    }
                    double adjustedY = y;

                    Block currentBlock = WorldUtils.getChunkBlock(array, x, y, z);
                    if (currentBlock.getMaterial() == Material.air) continue;
                    if (blockList.contains(currentBlock)) {
                        Block nextBlock = blockReplacement.get(currentBlock);
                        int layerMeta = 0;
                        if ((adjustedY + offsetY[0]) < 20) {
                            layerMeta = 0;
                        } else if ((adjustedY + offsetY[1]) < 40) {
                            layerMeta = 1;
                        } else if ((adjustedY - offsetY[2]) < 60) {
                            layerMeta = 2;
                        } else if ((adjustedY + offsetY[3]) < 90) {
                            layerMeta = 3;
                        } else if ((adjustedY - offsetY[4]) < 110) {
                            layerMeta = 4;
                        }
                        StoneType type = StoneType
                            .pickOneStoneType((int) (adjustedY + offsetY[layerMeta]), offsetB[layerMeta]);
                        // Biome Specific
                        if (Config.strataBiomeSpecific.getBoolean()) {
                            if (layerMeta == 3 || layerMeta == 4) {
                                if (stoneTypeMap.containsKey(currentBlock)) {
                                    if (stoneTypeBiomeMap.containsKey(currentBlock)) {
                                        List<BiomeGenBase> neededBiome = stoneTypeBiomeMap.get(currentBlock);
                                        BiomeGenBase biome = WorldUtils.getBiome(chunk, x, z);
                                        if (neededBiome.contains(biome)) {
                                            type = stoneTypeMap.get(currentBlock);
                                        }
                                    } else {
                                        type = stoneTypeMap.get(currentBlock);
                                    }
                                }
                            }
                        }
                        int meta = StoneType.getMeta(Constants.stoneTypes, type);

                        WorldUtils.setChunkBlock(array, x, y, z, nextBlock, meta);
                    }
                }
            }
        }
    }
}
