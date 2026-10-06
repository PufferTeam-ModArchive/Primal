package net.pufferlab.primal.mixins.early.minecraft.client;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.pufferlab.primal.client.renderer.blocks.BlockPrimalRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

    @Inject(
        method = "drawBlockDamageTexture(Lnet/minecraft/client/renderer/Tessellator;Lnet/minecraft/entity/EntityLivingBase;F)V",
        at = @At("HEAD"),
        remap = false)
    private void drawBlockDamageTexture$primal(Tessellator p_72717_1_, EntityLivingBase p_72717_2_, float p_72717_3_,
        CallbackInfo ci) {
        BlockPrimalRenderer.setRenderingBreakingState(true, p_72717_2_);
    }

    @Inject(
        method = "drawBlockDamageTexture(Lnet/minecraft/client/renderer/Tessellator;Lnet/minecraft/entity/EntityLivingBase;F)V",
        at = @At("TAIL"),
        remap = false)
    private void drawBlockDamageTexture$tail$primal(Tessellator p_72717_1_, EntityLivingBase p_72717_2_,
        float p_72717_3_, CallbackInfo ci) {
        BlockPrimalRenderer.setRenderingBreakingState(false, p_72717_2_);
    }
}
