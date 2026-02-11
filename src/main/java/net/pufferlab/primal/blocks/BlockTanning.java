package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.TanningRecipe;
import net.pufferlab.primal.tileentities.TileEntityTanning;
import net.pufferlab.primal.utils.ItemUtils;

public class BlockTanning extends BlockContainerPrimal {

    public IIcon[] icons = new IIcon[2];

    public static final int iconTanning = 99;

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
        if (side == iconTanning) {
            return icons[1];
        }
        return icons[1];
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        ItemStack heldItem = player.getHeldItem();
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityTanning scraping) {
            if (ItemUtils.isKnifeTool(heldItem) || TanningRecipe.hasRecipe(heldItem)) {
                if (ItemUtils.isKnifeTool(heldItem)) {
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
