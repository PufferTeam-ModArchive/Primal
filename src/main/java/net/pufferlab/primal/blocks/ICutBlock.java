package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.tileentities.TileEntityCut;
import net.pufferlab.primal.tileentities.TileEntityCutDouble;
import net.pufferlab.primal.utils.CutUtils;

public interface ICutBlock {

    default void getMaterialSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        if (this instanceof Block thiz) {
            CutUtils.getSubBlocks(thiz, list);
        }
    }

    default String[] getMaterialNames() {
        return CutUtils.getBlockNames();
    }

    default String getMaterialUnlocalizedName(int id) {
        return CutUtils.getUnlocalizedName(id);
    }

    default IIcon getMaterialIcon(int side, int meta) {
        return CutUtils.getIcon(side, meta);
    }

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
