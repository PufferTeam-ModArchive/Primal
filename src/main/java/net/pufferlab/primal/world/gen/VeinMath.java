package net.pufferlab.primal.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.blocks.BlockStoneRaw;
import net.pufferlab.primal.utils.VeinType;

public class VeinMath {

    public static void sphereImperfect(World world, int cx, int cy, int cz, Block block, int r) {
        ovalImperfect(world, cx, cy, cz, block, r, r, r);
    }

    public static void ovalImperfect(World world, int cx, int cy, int cz, Block block, int ox, int oy, int oz) {
        for (int x = cx - ox; x <= cx + ox; x++) {
            for (int y = cy - oy; y <= cy + oy; y++) {
                for (int z = cz - oz; z <= cz + oz; z++) {

                    double dx = (x - cx) / (double) ox;
                    double dy = (y - cy) / (double) oy;
                    double dz = (z - cz) / (double) oz;

                    if (world.rand.nextInt(3) == 0) {
                        if (dx * dx + dy * dy + dz * dz <= 1.0F) {
                            int meta = 0;
                            Block block2 = world.getBlock(x, y, z);
                            if (block2 == Registry.stone || block2 == Registry.gravel) {
                                meta = world.getBlockMetadata(x, y, z);
                                world.setBlock(x, y, z, block, meta, 2);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void ovalImperfect(World world, Random rand, int cx, int cy, int cz, VeinType vein) {
        int ox = Math.min(vein.getSize(rand), 8);
        int oy = Math.min(vein.getSize(rand), 8);
        int oz = Math.min(vein.getSize(rand), 8);

        Block block = vein.oreType.oreBlock;

        for (int x = cx - ox; x <= cx + ox; x++) {
            for (int y = cy - oy; y <= cy + oy; y++) {
                for (int z = cz - oz; z <= cz + oz; z++) {

                    if (Math.abs(x - cx) > 8 || Math.abs(y - cy) > 8 || Math.abs(z - cz) > 8) continue;

                    double dx = (x - cx) / (double) ox;
                    double dy = (y - cy) / (double) oy;
                    double dz = (z - cz) / (double) oz;

                    if (vein.getChanceBlock(rand)) {
                        if (dx * dx + dy * dy + dz * dz <= 1.0F) {
                            Block block2 = world.getBlock(x, y, z);
                            if (block2 instanceof BlockStoneRaw) {
                                int meta2 = world.getBlockMetadata(x, y, z);
                                world.setBlock(x, y, z, block, meta2, 2);
                            }
                        }
                    }
                }
            }
        }
    }
}
