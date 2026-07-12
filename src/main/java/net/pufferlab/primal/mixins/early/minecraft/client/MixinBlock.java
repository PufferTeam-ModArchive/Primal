package net.pufferlab.primal.mixins.early.minecraft.client;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.pufferlab.primal.world.VirtualBlock.*;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "getMixedBrightnessForBlock", at = @At("HEAD"), cancellable = true)
    private void primal$getMixedBrightnessForBlock(IBlockAccess world, int x, int y, int z,
                                                   CallbackInfoReturnable<Integer> cir) {
        if(redirect && x != tempX && y != tempY && z != tempZ) {
            cir.setReturnValue(tempBlock.getMixedBrightnessForBlock(world, tempX, tempY, tempZ));
        }
    }
}
