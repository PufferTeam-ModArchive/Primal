package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.blocks.BlockMetal;

public class ItemBlockMetal extends ItemBlockMetaHeatable {

    public ItemBlockMetal(Block block) {
        super(block);
    }

    @Override
    public int getMeltingTemperature(ItemStack stack) {
        BlockMetal block = (BlockMetal) field_150939_a;
        return block.getMetalTypes()[stack.getItemDamage()].meltingTemperature;
    }

    @Override
    public int getWeldingTemperature(ItemStack stack) {
        BlockMetal block = (BlockMetal) field_150939_a;
        return block.getMetalTypes()[stack.getItemDamage()].weldingTemperature;
    }

    @Override
    public boolean registerOre() {
        return true;
    }
}
