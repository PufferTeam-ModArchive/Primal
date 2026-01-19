package net.pufferlab.primal.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;

public class WorldGenGroundRock extends WorldGenGroundcover {

    public WorldGenGroundRock(int meta) {
        super(Registry.ground_rock, meta);
    }

    @Override
    public boolean canPlaceBlock(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y - 1, z);
        if (!Utils.isSandBlock(block)) {
            return true;
        }
        return false;
    }
}
