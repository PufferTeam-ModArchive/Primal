package net.pufferlab.primal.mixins.early.minecraft.client;

import static net.pufferlab.primal.client.renderer.RenderProjection.*;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlockClient {

    @Inject(method = "getMixedBrightnessForBlock", at = @At("HEAD"), cancellable = true)
    private void primal$getMixedBrightnessForBlock(IBlockAccess world, int x, int y, int z,
        CallbackInfoReturnable<Integer> cir) {
        if (redirect && x != tempX && y != tempY && z != tempZ) {
            cir.setReturnValue(tempBlock.getMixedBrightnessForBlock(world, tempX, tempY, tempZ));
        }
    }
}
