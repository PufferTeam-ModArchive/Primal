package net.pufferlab.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.TileEntityWaterWheel;

public class BlockWaterWheel extends BlockMotion {

    public IIcon[] icons = new IIcon[1];

    public BlockWaterWheel() {
        super(Material.wood);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityWaterWheel tef) {
            if (tef.isExtension) {
                TileEntity te2 = worldIn.getTileEntity(tef.baseXCoord, tef.baseYCoord, tef.baseZCoord);
                if (te2 instanceof TileEntityWaterWheel tef2) {
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
        if (te instanceof TileEntityWaterWheel tef) {
            if (!tef.needsRemove) {
                if (tef.isExtension) {
                    clearExtensions(worldIn, tef.baseXCoord, tef.baseYCoord, tef.baseZCoord, tef.axisMeta);
                    TileEntity te2 = worldIn.getTileEntity(tef.baseXCoord, tef.baseYCoord, tef.baseZCoord);
                    if (te2 instanceof TileEntityWaterWheel tef2) {
                        tef2.needsRemove = true;
                    }
                } else {
                    clearExtensions(worldIn, tef.xCoord, tef.yCoord, tef.zCoord, tef.axisMeta);
                }
            }
        }
    }

    public void clearExtensions(World worldObj, int x, int y, int z, int axis) {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            for (ForgeDirection direction0 : ForgeDirection.VALID_DIRECTIONS) {
                if (Utils.getAxis(direction.ordinal()) != axis && Utils.getAxis(direction0.ordinal()) != axis) {
                    for (ForgeDirection direction2 : ForgeDirection.VALID_DIRECTIONS) {
                        if (direction2 != direction && direction2 != direction.getOpposite()
                            && Utils.getAxis(direction2.ordinal()) != axis) {
                            int x2 = x + direction.offsetX + direction2.offsetX;
                            int y2 = y + direction.offsetY + direction2.offsetY;
                            int z2 = z + direction.offsetZ + direction2.offsetZ;
                            if (direction2 == direction0) {
                                x2 = x + direction.offsetX;
                                y2 = y + direction.offsetY;
                                z2 = z + direction.offsetZ;
                            }
                            TileEntity te = worldObj.getTileEntity(x2, y2, z2);
                            if (te instanceof TileEntityWaterWheel tef) {
                                tef.needsRemove = true;
                            }
                        }
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
        return new TileEntityWaterWheel();
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".waterwheel";
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getAxleRenderID();
    }
}
