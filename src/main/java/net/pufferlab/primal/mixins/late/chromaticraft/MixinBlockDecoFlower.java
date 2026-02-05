package net.pufferlab.primal.mixins.late.chromaticraft;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.ItemUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import Reika.ChromatiCraft.Block.Worldgen.BlockDecoFlower;
import Reika.DragonAPI.Libraries.Registry.ReikaTreeHelper;

@Mixin(BlockDecoFlower.class)
public abstract class MixinBlockDecoFlower {

    /**
     * @author JBT
     * @reason This fixes Primal grass/dirt not being detected as valid soil
     **/
    @Overwrite()
    public boolean canBlockStay(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        BlockDecoFlower.Flowers flower = BlockDecoFlower.Flowers.list[world.getBlockMetadata(x, y, z)];
        switch (flower) {
            case ENDERFLOWER:
            case RESOCLOVER:
            case GLOWDAISY:
            case LUMALILY:
                Block b = world.getBlock(x, y - 1, z);
                return b == Blocks.dirt || b == Blocks.grass
                    || ItemUtils.isGrassBlock(block)
                    || ItemUtils.isDirtBlock(block);
        }
        return BlockDecoFlower.Flowers.list[world.getBlockMetadata(x, y, z)].canPlantAt(world, x, y, z);
    }

    private boolean onActiveGrass(World world, int x, int y, int z) {
        Block b = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        boolean isValidSoil = ItemUtils.isGrassBlock(b) || (b == Blocks.grass && meta == 0);

        boolean isLeaf = ReikaTreeHelper.JUNGLE.isTreeLeaf(b, meta) && ReikaTreeHelper.isNaturalLeaf(world, x, y, z);

        return isValidSoil || isLeaf;
    }
}
