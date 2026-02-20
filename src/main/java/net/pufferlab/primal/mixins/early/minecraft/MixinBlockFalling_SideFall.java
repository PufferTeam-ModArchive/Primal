package net.pufferlab.primal.mixins.early.minecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.SoundTypePrimal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockFalling.class)
public abstract class MixinBlockFalling_SideFall extends Block {

    protected MixinBlockFalling_SideFall(Material materialIn) {
        super(materialIn);
    }

    @Inject(method = "updateTick", at = @At("HEAD"), cancellable = true)
    public void updateTick$primal(World worldIn, int x, int y, int z, Random random, CallbackInfo cir) {
        if (!worldIn.isRemote) {
            if (!(this instanceof ITileEntityProvider)) {
                Block blockBelow = worldIn.getBlock(x, y - 1, z);
                Block block = worldIn.getBlock(x, y, z);
                int meta = worldIn.getBlockMetadata(x, y, z);

                if (!blockBelow.getMaterial()
                    .isReplaceable()) {
                    Material material1 = BlockUtils
                        .getBlockDirection(worldIn, x, y, z, ForgeDirection.SOUTH, ForgeDirection.DOWN)
                        .getMaterial();
                    Material material2 = BlockUtils
                        .getBlockDirection(worldIn, x, y, z, ForgeDirection.NORTH, ForgeDirection.DOWN)
                        .getMaterial();
                    Material material3 = BlockUtils
                        .getBlockDirection(worldIn, x, y, z, ForgeDirection.EAST, ForgeDirection.DOWN)
                        .getMaterial();
                    Material material4 = BlockUtils
                        .getBlockDirection(worldIn, x, y, z, ForgeDirection.WEST, ForgeDirection.DOWN)
                        .getMaterial();
                    boolean moved = false;
                    if (material1.isReplaceable() && material1 != Material.water && random.nextInt(4) == 0) {
                        worldIn.setBlockToAir(x, y, z);
                        BlockUtils.setBlockDirection(worldIn, x, y, z, block, meta, ForgeDirection.SOUTH);
                        moved = true;
                    } else if (material2.isReplaceable() && material2 != Material.water && random.nextInt(4) == 0) {
                        worldIn.setBlockToAir(x, y, z);
                        BlockUtils.setBlockDirection(worldIn, x, y, z, block, meta, ForgeDirection.NORTH);
                        moved = true;
                    } else if (material3.isReplaceable() && material3 != Material.water && random.nextInt(4) == 0) {
                        worldIn.setBlockToAir(x, y, z);
                        BlockUtils.setBlockDirection(worldIn, x, y, z, block, meta, ForgeDirection.EAST);
                        moved = true;
                    } else if (material4.isReplaceable() && material4 != Material.water && random.nextInt(4) == 0) {
                        worldIn.setBlockToAir(x, y, z);
                        BlockUtils.setBlockDirection(worldIn, x, y, z, block, meta, ForgeDirection.WEST);
                        moved = true;
                    }
                    if (moved) {
                        BlockUtils.playSound(worldIn, x, y, z, SoundTypePrimal.soundSoilSlide);
                        cir.cancel();
                    }
                }

            }
        }
    }
}
