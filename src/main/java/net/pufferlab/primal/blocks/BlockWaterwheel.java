package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
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
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        if (te instanceof TileEntityWaterwheel tef) {
            int x2 = -(tef.xCoord - tef.baseXCoord);
            int y2 = -(tef.yCoord - tef.baseYCoord);
            int z2 = -(tef.zCoord - tef.baseZCoord);
            if (!tef.isExtension) {
                x2 = 0;
                y2 = 0;
                z2 = 0;
            }
            if (tef.axisMeta == 0) {
                this.setBlockBounds(-1.0F + x2, 0.0F + y2, -1.0F + z2, 2.0F + x2, 1.0F + y2, 2.0F + z2);
            } else if (tef.axisMeta == 1) {
                this.setBlockBounds(-1.0F + x2, -1.0F + y2, 0.0F + z2, 2.0F + x2, 2.0F + y2, 1.0F + z2);
            } else if (tef.axisMeta == 2) {
                this.setBlockBounds(0.0F + x2, -1.0F + y2, -1.0F + z2, 1.0F + x2, 2.0F + y2, 2.0F + z2);
            }
        }
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask,
        List<AxisAlignedBB> list, Entity collider) {
        this.setBlockBoundsBasedOnState(worldIn, x, y, z);
        super.addCollisionBoxesToList(worldIn, x, y, z, mask, list, collider);
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
                    if (!(xf == 0 && zf == 0)) {
                        tef.scheduleRemoval();
                    }
                    Primal.proxy.effect.playAuxFX(world, x2, y2, z2, tef.getBlockType(), tef.getBlockMetadata());
                }
            }
        }
    }

    @Override
    public byte getBlockParticleAmount() {
        return 2;
    }

    @Override
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        return true;
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
