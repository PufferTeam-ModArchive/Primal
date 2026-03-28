package net.pufferlab.primal.blocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.pufferlab.primal.client.utils.RenderState;

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

    default int getRenderShape() {
        return 0;
    }

    default int getStateID() {
        return 0;
    }

    default void setPass(int pass) {
        RenderState.setPass(this, pass);
    }

    default int getPass() {
        return RenderState.getPass(this);
    }

    default boolean isInventory() {
        return RenderState.isInventory(this);
    }

    default void setInventory(boolean state) {
        RenderState.setInventory(this, state);
    }

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
