package net.pufferlab.primal.mixins.late.bop;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.blocks.BlockBOPCoral;

@Mixin(BlockBOPCoral.class)
public class MixinBlockBOPCoral extends Block {

    protected MixinBlockBOPCoral(Material materialIn) {
        super(materialIn);
    }

    @Shadow(remap = false)
    @Final
    private BlockBOPCoral.CoralCategory category;

    /**
     * @author JBT
     * @reason This fixes Primal grass/dirt not being detected as valid soil
     **/
    @Overwrite(remap = false)
    public boolean canBlockStay(World world, int x, int y, int z, int metadata) {
        Block block = world.getBlock(x, y - 1, z);

        if (category == BlockBOPCoral.CoralCategory.CAT1) {
            switch (metadata) {
                case 9: // Kelp Middle
                    return block == this;

                case 10: // Kelp Top
                    return block == this;

                case 11:
                    return block == this || Utils.isTerrainBlock(block)
                        || block == Blocks.sponge
                        || block == Blocks.clay
                        || block == BOPCBlocks.mud;
            }
        }

        return Utils.isTerrainBlock(block) || block == Blocks.sponge || block == Blocks.clay || block == BOPCBlocks.mud;
    }
}
