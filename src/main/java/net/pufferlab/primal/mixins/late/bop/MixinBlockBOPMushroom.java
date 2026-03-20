package net.pufferlab.primal.mixins.late.bop;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.BlockUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.blocks.BlockBOPMushroom;

@Mixin(BlockBOPMushroom.class)
public class MixinBlockBOPMushroom {

    /**
     * @author JBT
     * @reason This fixes Primal grass/dirt not being detected as valid soil
     **/
    @Overwrite(remap = false)
    public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
        Block block = world.getBlock(x, y - 1, z);

        switch (metadata) {
            case 0: // Toadstool
                return BlockUtils.isGrassBlock(block) || BlockUtils.isDirtBlock(block)
                    || block == Blocks.mycelium
                    || block == Blocks.netherrack
                    || block == BOPCBlocks.overgrownNetherrack;

            case 1: // Portobello
                return BlockUtils.isGrassBlock(block) || BlockUtils.isDirtBlock(block) || block == Blocks.mycelium;

            case 2: // Blue Milk Cap
                return BlockUtils.isGrassBlock(block) || BlockUtils.isDirtBlock(block) || block == Blocks.mycelium;

            case 3: // Glowshroom
                return BlockUtils.isGrassBlock(block) || BlockUtils.isDirtBlock(block)
                    || block == Blocks.mycelium
                    || BlockUtils.isNaturalStone(block)
                    || block == Blocks.netherrack
                    || block == BOPCBlocks.overgrownNetherrack;

            case 5: // Shadow Shroom
                return BlockUtils.isGrassBlock(block) || BlockUtils.isDirtBlock(block)
                    || block == Blocks.mycelium
                    || block == Blocks.end_stone
                    || block == BOPCBlocks.bopGrass;

            default:
                return BlockUtils.isGrassBlock(block) || BlockUtils.isDirtBlock(block)
                    || block == Blocks.mycelium
                    || block == BOPCBlocks.overgrownNetherrack
                    || block == BOPCBlocks.bopGrass;
        }
    }
}
