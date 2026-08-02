package net.pufferlab.primal.world.terrafirma.gen.filler;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;
import net.pufferlab.primal.utils.VeinType;
import net.pufferlab.primal.utils.WorldUtils;
import net.pufferlab.primal.world.gen.WorldGenVein;
import net.pufferlab.primal.world.terrafirma.gen.region.data.ChunkBlockData;
import net.pufferlab.primal.world.terrafirma.gen.region.data.ChunkNoiseData;

public class VeinFiller extends WorldGenVein {

    public World world;

    public VeinFiller(World world) {
        this.world = world;
    }

    public void genVein(ChunkBlockData data, ChunkNoiseData dataNoise, int chunkX, int chunkZ) {
        for (VeinType vein : Constants.veinTypesAll) {
            if (vein.oreType.oreBlock != null) {
                int x = data.random.nextInt(16);
                int z = data.random.nextInt(16);
                int worldX = (chunkX << 4) + x;
                int worldZ = (chunkZ << 4) + z;
                int maxY = dataNoise.getHeight(x, z);
                int y = vein.getHeight(world, data.random);
                if (vein.getChance(data.random)) {
                    Block block = data.getBlock(x, y, z);
                    int meta = data.getBlockMetadata(x, y, z);
                    StoneType type = StoneType.getStoneType(block, meta);
                    if (type != null) {
                        if (vein.isValidStone(type)) {
                            int deep = Math.abs(maxY - y);
                            int oreMeta = vein.oreType.oreMeta;
                            if (vein.getChanceIndicator(data.random) && deep < 15) {
                                genGroundcover(world, data.random, dataNoise, data, Registry.ground_ore, oreMeta);
                            }
                            genVein(world, data.random, worldX, y, worldZ, vein, meta);
                        }
                    }
                }
            }
        }
    }

    public static void genVein(World world, Random rand, int cx, int cy, int cz, VeinType vein, int stoneMeta) {
        int ox = vein.getSize(rand);
        int oy = vein.getSize(rand);
        int oz = vein.getSize(rand);

        Block block = vein.oreType.oreBlock;

        for (int x = cx - ox; x <= cx + ox; x++) {
            for (int y = cy - oy; y <= cy + oy; y++) {
                for (int z = cz - oz; z <= cz + oz; z++) {

                    double dx = (x - cx) / (double) ox;
                    double dy = (y - cy) / (double) oy;
                    double dz = (z - cz) / (double) oz;

                    if (vein.getChanceBlock(rand)) {
                        if (dx * dx + dy * dy + dz * dz <= 1.0F) {
                            WorldUtils.setBlockWorldgen(world, x, y, z, block, stoneMeta);
                        }
                    }
                }
            }
        }
    }

    public static void genGroundcover(World world, Random rand, ChunkNoiseData data, ChunkBlockData blockData,
        Block groundcover, int oreMeta) {
        for (int l = 0; l < 3; ++l) {
            int x = rand.nextInt(16);
            int z = rand.nextInt(16);
            int worldX = data.chunkX + x;
            int worldZ = data.chunkZ + z;
            int y = data.getHeight(x, z);

            if (blockData.getBlock(x, y, z) == Blocks.air) {
                WorldUtils.setBlockWorldgen(world, worldX, y, worldZ, groundcover, oreMeta);
            }
        }
    }
}
