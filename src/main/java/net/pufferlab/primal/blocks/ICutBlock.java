package net.pufferlab.primal.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.tileentities.TileEntityCut;
import net.pufferlab.primal.tileentities.TileEntityCutDouble;

public interface ICutBlock {

    default int getMaterialMeta(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCut tef) {
            return tef.getMaterialMeta();
        }
        return 0;
    }

    default int getMaterialMeta2(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCutDouble tef) {
            return tef.getMaterialMeta2();
        }
        return 0;
    }

    default void setCutTileEntity(World worldIn, int x, int y, int z, int material) {
        TileEntity te = createCutTileEntity(worldIn, x, y, z, material);
        if (te != null) {
            worldIn.setTileEntity(x, y, z, te);
        }
    }

    default void setCutTileEntity(World worldIn, int x, int y, int z, int material, int material2) {
        if (material2 != -1) {
            TileEntity te = createCutTileEntity(worldIn, x, y, z, material, material2);
            if (te != null) {
                worldIn.setTileEntity(x, y, z, te);
            }
        } else {
            TileEntity te = createCutTileEntity(worldIn, x, y, z, material);
            if (te != null) {
                worldIn.setTileEntity(x, y, z, te);
            }
        }
    }

    default TileEntity createCutTileEntity(World world, int x, int y, int z, int material) {
        return new TileEntityCut(world, x, y, z, material);
    }

    default TileEntity createCutTileEntity(World world, int x, int y, int z, int material, int material2) {
        return new TileEntityCutDouble(world, x, y, z, material, material2);
    }
}
