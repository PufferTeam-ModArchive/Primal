package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.itemblocks.ItemBlockMeta;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaGrass extends BlockGrass implements IPrimalBlock, IMetaBlock, IGrowable {

    protected String[] elements;
    protected String[] elementsBlacklist;
    protected IIcon[] icons;
    public IIcon[] grassIcons;
    public static final int grassSide = 0;
    public static final int grassTop = 1;
    public static final int grassSideSnowed = 2;
    public static final int grassSideOverlay = 3;
    public static final int empty = 4;
    protected String name;
    protected String[] elementsTextures;
    public boolean hasSuffix;
    public BlockMetaGrass blockTexture;

    public int renderPass;
    public int renderPass2;
    public boolean isInventory;

    public BlockMetaGrass(Material material, String[] materials, String type, String[] blacklist, String[] tools,
        int[] levels) {
        super();
        elements = materials;
        name = type;
        elementsBlacklist = blacklist;
        blockTexture = this;

        for (int i = 0; i < elements.length; i++) {
            this.setHarvestLevel("shovel", 0, i);
        }

    }

    public BlockMetaGrass(Material material, String[] materials, String type) {
        this(material, materials, type, Constants.none, null, null);
    }

    public BlockMetaGrass() {
        super();
    }

    public BlockMetaGrass setTextureOverride(String[] elementsTextures) {
        this.elementsTextures = elementsTextures;
        return this;
    }

    public BlockMetaGrass setBlacklist(String[] blacklist) {
        this.elementsBlacklist = blacklist;
        return this;
    }

    public BlockMetaGrass setHasSuffix() {
        this.hasSuffix = true;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        for (int i = 0; i < elements.length; i++) {
            icons[i] = register.registerIcon(Primal.MODID + ":" + elements[i] + "_dirt");
        }

        this.grassIcons = new IIcon[5];

        this.grassIcons[grassSide] = register.registerIcon("grass_side");
        this.grassIcons[grassTop] = register.registerIcon("grass_top");
        this.grassIcons[grassSideSnowed] = register.registerIcon(Primal.MODID + ":grass_side_snowed_overlay");
        this.grassIcons[grassSideOverlay] = register.registerIcon("grass_side_overlay");
        this.grassIcons[empty] = register.registerIcon(Primal.MODID + ":empty");
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
        BlockMetaGrass inc = this.blockTexture;
        if (getPass() == 0) {
            if (side == 1) {
                return inc.grassIcons[empty];
            } else {
                return inc.icons[meta];
            }
        } else {
            if (side == 1) {
                return inc.grassIcons[grassTop];
            } else if (side == 0) {
                return inc.grassIcons[empty];
            } else {
                Material material = worldIn.getBlock(x, y + 1, z)
                    .getMaterial();
                return material != Material.snow && material != Material.craftedSnow ? inc.grassIcons[grassSideOverlay]
                    : inc.grassIcons[grassSideSnowed];
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
        BlockMetaGrass inc = this.blockTexture;
        if (isInventory) {
            if (getPass() == 0) {
                if (side == 1) {
                    return inc.grassIcons[empty];
                } else {
                    return inc.icons[meta];
                }
            } else {
                if (side == 1) {
                    return inc.grassIcons[grassTop];
                } else if (side == 0) {
                    return inc.grassIcons[empty];
                } else {
                    return inc.grassIcons[grassSideOverlay];
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
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
        IPlantable plantable) {
        return Blocks.grass.canSustainPlant(world, x, y, z, direction, plantable);
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
        return Primal.proxy.getGrassRenderID();
    }
}
