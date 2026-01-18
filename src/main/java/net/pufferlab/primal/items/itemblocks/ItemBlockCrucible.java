package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockCast;
import net.pufferlab.primal.blocks.BlockCrucible;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.utils.FluidUtils;

public class ItemBlockCrucible extends ItemBlockPrimal implements IHeatableItem {

    public ItemBlockCrucible(Block p_i45328_1_) {
        super(p_i45328_1_);

        if (p_i45328_1_ instanceof BlockCrucible) {
            this.setMaxStackSize(1);
        }
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {

        if (cancelPlacement(stack)) return false;

        if (world.getBlock(x, y - 1, z) instanceof BlockCast && field_150939_a instanceof BlockCrucible) {
            return false;
        }

        if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
            return false;
        }

        if (world.getBlock(x, y, z) == field_150939_a) {
            field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
            field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
        }

        return true;
    }

    @Override
    public void onUpdateHeat(ItemStack stack, World worldIn) {
        IHeatableItem.super.onUpdateHeat(stack, worldIn);

        if (stack.hasTagCompound()) {
            FluidUtils.updateFluidTankNBT(stack.getTagCompound());
        }
    }
}
