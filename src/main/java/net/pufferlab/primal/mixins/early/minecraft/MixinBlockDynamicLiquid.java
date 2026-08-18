package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Mixin(BlockDynamicLiquid.class)
public class MixinBlockDynamicLiquid {

    @ModifyExpressionValue(
        method = "updateTick",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlock(III)Lnet/minecraft/block/Block;"))
    private Block redirectBlock$updateTick$primal(Block original, World world, int x, int y, int z) {
        return getFluidBlock$primal(original, world, x, y, z);
    }

    @ModifyExpressionValue(
        method = "func_149809_q",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlock(III)Lnet/minecraft/block/Block;"))
    private Block redirectBlock$func_149809_q$primal(Block original, World world, int x, int y, int z) {
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
