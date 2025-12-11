package net.pufferlab.primal.blocks;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTorchPrimitive extends BlockTorch implements ITileEntityProvider {
    public BlockTorchPrimitive() {
        super();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
