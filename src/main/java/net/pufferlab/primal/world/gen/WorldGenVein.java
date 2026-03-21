package net.pufferlab.primal.world.gen;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;
import net.pufferlab.primal.utils.VeinType;
import net.pufferlab.primal.world.gen.feature.WorldGenGroundOre;

public class WorldGenVein {

    public static final WorldGenGroundOre genIndicator = new WorldGenGroundOre(Registry.ground_ore);
    public World lastWorld;

    public void initNoiseSeed(World world) {
        Primal.registry.setupServer();
        lastWorld = world;
    }

    public void genVein(World world, Random rand, Chunk chunk, int chunkX, int chunkZ) {
        for(VeinType vein : Constants.veinTypesAll) {
            if(vein.oreType.oreBlock != null) {
                int x = (chunkX << 4) + rand.nextInt(16) + 8;
                int z = (chunkZ << 4) + rand.nextInt(16) + 8;
                int maxY = world.getTopSolidOrLiquidBlock(x, z);
                int y = vein.getHeight(rand);
                if(vein.getChance(rand)) {
                    Block block = world.getBlock(x, y, z);
                    int meta = world.getBlockMetadata(x, y, z);
                    StoneType type = StoneType.getStoneType(block, meta);
                    if (type != null) {
                        if (vein.isValidStone(type)) {
                            int deep = Math.abs(maxY - y);
                            if (vein.getChanceIndicator(rand) && deep < 15) {
                                genIndicator.generate(world, rand, x, y, z);
                            }
                            VeinMath.ovalImperfect(world, rand, x, y, z, vein);
                        }
                    }
                }
            }
        }
    }

}
