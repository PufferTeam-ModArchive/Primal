package net.pufferlab.primal.blocks;

import static net.pufferlab.primal.tileentities.TileEntityQuern.*;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
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
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.QuernRecipe;
import net.pufferlab.primal.tileentities.TileEntityInventory;
import net.pufferlab.primal.tileentities.TileEntityQuern;

public class BlockQuern extends BlockContainer {

    public IIcon[] icons = new IIcon[2];

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
                if (Utils.isHandstoneTool(heldItem) || QuernRecipe.hasRecipe(heldItem)) {
                    if (Utils.isHandstoneTool(heldItem)) {
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
        if (side == 99) {
            return icons[1];
        }
        return icons[0];
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        dropItems(worldIn, x, y, z);
    }

    private void dropItems(World world, int i, int j, int k) {
        Random rando = world.rand;
        TileEntity tileEntity = world.getTileEntity(i, j, k);
        if (!(tileEntity instanceof TileEntityInventory)) return;
        TileEntityInventory inventory = (TileEntityInventory) tileEntity;
        for (int x = 0; x < inventory.getSizeInventory(); x++) {
            ItemStack item = inventory.getStackInSlot(x);
            inventory.setInventorySlotContentsUpdate(x, null);
            if (item != null && item.stackSize > 0) {
                float ri = rando.nextFloat() * 0.8F + 0.1F;
                float rj = rando.nextFloat() * 0.8F + 0.1F;
                float rk = rando.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, (i + ri), (j + rj + 0.7F), (k + rk), item.copy());
                float factor = 0.05F;
                entityItem.motionX = rando.nextGaussian() * factor;
                entityItem.motionY = rando.nextGaussian() * factor + 0.20000000298023224D;
                entityItem.motionZ = rando.nextGaussian() * factor;
                spawnEntity(world, entityItem);
                item.stackSize = 0;
            }
        }
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
