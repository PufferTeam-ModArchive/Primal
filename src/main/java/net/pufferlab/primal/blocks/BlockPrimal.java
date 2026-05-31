package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockPrimal;
import net.pufferlab.primal.utils.BlockUtils;

public abstract class BlockPrimal extends Block implements IPrimalBlock {

    public BlockPrimal(Material materialIn) {
        super(materialIn);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World worldIn, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
        List<AxisAlignedBB> bounds;
        this.setBlockBoundsBasedOnState(worldIn, x, y, z);
        bounds = getBounds(worldIn, x, y, z, null, BoundsType.rayTraced);
        if (bounds != null && !bounds.isEmpty()) {
            for (AxisAlignedBB bb : bounds) {
                MovingObjectPosition mop = BlockUtils.collisionRayTrace(bb, worldIn, x, y, z, startVec, endVec);
                if (mop != null) {
                    return mop;
                }
            }
        }
        return BlockUtils.collisionRayTrace(
            AxisAlignedBB.getBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ),
            worldIn,
            x,
            y,
            z,
            startVec,
            endVec);
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask,
        List<AxisAlignedBB> list, Entity collider) {
        this.setBlockBoundsBasedOnState(worldIn, x, y, z);
        List<AxisAlignedBB> bounds;
        bounds = getBounds(worldIn, x, y, z, null, BoundsType.collision);
        if (bounds != null && !bounds.isEmpty()) {
            for (AxisAlignedBB bb : bounds) {
                bb = bb.copy()
                    .offset(x, y, z);
                if (mask.intersectsWith(bb)) {
                    list.add(bb);
                }
            }
        }
        if (collideDefaultBounds()) {
            AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(worldIn, x, y, z);

            if (axisalignedbb1 != null && mask.intersectsWith(axisalignedbb1)) {
                list.add(axisalignedbb1);
            }
        }
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
