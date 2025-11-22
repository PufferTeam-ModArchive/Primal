package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityInventory;
import net.pufferlab.primal.tileentities.TileEntityLogPile;

public class BlockLogPile extends BlockPile {

    private IIcon[] icons = new IIcon[3];

    public BlockLogPile() {
        super(Material.wood);
        this.setStepSound(soundTypeWood);
        this.setHardness(2.5F);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":log_pile");
        icons[1] = reg.registerIcon(Primal.MODID + ":log_pile_2");
        icons[2] = reg.registerIcon(Primal.MODID + ":log_pile_front");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icons[1];
    }

    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        if (side == 0 || side == 1) {
            return icons[0];
        }
        if (side == 2 || side == 3) {
            return icons[2];
        }
        if (side == 4 || side == 5) {
            return icons[1];
        }
        /*
         * TileEntity te = worldIn.getTileEntity(x, y, z);
         * if(te instanceof TileEntityMetaFacing tef) {
         * if(tef.facingMeta == 1 || tef.facingMeta == 3) {
         * if(side == 2 || side == 3) {
         * return icons[1];
         * }
         * }
         * if(tef.facingMeta == 2 || tef.facingMeta == 4) {
         * if(side == 4 || side == 5) {
         * return icons[1];
         * }
         * }
         * }
         */
        return icons[2];
    }

    @Override
    public String getItemOre() {
        return "firewood";
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        boolean result = super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
        for (int i = 0; i < 10; i++) {
            TileEntity te = world.getTileEntity(x, y + i, z);
            if (te instanceof TileEntityInventory tef) {
                tef.syncMetaWithAmount();
            }
        }

        return result;
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getLogPileRenderID();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityLogPile();
    }
}
