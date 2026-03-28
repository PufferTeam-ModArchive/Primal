package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.CropType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemSeedFood.class)
public abstract class MixinItemSeedFood extends Item {

    @Inject(method = "onItemUse", at = @At("HEAD"), cancellable = true)
    private void onItemUse$primal(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_,
        int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_,
        CallbackInfoReturnable<Boolean> cir) {
        if (CropType.cropTypes.contains(this)) {
            cir.setReturnValue(false);
        }
    }

}
