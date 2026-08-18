package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public class MixinWorldLiquid {

    @Redirect(
        method = "handleMaterialAcceleration",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlock(III)Lnet/minecraft/block/Block;"))
    private Block primal$redirectGetBlock(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);

        return getFluidBlock$primal(block, world, x, y, z);
    }

    @Unique
    private static Block getFluidBlock$primal(Block original, IBlockAccess world, int x, int y, int z) {
        if (original instanceof IPrimalBlock primalBlock) {
            if (primalBlock.isWaterlogged(world, x, y, z)) {
                return primalBlock.getWaterloggedBlock(world, x, y, z);
            }
        }
        return original;
    }
}
