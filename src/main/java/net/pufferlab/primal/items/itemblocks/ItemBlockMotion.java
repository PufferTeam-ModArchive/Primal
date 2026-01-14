package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockMotion;

public class ItemBlockMotion extends ItemBlockPrimal {

    public ItemBlockMotion(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {

        if (cancelPlacement(stack)) return false;

        if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
            return false;
        }

        if (world.getBlock(x, y, z) == field_150939_a) {
            field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
            field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
            if (field_150939_a instanceof BlockMotion block) {
                block.onBlockSidePlacedBy(world, x, y, z, player, stack, side);
            }
        }

        return true;
    }
}
