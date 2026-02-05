package net.pufferlab.primal.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.ItemUtils;

public class WorldGenGroundShell extends WorldGenGroundcover {

    public WorldGenGroundShell() {
        super(Registry.ground_shell, 1, true);
    }

    @Override
    public int getAmount() {
        return 4;
    }

    @Override
    public boolean canPlaceBlock(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y - 1, z);
        if (ItemUtils.isSandBlock(block) && block != Blocks.grass) {
            return true;
        }
        return false;
    }
}
