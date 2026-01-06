package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.pufferlab.primal.blocks.BlockBarrel;
import net.pufferlab.primal.blocks.BlockLargeVessel;

public class ItemBlockPrimal extends ItemBlock {

    public ItemBlockPrimal(Block block) {
        super(block);
        if (block instanceof BlockLargeVessel || block instanceof BlockBarrel) {
            this.setMaxStackSize(1);
        }
    }
}
