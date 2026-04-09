package net.pufferlab.primal.mixins.early.minecraft.world;

import net.minecraft.world.gen.ChunkProviderGenerate;
import net.pufferlab.primal.world.ChunkProviderCustom;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Mixin(ChunkProviderGenerate.class)
public class MixinChunkProviderGenerate {

    @ModifyExpressionValue(method = "func_147424_a", at = @At(value = "CONSTANT", args = "intValue=63"))
    private int modifySeaLevel(int original) {
        return ChunkProviderCustom.getSeaLevel();
    }

    @ModifyExpressionValue(
        method = "func_147423_a",
        at = @At(value = "CONSTANT", args = "doubleValue=8.5", ordinal = 2))
    private double raiseBaseHeight(double original) {
        return original + ChunkProviderCustom.getRaiseFactor();
    }
}
