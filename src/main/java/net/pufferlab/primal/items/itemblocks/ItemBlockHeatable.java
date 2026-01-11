package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.pufferlab.primal.items.IHeatableItem;

public class ItemBlockHeatable extends ItemBlock implements IHeatableItem {

    public ItemBlockHeatable(Block p_i45328_1_) {
        super(p_i45328_1_);

        this.setMaxStackSize(1);
    }
}
