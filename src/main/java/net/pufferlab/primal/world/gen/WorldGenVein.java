package net.pufferlab.primal.world.gen;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;
import net.pufferlab.primal.utils.VeinType;
import net.pufferlab.primal.world.gen.feature.WorldGenGroundOre;

public class WorldGenVein {

    public static final WorldGenGroundOre genIndicator = new WorldGenGroundOre(Registry.ground_ore);
    public long lastSeed;

    public void initNoiseSeed(long seed) {
        if (lastSeed != seed) {
            lastSeed = seed;
        }
    }

    public void genVein(Chunk chunk, Random rand) {
        for (VeinType vein : Constants.veinTypesAll) {
            if (vein.oreType.oreBlock != null) {
                World world = chunk.worldObj;
                int x = (chunk.xPosition << 4) + rand.nextInt(16) + 8;
                int z = (chunk.zPosition << 4) + rand.nextInt(16) + 8;
                int maxY = world.getTopSolidOrLiquidBlock(x, z);
                int y = vein.getHeight(world, rand);
                if (vein.getChance(rand)) {
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
