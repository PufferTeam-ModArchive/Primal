package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.client.utils.RenderState;
import net.pufferlab.primal.utils.BlockUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public interface IPrimalBlock {

    default boolean shouldReplace() {
        return false;
    }

    default boolean skipReplace(Block block, int meta) {
        return false;
    }

    default int metaToReplace(Block block, int meta) {
        return -1;
    }

    default Block blockToReplace(Block block, int meta) {
        return (Block) this;
    }

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

    default int getRenderShape(int meta) {
        return 0;
    }

    default boolean isSnowlogged(int meta) {
        return false;
    }

    default boolean isWaterlogged(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (isWaterlogged(meta)) {
            for (ForgeDirection direction : BlockUtils.sideDirections) {
                int x2 = x + direction.offsetX;
                int y2 = y + direction.offsetY;
                int z2 = z + direction.offsetZ;
                Block block = world.getBlock(x2, y2, z2);
                if (block == Blocks.water || block == Blocks.flowing_water) {
                    return true;
                }
            }
        }
        return false;
    }

    default boolean isWaterlogged(int meta) {
        return false;
    }

    default Block getWaterloggedBlock(IBlockAccess world, int x, int y, int z) {
        return Blocks.flowing_water;
    }

    default int getWaterloggedMeta(IBlockAccess world, int x, int y, int z) {
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
