package net.pufferlab.primal.mixins.early.minecraft.client;

import static net.pufferlab.primal.client.renderer.RenderProjection.*;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "isAmbientOcclusionEnabled", at = @At("HEAD"), cancellable = true)
    private static void primal$isAmbientOcclusionEnabled(CallbackInfoReturnable<Boolean> cir) {
        if (redirect) {
            cir.setReturnValue(false);
        }
    }
}
