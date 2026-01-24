package net.pufferlab.primal.world.gen.feature;

import net.minecraft.block.Block;

public class WorldGenGroundOre extends WorldGenGroundcover {

    public WorldGenGroundOre(Block block) {
        super(block);
    }

    @Override
    public int getAmount() {
        return 3;
    }
}
