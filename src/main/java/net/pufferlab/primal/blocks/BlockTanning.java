package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.TanningRecipe;
import net.pufferlab.primal.tileentities.TileEntityInventory;
import net.pufferlab.primal.tileentities.TileEntityMetaFacing;
import net.pufferlab.primal.tileentities.TileEntityTanning;

public class BlockTanning extends BlockPrimal {

    public IIcon[] icons = new IIcon[2];

    public BlockTanning() {
        super(Material.wood);
        this.setHardness(1.0F);
        this.canBlockGrass = false;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":empty");
        icons[1] = reg.registerIcon(Primal.MODID + ":tanning_frame");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 99) {
            return icons[1];
        }
        return icons[1];
    }

    @Override
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        super.onBlockPlacedBy(worldIn, x, y, z, placer, itemIn);

        int metayaw = Utils.getMetaYaw(placer.rotationYaw);
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityMetaFacing tef) {
            tef.setFacingMeta(metayaw);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        ItemStack heldItem = player.getHeldItem();
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityTanning scraping) {
            if (Utils.isKnifeTool(heldItem) || TanningRecipe.hasRecipe(heldItem)) {
                if (Utils.isKnifeTool(heldItem)) {
                    boolean result = scraping.process();
                    if (result) {
                        heldItem.damageItem(10, player);
                    }
                    return result;
                }
                if (TanningRecipe.hasRecipe(heldItem)) {
                    return scraping.addInventorySlotContentsUpdate(0, player);
                }
            } else {
                if (scraping.getInventoryStack(0) != null) {
                    dropItems(worldIn, x, y, z);
                    scraping.setInventorySlotContentsUpdate(0);
                    return true;
                }

            }

        }
        return false;
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);
        dropItems(worldIn, x, y, z);
    }

    private void dropItems(World world, int i, int j, int k) {
        TileEntity tileEntity = world.getTileEntity(i, j, k);
        if (!(tileEntity instanceof TileEntityInventory)) return;
        TileEntityInventory inventory = (TileEntityInventory) tileEntity;
        for (int x = 0; x < inventory.getSizeInventory(); x++) {
            ItemStack item = inventory.getStackInSlot(x);
            inventory.setInventorySlotContentsUpdate(x, null);
            if (item != null && item.stackSize > 0) {
                EntityItem entityItem = new EntityItem(world, (i), (j + 0.5F), (k), item.copy());
                spawnEntity(world, entityItem);
                item.stackSize = 0;
            }
        }
    }

    public void spawnEntity(World world, Entity entityItem) {
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity) entityItem);
        }
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".tanning_frame";
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTanning();
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getTanningRenderID();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
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
    public boolean hasTileEntity(int metadata) {
        return true;
    }
}
