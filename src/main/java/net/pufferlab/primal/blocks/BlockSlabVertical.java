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
                int i1 = worldIn.getBlockMetadata(x, y, z);

                boolean subX = (i1 & 0x8) != 0;
                boolean subZ = (i1 & 0x4) != 0;
                boolean subXHigh = (i1 & 0x2) != 0;
                boolean subZHigh = (i1 & 0x1) != 0;

                if (((l == 1 || l == 3) && subX) || ((l == 0 || l == 2) && subZ)) {
                    if (l == 0) {
                        worldIn.setBlockMetadataWithNotify(x, y, z, 2, 2);
                    }
                    if (l == 1) {
                        worldIn.setBlockMetadataWithNotify(x, y, z, 1, 2);
                    }

                    if (l == 2) {
                        worldIn.setBlockMetadataWithNotify(x, y, z, 3, 2);
                    }

                    if (l == 3) {
                        worldIn.setBlockMetadataWithNotify(x, y, z, 0, 2);

                    }
                } else {
                    if (l == 0 || l == 2) {
                        if (subZHigh) {
                            worldIn.setBlockMetadataWithNotify(x, y, z, 2, 2);
                        } else {
                            worldIn.setBlockMetadataWithNotify(x, y, z, 3, 2);
                        }
                    }

                    if (l == 1 || l == 3) {
                        if (subXHigh) {
                            worldIn.setBlockMetadataWithNotify(x, y, z, 0, 2);
                        } else {
                            worldIn.setBlockMetadataWithNotify(x, y, z, 1, 2);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int onBlockPlaced(World worldIn, int x, int y, int z, int side, float subX, float subY, float subZ,
        int meta) {
        if (this.field_150004_a) return 0;
        int packed = 0;

        if (subX == 1.0F || subX == 0.0) packed |= 0x8;
        if (subZ == 1.0F || subZ == 0.0F) packed |= 0x4;
        if (subX > 0.5F) packed |= 0x2;
        if (subZ > 0.5F) packed |= 0x1;

        return packed;
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
