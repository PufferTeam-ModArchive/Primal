package net.pufferlab.primal.blocks;

import java.util.ArrayList;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockCutSlabVertical;
import net.pufferlab.primal.tileentities.TileEntityCut;
import net.pufferlab.primal.tileentities.TileEntityCutDouble;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.CutUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

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
    public List<AxisAlignedBB> getBounds(World world, int x, int y, int z, float hitX, float hitY, float hitZ,
        BoundsType type) {
        List<AxisAlignedBB> list = new ArrayList<>();
        if (!isFull) return null;
        int meta = world.getBlockMetadata(x, y, z);
        if (type == BoundsType.rendered) {
            if (meta == 1 || meta == 3) {
                if (hitZ < 0.5F) {
                    list.add(AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F));
                } else {
                    list.add(AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F));
                }
            } else if (meta == 0 || meta == 2) {
                if (hitX < 0.5F) {
                    list.add(AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F));
                } else {
                    list.add(AxisAlignedBB.getBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F));
                }
            }
        }
        return list;
    }

    @Override
    public boolean renderDefaultBounds() {
        if (!isFull) {
            return true;
        }
        return false;
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
    public ISimpleBlockRenderingHandler getRenderer() {
        return Primal.proxy.getSlabVerticalRenderer();
    }

    @Override
    public int getRenderType() {
        return getRenderId();
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
        if (isFull) {
            MovingObjectPosition mop = BlockUtils.getMovingObjectPositionFromPlayer(worldIn, player, false);
            if (mop != null) {
                int materialMeta = getMaterialMeta(worldIn, x, y, z);
                int materialMeta2 = getMaterialMeta2(worldIn, x, y, z);
                float hitX = (float) (mop.hitVec.xCoord - mop.blockX);
                float hitZ = (float) (mop.hitVec.zCoord - mop.blockZ);
                if (meta == 0 || meta == 2) {
                    if (hitX < 0.5F) {
                        if (meta == 2) {
                            worldIn.setBlock(x, y, z, this.slabBlock, 0, 2);
                            Primal.proxy.packet.sendMaterialPacket(worldIn, x, y, z, this.slabBlock, materialMeta2);
                        } else {
                            worldIn.setBlock(x, y, z, this.slabBlock, 0, 2);
                            Primal.proxy.packet.sendMaterialPacket(worldIn, x, y, z, this.slabBlock, materialMeta);
                        }
                    } else {
                        worldIn.setBlock(x, y, z, this.slabBlock, 1, 2);
                        Primal.proxy.packet.sendMaterialPacket(worldIn, x, y, z, this.slabBlock, materialMeta);
                    }
                }
                if (meta == 1 || meta == 3) {
                    if (hitZ < 0.5F) {
                        if (meta == 3) {
                            worldIn.setBlock(x, y, z, this.slabBlock, 2, 2);
                            Primal.proxy.packet.sendMaterialPacket(worldIn, x, y, z, this.slabBlock, materialMeta2);
                        } else {
                            worldIn.setBlock(x, y, z, this.slabBlock, 2, 2);
                            Primal.proxy.packet.sendMaterialPacket(worldIn, x, y, z, this.slabBlock, materialMeta);
                        }
                    } else {
                        worldIn.setBlock(x, y, z, this.slabBlock, 3, 2);
                        Primal.proxy.packet.sendMaterialPacket(worldIn, x, y, z, this.slabBlock, materialMeta);
                    }
                }
                Primal.proxy.packet.sendChunkUpdate(worldIn);
                if (player.capabilities.isCreativeMode) return;
                if (meta == 0 || meta == 2) {
                    if (hitX < 0.5F) {
                        dropBlockAsItem(worldIn, x, y, z, new ItemStack(this.slabBlock, 1, materialMeta));
                    } else {
                        if (meta == 2) {
                            dropBlockAsItem(worldIn, x, y, z, new ItemStack(this.slabBlock, 1, materialMeta2));
                        } else {
                            dropBlockAsItem(worldIn, x, y, z, new ItemStack(this.slabBlock, 1, materialMeta));
                        }
                    }
                }
                if (meta == 1 || meta == 3) {
                    if (hitZ < 0.5F) {
                        dropBlockAsItem(worldIn, x, y, z, new ItemStack(this.slabBlock, 1, materialMeta));
                    } else {
                        if (meta == 3) {
                            dropBlockAsItem(worldIn, x, y, z, new ItemStack(this.slabBlock, 1, materialMeta2));
                        } else {
                            dropBlockAsItem(worldIn, x, y, z, new ItemStack(this.slabBlock, 1, materialMeta));
                        }
                    }
                }
            }
        }
        if (player.capabilities.isCreativeMode) return;
        if (!isFull) {
            dropBlockAsItem(worldIn, x, y, z, new ItemStack(this, 1, getDamageValue(worldIn, x, y, z)));
        }
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        if (isFull) {
            return true;
        }
        return removedByPlayer(world, player, x, y, z);
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

    /**
     * Tile Entity Functions
     * Necessary for the block to have a Tile Entity correctly, as it doesn't extend BlockContainer
     **/
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
}
