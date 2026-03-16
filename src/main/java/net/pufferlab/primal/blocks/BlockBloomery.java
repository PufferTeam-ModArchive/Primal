package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityBloomery;

public class BlockBloomery extends BlockContainerPrimal {

    public IIcon bloomery;
    public IIcon particle;

    public static final int iconBloomery = 99;

    public BlockBloomery() {
        super(Material.rock);
        this.setStepSound(soundTypeStone);
        this.setHardness(0.8F);
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".bloomery";
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        bloomery = reg.registerIcon(Primal.MODID + ":bloomery");
        particle = reg.registerIcon(Primal.MODID + ":fire_bricks");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconBloomery) {
            return bloomery;
        }
        return particle;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBloomery();
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getBloomeryRenderID();
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
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }
}
