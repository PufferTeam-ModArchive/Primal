package net.pufferlab.primal.blocks;

import static net.pufferlab.primal.tileentities.TileEntityCast.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.CastingRecipe;
import net.pufferlab.primal.tileentities.TileEntityCast;
import net.pufferlab.primal.tileentities.TileEntityInventory;
import net.pufferlab.primal.utils.FluidUtils;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.MetalType;
import net.pufferlab.primal.world.GlobalTickingData;

public class BlockCast extends BlockContainerPrimal {

    public IIcon[] icons = new IIcon[2];
    public IIcon[] moldIcons;
    public String[] molds;

    public static final int iconCast = 99;

    public BlockCast() {
        super(Material.clay);

        this.setHardness(0.2F);
        super.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.125F, 0.875F);
        this.canBlockGrass = false;

        molds = Constants.moldItems;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        ItemStack heldItem = player.getHeldItem();
        if (te instanceof TileEntityCast tef) {
            if (heldItem != null) {
                if (heldItem.getItem() == Item.getItemFromBlock(Registry.crucible)) {
                    if (HeatUtils.hasImpl(heldItem)) {
                        FluidStack stack = FluidUtils.getFluidTankFromNBT(heldItem.getTagCompound());
                        MetalType metal = MetalType.getMetalFromFluid(stack);
                        if (metal != null) {
                            int temp = HeatUtils.getInterpolatedTemperature(
                                GlobalTickingData.getTickTime(worldIn),
                                heldItem.getTagCompound());
                            if (temp > metal.meltingTemperature) {
                                FluidStack fluidInfo = FluidUtils.getFluidTankFromNBT(heldItem.getTagCompound());
                                CastingRecipe recipe = CastingRecipe
                                    .getRecipe(tef.getInventoryStack(slotCast), fluidInfo);
                                if (fluidInfo != null && recipe != null) {
                                    tef.tank.setCapacity(recipe.input.amount);
                                    if (tef.fill(ForgeDirection.getOrientation(side), fluidInfo, false) > 0) {
                                        FluidStack fluid = FluidUtils
                                            .drainFluidTankFromNBT(heldItem.getTagCompound(), recipe.input.amount);
                                        if (tef.getFluidStack() == null && fluid != null) {
                                            tef.temperature = temp;
                                            tef.fill(ForgeDirection.getOrientation(side), fluid, true);
                                        }
                                    }

                                }
                            }
                        }
                    }
                    return true;
                }
            }
            if (FluidUtils.isFluidContainer(heldItem)) {
                return true;
            } else {
                if (tef.getInventoryStack(slotOutputSmall) != null) {
                    return addOrRemoveItem(worldIn, x, y, z, player, tef, slotOutputSmall, heldItem);
                } else if (tef.getInventoryStack(slotOutput) != null) {
                    return addOrRemoveItem(worldIn, x, y, z, player, tef, slotOutput, heldItem);
                } else {
                    return addOrRemoveItem(worldIn, x, y, z, player, tef, slotCast, heldItem);
                }
            }
        }
        return false;
    }

    public boolean addOrRemoveItem(World world, int x, int y, int z, EntityPlayer player, TileEntityInventory tef,
        int index, ItemStack heldItem) {
        if (tef.getInventoryStack(index) == null) {
            if (!Utils.containsOreDict(heldItem, "mold")) return false;
            return tef.addInventorySlotContentsUpdate(index, player);
        } else {
            dropItem(world, x, y, z, index);
            tef.setInventorySlotContentsUpdate(index);
            if (tef.getInventoryStack(slotCast) == null) {
                world.setBlockToAir(x, y, z);
            }
            return true;
        }
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(worldIn, x, y, z, neighbor);
        Block block = worldIn.getBlock(x, y, z);
        if (!worldIn.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
            worldIn.setBlockToAir(x, y, z);
            block.onBlockPreDestroy(worldIn, x, y, z, worldIn.getBlockMetadata(x, y, z));
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(Primal.MODID + ":ceramic");
        icons[1] = reg.registerIcon(Primal.MODID + ":mold");

        moldIcons = new IIcon[molds.length];
        for (int i = 0; i < molds.length; i++) {
            moldIcons[i] = reg.registerIcon(Primal.MODID + ":items/" + molds[i]);
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconCast) {
            return icons[1];
        }
        if (side > 99) {
            return moldIcons[side - 100];
        }
        return moldIcons[0];
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);

        dropItems(worldIn, x, y, z, 0);
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return false;
    }

    @Override
    public Item getItem(World worldIn, int x, int y, int z) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityCast tef) {
            return tef.getLastItem();
        }
        return super.getItem(worldIn, x, y, z);
    }

    @Override
    public int getDamageValue(World worldIn, int x, int y, int z) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityCast tef) {
            return tef.getLastItemMeta();
        }
        return super.getDamageValue(worldIn, x, y, z);
    }

    @Override
    protected void dropBlockAsItem(World worldIn, int x, int y, int z, ItemStack itemIn) {}

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCast();
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
        return Primal.proxy.getCastRenderID();
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return null;
    }
}
