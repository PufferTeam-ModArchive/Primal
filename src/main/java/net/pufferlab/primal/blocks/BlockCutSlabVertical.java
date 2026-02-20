package net.pufferlab.primal.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockCutSlabVertical;
import net.pufferlab.primal.tileentities.TileEntityCut;
import net.pufferlab.primal.tileentities.TileEntityCutDouble;
import net.pufferlab.primal.utils.CutUtils;

public class BlockCutSlabVertical extends BlockSlabVertical implements ITileEntityProvider, IPrimalBlock, ICutBlock {

    private final Block field_150149_b;

    public Block slabBlock;
    public Block fullBlock;
    public boolean isFull;

    public BlockCutSlabVertical(Block block, boolean p_i45431_1_) {
        super(p_i45431_1_, block.getMaterial());
        this.field_150149_b = block;
        this.setHardness(block.blockHardness);
        this.setResistance(block.blockResistance / 3.0F);
        this.setStepSound(block.stepSound);
        this.isBlockContainer = true;
        this.useNeighborBrightness = true;
        this.isFull = p_i45431_1_;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return CutUtils.getIcon(side, meta);
    }

    @Override
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        if (field_150004_a && (meta == 2 || meta == 3)) {
            if (side == 0 || side == 2 || side == 4) {
                int materialMeta = getMaterialMeta(worldIn, x, y, z);
                return CutUtils.getIcon(side, materialMeta);
            } else {
                int materialMeta2 = getMaterialMeta2(worldIn, x, y, z);
                return CutUtils.getIcon(side, materialMeta2);
            }
        } else {
            if (this.field_150004_a && (meta & 8) != 0) {
                side = 1;
            }

            int materialMeta = getMaterialMeta(worldIn, x, y, z);
            return CutUtils.getIcon(side, materialMeta);
        }
    }

    @Override
    protected void dropBlockAsItem(World worldIn, int x, int y, int z, ItemStack itemIn) {
        super.dropBlockAsItem(worldIn, x, y, z, itemIn);
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    public String func_150002_b(int id) {
        return CutUtils.getUnlocalizedName(id) + "_slab";
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        if (field_150004_a) return;
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
        return ItemBlockCutSlabVertical.class;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        if (field_150004_a) {
            return null;
        }
        return Registry.creativeTabWorld;
    }

    @Override
    public boolean canRegister() {
        return Config.strataStoneTypes.getBoolean();
    }

    @Override
    public Item getItem(World worldIn, int x, int y, int z) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getVerticalSlabRenderID();
    }

    @Override
    public boolean useWorldIcon() {
        return true;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return null;
    }

    @Override
    public void onBlockHarvested(World worldIn, int x, int y, int z, int meta, EntityPlayer player) {
        if (player.capabilities.isCreativeMode) return;
        if (field_150004_a) {
            if (meta == 2 || meta == 3) {
                dropBlockAsItem(worldIn, x, y, z, new ItemStack(this.slabBlock, 1, getDamageValue(worldIn, x, y, z)));
                dropBlockAsItem(worldIn, x, y, z, new ItemStack(this.slabBlock, 1, getMaterialMeta2(worldIn, x, y, z)));
            } else {
                dropBlockAsItem(worldIn, x, y, z, new ItemStack(this.slabBlock, 2, getDamageValue(worldIn, x, y, z)));
            }
        } else {
            dropBlockAsItem(worldIn, x, y, z, new ItemStack(this, 1, getDamageValue(worldIn, x, y, z)));
        }
    }

    // Tile Entity Provider Function
    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        int meta = worldIn.getBlockMetadata(x, y, z);
        worldIn.setTileEntity(x, y, z, this.createNewTileEntity(worldIn, meta));
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
        if (field_150004_a && (meta == 2 || meta == 3)) {
            return new TileEntityCutDouble();
        }
        return new TileEntityCut();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}
