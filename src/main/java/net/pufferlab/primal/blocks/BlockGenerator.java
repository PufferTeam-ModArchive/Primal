package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityGenerator;

public class BlockGenerator extends BlockMotion {

    public IIcon[] icons = new IIcon[2];

    public BlockGenerator() {
        super(Material.wood);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        if (player.isSneaking() && worldIn.isRemote) {
            player.openGui(Primal.instance, Primal.proxy.generatorGuiID, worldIn, x, y, z);
            return true;
        }

        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon("minecraft:planks_spruce");
        icons[1] = reg.registerIcon(Primal.MODID + ":generator");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 99) {
            return icons[1];
        }
        return icons[0];
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
