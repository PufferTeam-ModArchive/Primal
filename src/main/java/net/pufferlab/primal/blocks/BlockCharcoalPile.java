package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityCharcoalPile;
import net.pufferlab.primal.tileentities.TileEntityInventory;

public class BlockCharcoalPile extends BlockPile {

    private IIcon[] icons = new IIcon[1];

    public BlockCharcoalPile() {
        super(Material.sand);
        this.setStepSound(SoundTypeCustom.soundTypeCharcoal);
        this.setHardness(0.5F);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F + (0.125F * metadata), 1.0F);
    }

    @Override
    public AxisAlignedBB getBlockBoundsNextState(IBlockAccess world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.125F + (0.125F * (metadata + 1)), 1.0F);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":charcoal_pile");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icons[0];
    }

    @Override
    public String getItemOre() {
        return "charcoal";
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
        return Primal.proxy.getCharcoalPileRenderID();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCharcoalPile();
    }
}
