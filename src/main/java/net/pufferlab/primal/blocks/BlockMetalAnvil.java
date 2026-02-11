package net.pufferlab.primal.blocks;

import static net.pufferlab.primal.tileentities.TileEntityAnvil.slotInput;
import static net.pufferlab.primal.tileentities.TileEntityAnvil.slotOutput;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.AnvilRecipe;
import net.pufferlab.primal.tileentities.TileEntityAnvil;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.MetalType;

public class BlockMetalAnvil extends BlockMetaContainer {

    public MetalType[] metalTypes;
    public IIcon[] metalIcons;

    public BlockMetalAnvil(MetalType[] metalTypes) {
        this(
            Material.iron,
            MetalType.getNames(metalTypes),
            "anvil",
            Constants.none,
            MetalType.getTools(metalTypes),
            MetalType.getLevels(metalTypes));

        this.metalTypes = metalTypes;
        this.setStepSound(soundTypeMetal);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setHasSuffix();
        this.setPrefix("block");
    }

    public BlockMetalAnvil(Material material, String[] materials, String type, String[] blacklist, String[] tools,
        int[] levels) {
        super(material, materials, type, blacklist, tools, levels);
    }

    public MetalType[] getMetalTypes() {
        return metalTypes;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        ItemStack heldItem = player.getHeldItem();
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityAnvil tef) {
            if (tef.getInventoryStack(slotInput) == null) {
                if (AnvilRecipe.hasRecipe(heldItem)) {
                    Primal.proxy.openAnvilPlanGui(player, worldIn, x, y, z);
                    return true;
                }
            } else {
                if (ItemUtils.isHammerTool(heldItem)) {
                    Primal.proxy.openAnvilWorkGui(player, worldIn, x, y, z);
                    return true;
                } else if (tef.getInventoryStack(slotOutput) == null) {
                    dropItems(worldIn, x, y, z);
                    tef.setInventorySlotContentsUpdate(slotInput);
                    tef.recipeID = "None";
                    return true;
                } else {
                    dropItems(worldIn, x, y, z);
                    tef.setInventorySlotContentsUpdate(slotOutput);
                    tef.recipeID = "None";
                    return true;
                }
            }

        }

        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);

        String[] elements = getElements();
        metalIcons = new IIcon[elements.length];
        for (int i = 0; i < elements.length; i++) {
            metalIcons[i] = register.registerIcon(Primal.MODID + ":" + elements[i] + "_anvil");
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        if (world.getTileEntity(x, y, z) instanceof TileEntityAnvil tef) {
            if (tef.facingMeta == 3 || tef.facingMeta == 1) {
                this.setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 0.75F, 0.875F);
            } else if (tef.facingMeta == 2 || tef.facingMeta == 4) {
                this.setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 0.75F, 1.0F);
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
    public IIcon getIcon(int side, int meta) {
        if (side > 99) {
            return metalIcons[side - 100];
        }
        return super.getIcon(side, meta);
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta) {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);
        dropItems(worldIn, x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityAnvil();
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getAnvilRenderID();
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
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }
}
