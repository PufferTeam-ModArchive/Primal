package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLiquid.class)
public class MixinBlockLiquid {

    @Inject(method = "getFlowVector", at = @At("RETURN"), cancellable = true)
    private void getFlowVector$primal(IBlockAccess p_149800_1_, int p_149800_2_, int p_149800_3_, int p_149800_4_,
        CallbackInfoReturnable<Vec3> cir) {
        if (p_149800_1_.getBlock(p_149800_2_, p_149800_3_, p_149800_4_) instanceof IPrimalBlock primalBlock) {
            if (primalBlock.isWaterlogged(p_149800_1_, p_149800_2_, p_149800_3_, p_149800_4_)) {
                cir.setReturnValue(Vec3.createVectorHelper(0.0D, 0.0D, 0.0D));
            }
        }
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
