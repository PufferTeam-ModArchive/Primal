package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.items.itemblocks.ItemBlockWindmill;
import net.pufferlab.primal.tileentities.TileEntityWindmill;

public class BlockWindmill extends BlockMotion {

    public IIcon[] icons = new IIcon[1];

    public BlockWindmill() {
        super(Material.wood);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
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
        return new TileEntityWindmill();
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".windmill";
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getAxleRenderID();
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockWindmill.class;
    }
}
