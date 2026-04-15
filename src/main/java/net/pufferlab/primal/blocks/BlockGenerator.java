package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityGenerator;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockGenerator extends BlockMotion {

    public IIcon generator;
    public IIcon particle;

    public static final int iconGenerator = 99;

    public BlockGenerator() {
        super(Material.wood);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        if (player.isSneaking() && worldIn.isRemote) {
            Primal.proxy.openGeneratorGui(player, worldIn, x, y, z);
            return true;
        }

        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        super.setBlockBoundsBasedOnState(worldIn, x, y, z);
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityGenerator tef) {
            float b0 = 0.25F;
            float b1 = 1 - b0;
            if (tef.axisMeta == 0) {
                this.setBlockBounds(b0, 0.0F, b0, b1, 1.0F, b1);
            } else if (tef.axisMeta == 1) {
                this.setBlockBounds(b0, b0, 0.0F, b1, b1, 1.0F);
            } else if (tef.axisMeta == 2) {
                this.setBlockBounds(0.0F, b0, b0, 1.0F, b1, b1);
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
    public void registerBlockIcons(IIconRegister reg) {
        generator = reg.registerIcon(Primal.MODID + ":generator");
        particle = reg.registerIcon("minecraft:planks_spruce");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconGenerator) {
            return generator;
        }
        return particle;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".generator";
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGenerator();
    }

    @Override
    public ISimpleBlockRenderingHandler getRenderer() {
        return Primal.proxy.generatorRenderer;
    }
}
