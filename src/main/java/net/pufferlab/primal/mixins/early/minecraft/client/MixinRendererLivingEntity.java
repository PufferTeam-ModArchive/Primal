package net.pufferlab.primal.mixins.early.minecraft.client;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.pufferlab.primal.client.renderer.RenderWearable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity {

    @Shadow
    protected abstract float interpolateRotation(float angle1, float angle2, float p_77034_3_);

    @Shadow
    protected abstract float handleRotationFloat(EntityLivingBase entityLivingBase, float p_77044_2_);

    @Inject(
        method = "doRender",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;renderModel(Lnet/minecraft/entity/EntityLivingBase;FFFFFF)V",
            shift = At.Shift.AFTER))
    private void primal$postRender(EntityLivingBase entity, double x, double y, double z, float yaw, float partialTicks,
        CallbackInfo ci) {

        float f2 = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
        float f3 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
        float f4 = this.handleRotationFloat(entity, partialTicks);
        float f13 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        float f5 = 0.0625F;
        float f6 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
        float f7 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

        RenderWearable.wearableHandler
            .handleRendering((RendererLivingEntity) (Object) this, entity, f7, f6, f4, f3, f2, f13, f5, partialTicks);
    }
}
