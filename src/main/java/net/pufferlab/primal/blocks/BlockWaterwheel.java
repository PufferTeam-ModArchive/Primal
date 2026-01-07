package net.pufferlab.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.items.itemblocks.ItemBlockWaterwheel;
import net.pufferlab.primal.tileentities.TileEntityWaterwheel;

public class BlockWaterwheel extends BlockMotion {

    public IIcon[] icons = new IIcon[1];

    public BlockWaterwheel() {
        super(Material.wood);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityWaterwheel tef) {
            if (tef.isExtension) {
                TileEntity te2 = worldIn.getTileEntity(tef.baseXCoord, tef.baseYCoord, tef.baseZCoord);
                if (te2 instanceof TileEntityWaterwheel tef2) {
                    tef2.scheduleFlowUpdate();
                }
            } else {
                tef.scheduleFlowUpdate();
            }
        }
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, int x, int y, int z, int meta) {
        super.onBlockDestroyedByPlayer(worldIn, x, y, z, meta);
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityWaterwheel tef) {
            if (tef.isExtension) {
                clearExtensions(worldIn, tef.baseXCoord, tef.baseYCoord, tef.baseZCoord, tef.axisMeta);
                TileEntity te2 = worldIn.getTileEntity(tef.baseXCoord, tef.baseYCoord, tef.baseZCoord);
                if (te2 instanceof TileEntityWaterwheel tef2) {
                    tef2.scheduleRemoval();
                }
            } else {
                clearExtensions(worldIn, tef.xCoord, tef.yCoord, tef.zCoord, tef.axisMeta);
            }
        }
    }

    public void clearExtensions(World world, int x, int y, int z, int axis) {
        for (int xf = -1; xf <= 1; xf++) {
            for (int zf = -1; zf <= 1; zf++) {
                if (!(xf == 0 && zf == 0)) {
                    int x2 = x;
                    int y2 = y;
                    int z2 = z;

                    if (axis == 0) {
                        x2 += xf;
                        z2 += zf;
                    } else if (axis == 1) {
                        x2 += xf;
                        y2 += zf;
                    } else if (axis == 2) {
                        y2 += xf;
                        z2 += zf;
                    }

                    TileEntity te = world.getTileEntity(x2, y2, z2);
                    if (te instanceof TileEntityWaterwheel tef) {
                        tef.scheduleRemoval();
                    }
                }
            }
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon("minecraft:planks_spruce");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icons[0];
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityWaterwheel();
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".waterwheel";
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getAxleRenderID();
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockWaterwheel.class;
    }
}
