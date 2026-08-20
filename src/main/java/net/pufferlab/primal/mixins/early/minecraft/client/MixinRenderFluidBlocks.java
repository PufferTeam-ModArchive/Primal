package net.pufferlab.primal.mixins.early.minecraft.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Mixin(RenderBlocks.class)
public abstract class MixinRenderFluidBlocks {

    @Shadow
    public IBlockAccess blockAccess;

    @ModifyExpressionValue(
        method = "renderBlockLiquid",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/IBlockAccess;getBlockMetadata(III)I"))
    private int redirectMeta$renderBlockLiquid$primal(int original, Block block, int x, int y, int z) {
        return getFluidMeta$primal(original, this.blockAccess, x, y, z);
    }

    @ModifyExpressionValue(
        method = "getLiquidHeight",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/block/material/Material;isSolid()Z"))
    private boolean redirect$getLiquidHeight$primal(boolean original) {
        return true;
    }

    @Redirect(
        method = "getLiquidHeight",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/IBlockAccess;getBlockMetadata(III)I"))
    private int redirectMeta$getLiquidHeight$primal(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);

        return getFluidMeta$primal(meta, this.blockAccess, x, y, z);
    }

    @Redirect(
        method = "getLiquidHeight",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/IBlockAccess;getBlock(III)Lnet/minecraft/block/Block;"))
    private Block redirectBlock$getLiquidHeight$primal(IBlockAccess world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);

        return getFluidBlock$primal(block, this.blockAccess, x, y, z);
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

    @Unique
    private static int getFluidMeta$primal(int original, IBlockAccess world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block instanceof IPrimalBlock primalBlock) {
            if (primalBlock.isWaterlogged(world, x, y, z)) {
                return primalBlock.getWaterloggedMeta(world, x, y, z);
            }
        }
        return original;
    }
}
