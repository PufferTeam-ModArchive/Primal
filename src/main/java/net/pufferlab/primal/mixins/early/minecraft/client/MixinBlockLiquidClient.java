package net.pufferlab.primal.mixins.early.minecraft.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Mixin(BlockLiquid.class)
public class MixinBlockLiquidClient {

    @ModifyExpressionValue(
        method = "shouldSideBeRendered",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/IBlockAccess;getBlock(III)Lnet/minecraft/block/Block;"))
    private Block redirectMeta$shouldSideBeRendered$primal(Block original, IBlockAccess world, int x, int y, int z) {
        return getFluidBlock$primal(original, world, x, y, z);
    }

    @Unique
    private static Block getFluidBlock$primal(Block original, IBlockAccess world, int x, int y, int z) {
        if (original instanceof IPrimalBlock primalBlock) {
            if (primalBlock.isWaterlogged(world, x, y, z)) {
                return primalBlock.getWaterloggedBlock(world, x, y, z);
            }
        }
        return original;
    }

}
