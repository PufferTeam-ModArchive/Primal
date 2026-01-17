package net.pufferlab.primal.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGroundcover extends WorldGenerator {

    public Block block;
    public int meta;
    public boolean randomMeta;

    public WorldGenGroundcover(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public WorldGenGroundcover(Block block, int meta, boolean randomMeta) {
        this(block, meta);
        this.randomMeta = randomMeta;
    }

    @Override
    public boolean generate(World world, Random ran, int x, int y, int z) {
        for (int l = 0; l < getAmount(); ++l) {
            int i1 = x + ran.nextInt(8) - ran.nextInt(8);
            int k1 = z + ran.nextInt(8) - ran.nextInt(8);
            int j1 = world.getHeightValue(i1, k1);

            if (world.isAirBlock(i1, j1, k1) && (!world.provider.hasNoSky || j1 < 255)
                && this.block.canBlockStay(world, i1, j1, k1)
                && canPlaceBlock(world, i1, j1, k1)) {
                int meta = this.meta;
                if (this.randomMeta) {
                    meta = world.rand.nextInt(this.meta + 1);
                }
                world.setBlock(i1, j1, k1, this.block, meta, 2);
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
