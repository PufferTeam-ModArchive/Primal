package net.pufferlab.primal.blocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public interface IPrimalBlock {

    public Class<? extends ItemBlock> getItemBlockClass();

    public CreativeTabs getCreativeTab();
}
