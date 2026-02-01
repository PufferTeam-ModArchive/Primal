package net.pufferlab.primal.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockPrimal;

public abstract class BlockContainerPrimal extends BlockContainer implements IPrimalBlock {

    protected BlockContainerPrimal(Material material) {
        super(material);
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockPrimal.class;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTab;
    }
}
