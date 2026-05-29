package net.pufferlab.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockPipe extends BlockPrimal {

    public IIcon pipe;
    public static final int iconPipe = 99;

    public BlockPipe() {
        super(Material.iron);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        super.registerBlockIcons(reg);

        pipe = reg.registerIcon(Primal.MODID + ":pipe");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconPipe) {
            return pipe;
        }
        return super.getIcon(side, meta);
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".pipe";
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);
        worldIn.markBlockForUpdate(x, y, z);
    }

    @Override
    public ISimpleBlockRenderingHandler getRenderer() {
        return Primal.proxy.getPipeRenderer();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
