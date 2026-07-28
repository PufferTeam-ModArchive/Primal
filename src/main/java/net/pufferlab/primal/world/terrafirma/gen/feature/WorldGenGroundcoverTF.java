package net.pufferlab.primal.world.terrafirma.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.WorldUtils;

public class WorldGenGroundcoverTF {

    public WorldGenGroundcoverTF() {}

    public boolean generate(World world, Random rand, int x, int y, int z, Block block, int meta) {
        for (int l = 0; l < getAmount(); ++l) {
            int i1 = x + rand.nextInt(8) - rand.nextInt(8);
            int k1 = z + rand.nextInt(8) - rand.nextInt(8);
            int j1 = world.getHeightValue(i1, k1);

            if (world.isAirBlock(i1, j1, k1) && (!world.provider.hasNoSky || j1 < 255)
                && block.canBlockStay(world, i1, j1, k1)
                && canPlaceBlock(world, i1, j1, k1)) {
                WorldUtils.setBlockWorldgen(world, i1, j1, k1, block, meta);
            }
        }
        return true;
    }

    public int getAmount() {
        return 5;
    }

    public boolean canPlaceBlock(World world, int x, int y, int z) {
        return true;
    }
}
