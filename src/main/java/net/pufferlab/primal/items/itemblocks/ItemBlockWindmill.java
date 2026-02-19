package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.BlockUtils;

public class ItemBlockWindmill extends ItemBlockPrimal {

    public ItemBlockWindmill(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {

        int axis = BlockUtils.getAxis(side);
        boolean isValid = placeExtensionAt(world, x, y, z, axis, true);
        if (isValid) {
            if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
                return false;
            }

            if (world.getBlock(x, y, z) == field_150939_a) {
                placeBlock(world, x, y, z, player, stack, metadata, side);
            }
            return true;
        }
        return false;
    }

    public boolean placeExtensionAt(World world, int x, int y, int z, int axis, boolean check) {
        for (int xf = -5; xf <= 5; xf++) {
            for (int zf = -5; zf <= 5; zf++) {
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

                if (block.getMaterial() != Material.air) {
                    return false;
                }
            }
        }
        return true;
    }
}
