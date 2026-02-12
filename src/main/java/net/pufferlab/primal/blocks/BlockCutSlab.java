package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockCutSlab;
import net.pufferlab.primal.tileentities.TileEntityCut;
import net.pufferlab.primal.utils.CutUtils;

public class BlockCutSlab extends BlockSlab implements ITileEntityProvider, IPrimalBlock {

    public BlockCutSlab(Material material, boolean p_i45431_1_) {
        super(p_i45431_1_, material);
        this.isBlockContainer = true;
        this.useNeighborBrightness = true;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return CutUtils.getIcon(side, meta);
    }

    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        if (this.field_150004_a && (worldIn.getBlockMetadata(x, y, z) & 8) != 0) {
            side = 1;
        }

        int materialMeta = getMaterialMeta(worldIn, x, y, z);
        return CutUtils.getIcon(side, materialMeta);
    }

    public int getMaterialMeta(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCut tef) {
            return tef.getMaterialMeta();
        }
        return 0;
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public int onBlockPlaced(World worldIn, int x, int y, int z, int side, float subX, float subY, float subZ,
        int meta) {
        meta = 0;
        return this.field_150004_a ? meta : (side != 0 && (side == 1 || (double) subY <= 0.5D) ? meta : meta | 8);
    }

    @Override
    public String func_150002_b(int id) {
        return CutUtils.getUnlocalizedName(id) + "_slab";
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < CutUtils.getSize(); i++) {
            list.add(new ItemStack(this, 0, i));
        }
    }

    @Override
    public int getDamageValue(World worldIn, int x, int y, int z) {
        return getMaterialMeta(worldIn, x, y, z);
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockCutSlab.class;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }

    @Override
    public Item getItem(World worldIn, int x, int y, int z) {
        return Item.getItemFromBlock(this);
    }

    // Tile Entity Provider Function
    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        worldIn.setTileEntity(x, y, z, this.createNewTileEntity(worldIn, 0));
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {
        super.breakBlock(worldIn, x, y, z, blockBroken, meta);
        worldIn.removeTileEntity(x, y, z);
    }

    @Override
    public boolean onBlockEventReceived(World worldIn, int x, int y, int z, int eventId, int eventData) {
        super.onBlockEventReceived(worldIn, x, y, z, eventId, eventData);
        TileEntity tileentity = worldIn.getTileEntity(x, y, z);
        return tileentity != null ? tileentity.receiveClientEvent(eventId, eventData) : false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCut();
    }
}
