package net.pufferlab.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.tileentities.IMotion;

public abstract class BlockMotion extends BlockContainerPrimal {

    protected BlockMotion(Material material) {
        super(material);
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);

        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof IMotion tef) {
            tef.scheduleUpdate();
        }
    }

    @Override
    public void onBlockSidePlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn,
        int side) {
        super.onBlockSidePlacedBy(worldIn, x, y, z, placer, itemIn, side);

        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof IMotion tef) {
            tef.scheduleUpdate();
        }
    }

    @Override
    public int getRenderType() {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        return true;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }
}
