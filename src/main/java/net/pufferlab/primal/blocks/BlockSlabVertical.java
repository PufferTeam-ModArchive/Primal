package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSlabVertical extends Block {

    protected final boolean field_150004_a;

    public BlockSlabVertical(boolean field_150004_a, Material material) {
        super(material);
        this.field_150004_a = field_150004_a;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        Block block = worldIn.getBlock(x, y, z);
        if (block instanceof BlockSlabVertical slab) {
            if (!slab.field_150004_a) {
                int l = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                int i1 = worldIn.getBlockMetadata(x, y, z) & 4;

                if (l == 0) {
                    worldIn.setBlockMetadataWithNotify(x, y, z, 2 | i1, 2);
                }

                if (l == 1) {
                    worldIn.setBlockMetadataWithNotify(x, y, z, 1 | i1, 2);
                }

                if (l == 2) {
                    worldIn.setBlockMetadataWithNotify(x, y, z, 3 | i1, 2);
                }

                if (l == 3) {
                    worldIn.setBlockMetadataWithNotify(x, y, z, 0 | i1, 2);
                }
            }
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        if (this.field_150004_a) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            int meta = world.getBlockMetadata(x, y, z) & 3;

            switch (meta) {
                case 2:
                    this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
                    break;

                case 1:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
                    break;

                case 3:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                    break;

                case 0:
                    this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;
            }
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return this.field_150004_a;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return this.field_150004_a;
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask,
        List<AxisAlignedBB> list, Entity collider) {
        this.setBlockBoundsBasedOnState(worldIn, x, y, z);
        super.addCollisionBoxesToList(worldIn, x, y, z, mask, list, collider);
    }
}
