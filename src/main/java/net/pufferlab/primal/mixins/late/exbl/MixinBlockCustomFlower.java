package net.pufferlab.primal.mixins.late.exbl;

import net.minecraft.block.Block;
import net.pufferlab.primal.utils.ItemUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import extrabiomes.blocks.BlockCustomFlower;
import extrabiomes.lib.BiomeSettings;

@Mixin(BlockCustomFlower.class)
public class MixinBlockCustomFlower {

    /**
     * @author JBT
     * @reason This fixes Primal grass/dirt not being detected as valid soil
     **/
    @Overwrite(remap = false)
    private boolean canThisPlantGrowOnThisBlock(Block block) {
        // TODO: separate rules for edge cases (like cactus)
        return ItemUtils.isGrassBlock(block) || ItemUtils.isDirtBlock(block)
            || ItemUtils.isFarmlandBlock(block)
            || ItemUtils.isSandBlock(block)
            || (BiomeSettings.MOUNTAINRIDGE.getBiome()
                .isPresent()
                && block.equals(
                    BiomeSettings.MOUNTAINRIDGE.getBiome()
                        .get().topBlock));
    }
}
