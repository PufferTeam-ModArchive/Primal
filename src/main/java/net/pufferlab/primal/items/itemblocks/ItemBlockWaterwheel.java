package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.tileentities.TileEntityWaterwheel;
import net.pufferlab.primal.utils.FacingUtils;

public class ItemBlockWaterwheel extends ItemBlockPrimal {

    public ItemBlockWaterwheel(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {

        int axis = FacingUtils.getAxis(side);
        boolean isValid = placeExtensionAt(world, x, y, z, axis, true);
        if (isValid) {
            if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
                return false;
            }

            placeExtensionAt(world, x, y, z, axis, false);

            if (world.getBlock(x, y, z) == field_150939_a) {
                placeBlock(world, x, y, z, player, stack, metadata, side);
            }
            return true;
        }
        return false;
    }

    public boolean placeExtensionAt(World world, int x, int y, int z, int axis, boolean check) {
        for (int xf = -1; xf <= 1; xf++) {
            for (int zf = -1; zf <= 1; zf++) {
                if (!(xf == 0 && zf == 0)) {
                    int x2 = x;
                    int y2 = y;
                    int z2 = z;

                    if (axis == 0) {
                        x2 += xf;
                        z2 += zf;
                    } else if (axis == 1) {
                        x2 += xf;
                        y2 += zf;
                    } else if (axis == 2) {
                        y2 += xf;
                        z2 += zf;
                    }

                    Block block = world.getBlock(x2, y2, z2);
                    if (check) {
                        if (block.getMaterial() != Material.air && block.getMaterial() != Material.water) {
                            return false;
                        }
                    } else {
                        world.setBlock(x2, y2, z2, Registry.waterwheel, 1, 3);
                        TileEntity te = world.getTileEntity(x2, y2, z2);
                        if (te instanceof TileEntityWaterwheel tef) {
                            tef.axisMeta = axis;
                            tef.isExtension = true;
                            tef.baseXCoord = x;
                            tef.baseYCoord = y;
                            tef.baseZCoord = z;
                        }
                    }
                }

            }
        }
        return true;
    }
}
