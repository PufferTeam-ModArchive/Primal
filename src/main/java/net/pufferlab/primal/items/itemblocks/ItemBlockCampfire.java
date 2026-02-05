package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockCampfire;
import net.pufferlab.primal.tileentities.TileEntityCampfire;
import net.pufferlab.primal.utils.ItemUtils;

public class ItemBlockCampfire extends ItemBlock {

    public ItemBlockCampfire(Block block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        if (field_150939_a instanceof BlockCampfire) {
            metadata = 5;
        }
        if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
            return false;
        }

        if (world.getBlock(x, y, z) == field_150939_a) {
            field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
            field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
        }

        if (field_150939_a instanceof BlockCampfire) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TileEntityCampfire tef) {
                tef.isBuilt = true;
                tef.setInventorySlotContentsUpdate(1, ItemUtils.getModItem("straw_kindling", 1));
                for (int i = 2; i < 6; i++) {
                    tef.setInventorySlotContentsUpdate(i, ItemUtils.getModItem("firewood", 1));
                }
            }
        }

        return true;
    }
}
