package net.pufferlab.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockPrimal;

public abstract class BlockPrimal extends Block implements IPrimalBlock {

    public BlockPrimal(Material materialIn) {
        super(materialIn);
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockPrimal.class;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTab;
    }

    @Override
    public int getRenderType() {
        return getRenderId();
    }
}
