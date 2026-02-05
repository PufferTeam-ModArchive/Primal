package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.pufferlab.primal.utils.ItemUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBush.class)
public class MixinBlockBush {

    @Inject(method = "canPlaceBlockOn", at = @At("HEAD"), cancellable = true)
    public void canPlaceBlockOn$primal(Block ground, CallbackInfoReturnable<Boolean> cir) {
        if (ItemUtils.isGrassBlock(ground) || ItemUtils.isDirtBlock(ground) || ItemUtils.isFarmlandBlock(ground)) {
            cir.setReturnValue(true);
        }
    }
}
