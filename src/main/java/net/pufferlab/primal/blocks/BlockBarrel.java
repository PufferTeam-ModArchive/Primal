package net.pufferlab.primal.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.BarrelRecipe;
import net.pufferlab.primal.tileentities.TileEntityBarrel;
import net.pufferlab.primal.tileentities.TileEntityInventory;

public class BlockBarrel extends BlockContainer {

    public IIcon[] icons = new IIcon[2];

    public BlockBarrel() {
        super(Material.wood);
        this.setStepSound(soundTypeWood);
        this.setHardness(1.0F);
        this.canBlockGrass = false;
        this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityBarrel tef) {
            if (tef.isFloorBarrel) {
                if (tef.facingMeta == 2 || tef.facingMeta == 4) {
                    this.setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 0.75F, 0.875F);
                } else if (tef.facingMeta == 3 || tef.facingMeta == 1) {
                    this.setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 0.75F, 1.0F);
                }
            } else {
                this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
            }
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
        if (te instanceof TileEntityBarrel tef) {
            if (player.isSneaking()) {
                tef.setOpen(!tef.isOpen);
            } else {
                if (heldItem != null && BarrelRecipe.hasRecipe(heldItem, tef.getFluidStack())) {
                    return tef.addInventorySlotContentsUpdateWhole(0, player);
                } else {
                    if (tef.getInventoryStack(1) != null) {
                        dropItems(worldIn, x, y, z);
                        tef.setInventorySlotContentsUpdate(1);
                        return true;
                    }
                    if (tef.getInventoryStack(0) != null) {
                        dropItems(worldIn, x, y, z);
                        tef.setInventorySlotContentsUpdate(0);
                        return true;
                    }
                }
            }

        }
        return true;
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
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":barrel");
        icons[1] = reg.registerIcon(Primal.MODID + ":barrel_top");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 99) {
            return icons[0];
        }
        return icons[1];
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".barrel";
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        ItemStack heldItem = placer.getHeldItem();
        NBTTagCompound tagCompound = heldItem.getTagCompound();
        if (tagCompound != null) {
            TileEntity te = worldIn.getTileEntity(x, y, z);
            if (te instanceof TileEntityBarrel tef) {
                tef.readFromNBTInventory(tagCompound);
            }
        }

        int metayaw = Utils.getMetaYaw(placer.rotationYaw);
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityBarrel tef) {
            tef.setFacingMeta(metayaw);
            if (placer.isSneaking()) {
                tef.setFloorBarrel(true);
            } else {
                tef.setFloorBarrel(false);
            }
        }

    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        ItemStack item = new ItemStack(this, 1, 0);
        NBTTagCompound tagCompound = new NBTTagCompound();
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityBarrel tef) {
            tef.writeToNBTInventory(tagCompound);
            item.setTagCompound(tagCompound);
        }
        dropItemStack(worldIn, x, y, z, item);
    }

    @Override
    protected void dropBlockAsItem(World worldIn, int x, int y, int z, ItemStack itemIn) {}

    public void dropItemStack(World world, int x, int y, int z, ItemStack item) {
        if (item != null && item.stackSize > 0) {
            EntityItem entityItem = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, item.copy());
            entityItem.motionX = 0.0D;
            entityItem.motionY = 0.0D;
            entityItem.motionZ = 0.0D;
            spawnEntity(world, entityItem);
            item.stackSize = 0;
        }
    }

    public void spawnEntity(World world, Entity entityItem) {
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity) entityItem);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBarrel();
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getBarrelRenderID();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        return true;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }
}
