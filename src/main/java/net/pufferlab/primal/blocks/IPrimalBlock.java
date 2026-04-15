package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.pufferlab.primal.client.utils.RenderState;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public interface IPrimalBlock {

    default List<AxisAlignedBB> getBounds(World world, int x, int y, int z, BoundsType bounds) {
        return null;
    }

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

    default ISimpleBlockRenderingHandler getRenderer() {
        return null;
    }

    default int getRenderId() {
        ISimpleBlockRenderingHandler renderer = getRenderer();
        if (renderer != null) {
            return renderer.getRenderId();
        }
        return 0;
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
