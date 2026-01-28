package net.pufferlab.primal.mixins.early.minecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Utils;
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

    @Inject(method = "updateTick", at = @At("HEAD"))
    public void updateTick$primal(World worldIn, int x, int y, int z, Random random, CallbackInfo cir) {
        if (!worldIn.isRemote) {
            if (!(this instanceof ITileEntityProvider)) {
                Block blockBelow = worldIn.getBlock(x, y - 1, z);
                Block block = worldIn.getBlock(x, y, z);
                int meta = worldIn.getBlockMetadata(x, y, z);

                if (!blockBelow.getMaterial()
                    .isReplaceable()) {
                    boolean moved = false;
                    if (Utils.getBlockDirection(worldIn, x, y, z, ForgeDirection.SOUTH, ForgeDirection.DOWN)
                        .getMaterial()
                        .isReplaceable() && random.nextInt(4) == 0) {
                        worldIn.setBlockToAir(x, y, z);
                        Utils.setBlockDirection(worldIn, x, y, z, block, meta, ForgeDirection.SOUTH);
                        moved = true;
                    } else if (Utils.getBlockDirection(worldIn, x, y, z, ForgeDirection.NORTH, ForgeDirection.DOWN)
                        .getMaterial()
                        .isReplaceable() && random.nextInt(4) == 0) {
                            worldIn.setBlockToAir(x, y, z);
                            Utils.setBlockDirection(worldIn, x, y, z, block, meta, ForgeDirection.NORTH);
                            moved = true;
                        } else if (Utils.getBlockDirection(worldIn, x, y, z, ForgeDirection.EAST, ForgeDirection.DOWN)
                            .getMaterial()
                            .isReplaceable() && random.nextInt(4) == 0) {
                                worldIn.setBlockToAir(x, y, z);
                                Utils.setBlockDirection(worldIn, x, y, z, block, meta, ForgeDirection.EAST);
                                moved = true;
                            } else
                            if (Utils.getBlockDirection(worldIn, x, y, z, ForgeDirection.WEST, ForgeDirection.DOWN)
                                .getMaterial()
                                .isReplaceable() && random.nextInt(4) == 0) {
                                    worldIn.setBlockToAir(x, y, z);
                                    Utils.setBlockDirection(worldIn, x, y, z, block, meta, ForgeDirection.WEST);
                                    moved = true;
                                }
                    if (moved) {
                        Utils.playSound(worldIn, x, y, z, SoundTypePrimal.slidingSoil);
                    }
                }

            }
        }
    }
}
