package net.pufferlab.primal.mixins.early.minecraft.client;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityDiggingFX.class, priority = 2000)
public abstract class MixinEntityDiggingFX {

    @Shadow
    private Block field_145784_a;
    @Shadow(remap = false)
    private int side;

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDDDDLnet/minecraft/block/Block;II)V", at = @At("RETURN"))
    private void onInit(World world, double x, double y, double z, double mx, double my, double mz, Block block,
        int meta, int side, CallbackInfo ci) {
        if (block instanceof IPrimalBlock block2) {
            if (block2.useWorldIcon()) {
                int x2 = Utils.floor(x);
                int y2 = Utils.floor(y);
                int z2 = Utils.floor(z);
                double x3 = (x - x2);
                double y3 = (y - y2);
                double z3 = (z - z2);
                int x4 = x2;
                int y4 = y2;
                int z4 = z2;
                if (Utils.isClose(x3, 0.1F)) {
                    x4--;
                }
                if (Utils.isClose(x3, 0.9F)) {
                    x4++;
                }
                if (Utils.isClose(y3, 0.1F)) {
                    y4--;
                }
                if (Utils.isClose(y3, 0.9F)) {
                    y4++;
                }
                if (Utils.isClose(z3, 0.1F)) {
                    z4--;
                }
                if (Utils.isClose(z3, 0.9F)) {
                    z4++;
                }
                boolean b1 = false;
                boolean b2 = false;
                Block block3 = world.getBlock(x2, y2, z2);
                if (block3 instanceof IPrimalBlock block4) {
                    if (block4.useWorldIcon()) {
                        ((EntityDiggingFX) (Object) this).setParticleIcon(block.getIcon(world, x2, y2, z2, side));
                        b1 = true;
                    }
                }
                if (!b1) {
                    Block block4 = world.getBlock(x4, y4, z4);
                    if (block4 instanceof IPrimalBlock block5) {
                        if (block5.useWorldIcon()) {
                            ((EntityDiggingFX) (Object) this).setParticleIcon(block.getIcon(world, x4, y4, z4, side));
                            b2 = true;
                        }
                    }
                    if (!b2) {
                        ((EntityDiggingFX) (Object) this).setParticleIcon(block.getIcon(world, x2, y2 - 1, z2, side));
                    }
                }
            }
        }
    }

    @Inject(method = "applyColourMultiplier", at = @At("HEAD"), cancellable = true)
    public void applyColourMultiplier$primal(int x, int y, int z, CallbackInfoReturnable<EntityDiggingFX> cir) {
        if (this.field_145784_a instanceof IPrimalBlock block) {
            if (block.hasOverlay()) {
                cir.setReturnValue((EntityDiggingFX) (Object) this);
            }
        }
    }

    @Inject(method = "applyRenderColor", at = @At("HEAD"), cancellable = true)
    public void applyRenderColor$primal(int p_90019_1_, CallbackInfoReturnable<EntityDiggingFX> cir) {
        if (this.field_145784_a instanceof IPrimalBlock block) {
            if (block.hasOverlay()) {
                cir.setReturnValue((EntityDiggingFX) (Object) this);
            }
        }
    }

}
