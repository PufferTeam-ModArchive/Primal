package net.pufferlab.primal.world.terrafirma.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;
import net.pufferlab.primal.utils.VeinType;
import net.pufferlab.primal.world.gen.VeinMath;
import net.pufferlab.primal.world.gen.WorldGenVein;
import net.pufferlab.primal.world.terrafirma.gen.feature.WorldGenGroundOreTF;

public class WorldGenVeinTF extends WorldGenVein {

    public static final WorldGenGroundOreTF genIndicator = new WorldGenGroundOreTF();
    public long lastSeed;

    public void initNoiseSeed(long seed) {
        if (lastSeed != seed) {
            lastSeed = seed;
            Primal.registry.setupServer();
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
                            int oreMeta = vein.oreType.oreMeta;
                            if (vein.getChanceIndicator(rand) && deep < 15) {
                                genIndicator.generate(world, rand, x, y, z, Registry.ground_ore, oreMeta);
                            }
                            VeinMath.ovalImperfect(world, rand, x, y, z, vein);
                        }
                    }
                }
            }
        }
    }

}
