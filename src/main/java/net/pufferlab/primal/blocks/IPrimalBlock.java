package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.pufferlab.primal.client.utils.RenderState;
import net.pufferlab.primal.utils.BlockUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public interface IPrimalBlock {

    default List<AxisAlignedBB> getBounds(World world, int x, int y, int z, EntityPlayer player, BoundsType bounds) {
        return null;
    }

    default MovingObjectPosition customCollisionRayTrace(World worldIn, int x, int y, int z, Vec3 startVec,
        Vec3 endVec) {
        if (this instanceof Block thiz) {
            return BlockUtils.customCollisionRayTrace(thiz, worldIn, x, y, z, startVec, endVec);
        }
        return null;
    }

    default void addCustomCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask,
        List<AxisAlignedBB> list, Entity collider) {
        if (this instanceof Block thiz) {
            BlockUtils.addCustomCollisionBoxesToList(thiz, worldIn, x, y, z, mask, list, collider);
        }
    }

    default boolean renderDefaultBounds() {
        return true;
    }

    default boolean collideDefaultBounds() {
        return true;
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

    default boolean hideBlock() {
        return false;
    }
}
