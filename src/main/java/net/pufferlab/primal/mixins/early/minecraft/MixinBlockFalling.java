package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockFalling.class)
public abstract class MixinBlockFalling extends Block {

    protected MixinBlockFalling(Material materialIn) {
        super(materialIn);
    }

    @Inject(method = "func_149831_e", at = @At("HEAD"), cancellable = true)
    private static void func_149831_e$primal(World worldObj, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        Block block = worldObj.getBlock(i, j, k);
        if (worldObj.canPlaceEntityOnSide(block, i, j, k, true, 1, (Entity) null, (ItemStack) null)) {
            cir.setReturnValue(true);
        }
    }
}
