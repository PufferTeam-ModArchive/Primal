package net.pufferlab.primal.blocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public interface IPrimalBlock {

    default Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlock.class;
    };

    public CreativeTabs getCreativeTab();

    default boolean isEmissive() {
        return false;
    };

    default boolean hasOverlay() {
        return false;
    }

    default boolean canRegister() {
        return true;
    }

    default boolean useWorldIcon() {
        return false;
    }
}
