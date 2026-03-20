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

    default int getPass() {
        return 0;
    }

    default void setPass(int renderPass) {}

    default boolean canRegister() {
        return true;
    }

    default boolean useWorldIcon() {
        return false;
    }

    default byte getBlockParticleAmount() {
        return 4;
    }

    default int getMaxMeta() {
        return 0;
    }
}
