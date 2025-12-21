package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;

public class BlockChimney extends BlockMeta {

    public BlockChimney() {
        super(Material.rock, Constants.chimneyTypes, "chimney");
        this.setHardness(0.6F);
        this.setTextureOverride(Constants.chimneyTextures);
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getChimneyRenderID();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        if (side.equals(ForgeDirection.UP) || side.equals(ForgeDirection.DOWN)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }
}
