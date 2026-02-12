package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockCutStairs;
import net.pufferlab.primal.tileentities.TileEntityCut;
import net.pufferlab.primal.utils.CutUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCutStairs extends BlockStairs implements ITileEntityProvider, IPrimalBlock {

    public BlockCutStairs(Block block) {
        super(block, 0);
        this.isBlockContainer = true;
        this.useNeighborBrightness = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return CutUtils.getIcon(side, meta);
    }

    public String func_150002_b(int id) {
        return CutUtils.getUnlocalizedName(id) + "_stairs";
    }

    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
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
    public Item getItem(World worldIn, int x, int y, int z) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockCutStairs.class;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }

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
