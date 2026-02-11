package net.pufferlab.primal.blocks;

import static net.pufferlab.primal.tileentities.TileEntityQuern.*;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.QuernRecipe;
import net.pufferlab.primal.tileentities.TileEntityQuern;
import net.pufferlab.primal.utils.ItemUtils;

public class BlockQuern extends BlockContainerPrimal {

    public IIcon[] icons = new IIcon[2];

    public static final int iconQuern = 99;

    public BlockQuern() {
        super(Material.rock);

        this.setHardness(0.8F);
        this.canBlockGrass = false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (metadata == 0) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.6875F, 1.0F);
        } else {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask,
        List<AxisAlignedBB> list, Entity collider) {
        this.setBlockBoundsBasedOnState(worldIn, x, y, z);
        super.addCollisionBoxesToList(worldIn, x, y, z, mask, list, collider);
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        ItemStack heldItem = player.getHeldItem();
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityQuern quern) {
            if (!player.isSneaking()) {
                if (ItemUtils.isHandstoneTool(heldItem) || QuernRecipe.hasRecipe(heldItem)) {
                    if (ItemUtils.isHandstoneTool(heldItem)) {
                        worldIn.setBlockMetadataWithNotify(x, y, z, 1, 2);
                        return quern.addInventorySlotContentsUpdate(slotHandstone, player);
                    }
                    int meta = worldIn.getBlockMetadata(x, y, z);
                    if (meta == 1) {
                        if (QuernRecipe.hasRecipe(heldItem)) {
                            return quern.addInventorySlotContentsUpdateWhole(slotInput, player);
                        }
                    }
                } else {
                    if (quern.getInventoryStack(slotOutput) != null) {
                        dropItemStack(worldIn, x, y, z, quern.getInventoryStack(slotOutput));
                        quern.setInventorySlotContentsUpdate(slotOutput);
                        return true;
                    }
                    if (quern.getInventoryStack(slotInput) != null) {
                        dropItemStack(worldIn, x, y, z, quern.getInventoryStack(slotInput));
                        quern.setInventorySlotContentsUpdate(slotInput);
                        return true;
                    }
                    if (quern.getInventoryStack(slotHandstone) != null) {
                        worldIn.setBlockMetadataWithNotify(x, y, z, 0, 2);
                        dropItemStack(worldIn, x, y, z, quern.getInventoryStack(slotHandstone));
                        quern.setInventorySlotContentsUpdate(slotHandstone);
                        return true;
                    }
                }
            } else {
                quern.markDirty();
                quern.addSpeed();
                return true;
            }
        }
        return false;
    }

    public void dropItemStack(World world, int x, int y, int z, ItemStack item) {
        if (item != null && item.stackSize > 0) {
            EntityItem entityItem = new EntityItem(world, x + 0.5, y + 1.2, z + 0.5, item.copy());
            entityItem.motionX = 0.0D;
            entityItem.motionY = 0.0D;
            entityItem.motionZ = 0.0D;
            spawnEntity(world, entityItem);
            item.stackSize = 0;
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon("minecraft:stone");
        icons[1] = reg.registerIcon(Primal.MODID + ":quern");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconQuern) {
            return icons[1];
        }
        return icons[0];
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        dropItems(worldIn, x, y, z);
    }

    @Override
    public int getDamageValue(World worldIn, int x, int y, int z) {
        return worldIn.getBlockMetadata(x, y, z);
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return false;
    }

    public void spawnEntity(World world, Entity entityItem) {
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity) entityItem);
        }
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".quern";
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityQuern();
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
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getQuernRenderID();
    }
}
