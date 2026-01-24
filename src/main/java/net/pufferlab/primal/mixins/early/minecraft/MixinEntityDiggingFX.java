package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.init.Blocks;
import net.pufferlab.primal.Utils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityDiggingFX.class, priority = 2000)
public abstract class MixinEntityDiggingFX {

    @Shadow
    private Block field_145784_a;
    @Shadow(remap = false)
    private int side;

    @Inject(method = "applyColourMultiplier", at = @At("HEAD"), cancellable = true)
    public void applyColourMultiplier$primal(int x, int y, int z, CallbackInfoReturnable<EntityDiggingFX> cir) {
        if (Utils.isGrassBlock(this.field_145784_a) && this.field_145784_a != Blocks.grass) {
            cir.setReturnValue((EntityDiggingFX) (Object) this);
        }
    }

    @Inject(method = "applyRenderColor", at = @At("HEAD"), cancellable = true)
    public void applyRenderColor$primal(int p_90019_1_, CallbackInfoReturnable<EntityDiggingFX> cir) {
        if (Utils.isGrassBlock(this.field_145784_a)) {
            cir.setReturnValue((EntityDiggingFX) (Object) this);
        }
    }

}
