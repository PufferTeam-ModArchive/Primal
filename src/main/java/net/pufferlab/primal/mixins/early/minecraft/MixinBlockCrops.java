package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.pufferlab.primal.utils.BlockUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockCrops.class)
public class MixinBlockCrops {

    @Inject(method = "canPlaceBlockOn", at = @At("HEAD"), cancellable = true)
    private void canPlaceBlockOn(Block ground, CallbackInfoReturnable<Boolean> cir) {
        if (BlockUtils.isFarmlandBlock(ground)) cir.setReturnValue(true);
    }
}
