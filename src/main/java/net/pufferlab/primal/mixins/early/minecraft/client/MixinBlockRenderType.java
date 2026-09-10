package net.pufferlab.primal.mixins.early.minecraft.client;

import net.minecraft.block.Block;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

@Mixin(Block.class)
public class MixinBlockRenderType {

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    private void getRenderType$primal(CallbackInfoReturnable<Integer> cir) {
        if (this instanceof IPrimalBlock block) {
            ISimpleBlockRenderingHandler renderer = block.getRenderer();
            if (renderer != null) {
                cir.setReturnValue(block.getRenderId());
            }
        }
    }
}
