package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityGenerator;

public class BlockGenerator extends BlockMotion {

    public IIcon[] icons = new IIcon[1];

    public BlockGenerator() {
        super(Material.wood);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":generator");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 99) {
            return icons[0];
        }
        return null;
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
    public int getRenderType() {
        return Primal.proxy.getGeneratorRenderID();
    }
}
