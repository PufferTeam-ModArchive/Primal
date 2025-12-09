package net.pufferlab.primal.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGround extends WorldGenerator {

    public Block block;
    public int meta;

    public WorldGenGround(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    @Override
    public boolean generate(World world, Random ran, int x, int y, int z) {
        for (int l = 0; l < 5; ++l) {
            int i1 = x + ran.nextInt(8) - ran.nextInt(8);
            int k1 = z + ran.nextInt(8) - ran.nextInt(8);
            int j1 = world.getHeightValue(i1, k1);

            if (world.isAirBlock(i1, j1, k1) && (!world.provider.hasNoSky || j1 < 255)
                && this.block.canBlockStay(world, i1, j1, k1)) {
                world.setBlock(i1, j1, k1, this.block, this.meta, 2);
            }
        }
        return true;
    }
}
