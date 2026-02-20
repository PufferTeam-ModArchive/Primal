package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockMeta;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaPath extends BlockDirt implements IPrimalBlock, IMetaBlock {

    protected String[] elements;
    protected String[] elementsBlacklist;
    protected IIcon[] icons;
    public IIcon[] pathIcons;
    public static final int pathSide = 0;
    public static final int pathTop = 1;
    public static final int empty = 2;
    protected String name;
    protected String[] elementsTextures;
    public boolean hasSuffix;
    public BlockMetaPath blockTexture;

    public int renderPass;
    public int renderPass2;
    public boolean isInventory;

    public BlockMetaPath(Material material, String[] materials, String type, String[] blacklist, String[] tools,
        int[] levels) {
        super();
        elements = materials;
        name = type;
        elementsBlacklist = blacklist;
        blockTexture = this;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        this.useNeighborBrightness = true;
        this.setHarvestLevel("shovel", 0);
    }

    public BlockMetaPath(Material material, String[] materials, String type) {
        this(material, materials, type, Constants.none, null, null);
    }

    public BlockMetaPath() {
        super();
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        this.useNeighborBrightness = true;
    }

    public BlockMetaPath setTextureOverride(String[] elementsTextures) {
        this.elementsTextures = elementsTextures;
        return this;
    }

    public BlockMetaPath setBlacklist(String[] blacklist) {
        this.elementsBlacklist = blacklist;
        return this;
    }

    public BlockMetaPath setHasSuffix() {
        this.hasSuffix = true;
        return this;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(
            (double) (x + 0),
            (double) (y + 0),
            (double) (z + 0),
            (double) (x + 1),
            (double) (y + 1),
            (double) (z + 1));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        for (int i = 0; i < elements.length; i++) {
            icons[i] = register.registerIcon(Primal.MODID + ":" + elements[i] + "_dirt");
        }

        this.pathIcons = new IIcon[3];

        this.pathIcons[pathSide] = register.registerIcon(Primal.MODID + ":dirt_path_side");
        this.pathIcons[pathTop] = register.registerIcon(Primal.MODID + ":dirt_path_top");
        this.pathIcons[empty] = register.registerIcon(Primal.MODID + ":empty");
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < elements.length; i++) {
            if (!Utils.contains(elementsBlacklist, elements[i])) {
                list.add(new ItemStack(item, 1, i));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        return getIcon(worldIn, x, y, z, side, meta);
    }

    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side, int meta) {
        BlockMetaPath inc = this.blockTexture;
        if (getPass() == 0) {
            if (side == 1) {
                return inc.pathIcons[empty];
            } else {
                return inc.icons[meta];
            }
        } else {
            if (side == 1) {
                return inc.pathIcons[pathTop];
            } else if (side == 0) {
                return inc.pathIcons[empty];
            } else {
                return inc.pathIcons[pathSide];
            }
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        if (getPass() == 0 && side == 1) {
            return false;
        }
        if (getPass() == 1 && side == 0) {
            return false;
        }
        return super.shouldSideBeRendered(worldIn, x, y, z, side);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        BlockMetaPath inc = this.blockTexture;
        if (isInventory) {
            if (getPass() == 0) {
                if (side == 1) {
                    return inc.pathIcons[empty];
                } else {
                    return inc.icons[meta];
                }
            } else {
                if (side == 1) {
                    return inc.pathIcons[pathTop];
                } else if (side == 0) {
                    return inc.pathIcons[empty];
                } else {
                    return inc.pathIcons[pathSide];
                }
            }
        } else {
            return inc.icons[meta];
        }
    }

    public int getPass() {
        if (this.isInventory) {
            return renderPass2;
        } else {
            return renderPass;
        }
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
    public boolean hasOverlay() {
        return true;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    public String[] getElements() {
        return elements;
    }

    public String[] getElementsBlacklist() {
        return elementsBlacklist;
    }

    public String getElementName() {
        return name;
    }

    public boolean hasSuffix() {
        return this.hasSuffix;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockMeta.class;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTab;
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getPathRenderID();
    }
}
