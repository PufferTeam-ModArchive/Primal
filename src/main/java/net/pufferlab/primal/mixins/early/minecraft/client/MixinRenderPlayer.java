package net.pufferlab.primal.mixins.early.minecraft.client;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.pufferlab.primal.client.models.entities.ModelBipedPrimal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer extends RendererLivingEntity {

    @Shadow
    public ModelBiped modelBipedMain;

    protected MixinRenderPlayer(ModelBase p_i1261_1_, float p_i1261_2_) {
        super(p_i1261_1_, p_i1261_2_);
    }

    @Inject(method = "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V", at = @At("TAIL"))
    private void doRender$primal(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_,
        double p_76986_6_, float p_76986_8_, float p_76986_9_, CallbackInfo ci) {
        for (ModelBiped biped : ModelBipedPrimal.modelBipeds) {
            biped.aimedBow = this.modelBipedMain.aimedBow;
            biped.isSneak = this.modelBipedMain.isSneak;
            biped.heldItemRight = this.modelBipedMain.heldItemRight;
        }
    }

}
