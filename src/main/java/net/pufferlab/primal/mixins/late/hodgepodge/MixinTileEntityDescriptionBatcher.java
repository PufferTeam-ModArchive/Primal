package net.pufferlab.primal.mixins.late.hodgepodge;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.tileentities.ITile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mitchej123.hodgepodge.hax.TileEntityDescriptionBatcher;

@Mixin(TileEntityDescriptionBatcher.class)
public class MixinTileEntityDescriptionBatcher {

    @Inject(method = "queueSend", at = @At("HEAD"), cancellable = true, remap = false)
    private static void queueSend$primal(EntityPlayerMP player, TileEntity tile, S35PacketUpdateTileEntity packet,
        CallbackInfoReturnable<Boolean> ci) {
        if (tile instanceof ITile tef) {
            if (!tef.shouldBatchUpdate()) {
                ci.setReturnValue(false);
            }
        }
    }
}
