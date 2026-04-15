package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.BlockMycelium;
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
import net.pufferlab.primal.items.itemblocks.ItemBlockMeta;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaMycelium extends BlockMycelium implements IPrimalBlock, IMetaBlock {

    protected String[] elements;
    protected String[] elementsBlacklist;
    protected IIcon[] icons;
    public IIcon grassTop;
    public IIcon grassSideSnowed;
    public IIcon grassSideOverlay;
    public IIcon empty;
    protected String name;
    protected String[] elementsTextures;
    public boolean hasSuffix;

    public BlockMetaMycelium(Material material, String[] materials, String type, String[] blacklist, String[] tools,
        int[] levels) {
        super();
        elements = materials;
        name = type;
        elementsBlacklist = blacklist;
        this.setHarvestLevel("shovel", 0);
    }

    public BlockMetaMycelium(Material material, String[] materials, String type) {
        this(material, materials, type, Constants.none, null, null);
    }

    public BlockMetaMycelium() {
        super();
    }

    public BlockMetaMycelium setTextureOverride(String[] elementsTextures) {
        this.elementsTextures = elementsTextures;
        return this;
    }

    public BlockMetaMycelium setBlacklist(String[] blacklist) {
        this.elementsBlacklist = blacklist;
        return this;
    }

    public BlockMetaMycelium setHasSuffix() {
        this.hasSuffix = true;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        for (int i = 0; i < elements.length; i++) {
            icons[i] = register.registerIcon(Primal.MODID + ":" + elements[i] + "_dirt");
        }

        this.grassSideOverlay = register.registerIcon(Primal.MODID + ":mycelium_side_overlay");
        this.grassTop = register.registerIcon("mycelium_top");
        this.grassSideSnowed = register.registerIcon(Primal.MODID + ":grass_side_snowed_overlay");
        this.empty = register.registerIcon(Primal.MODID + ":empty");
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
        if (getPass() == 0) {
            if (side == 1) {
                return this.empty;
            } else {
                return this.icons[meta];
            }
        } else {
            if (side == 1) {
                return this.grassTop;
            } else if (side == 0) {
                return this.empty;
            } else {
                Material material = worldIn.getBlock(x, y + 1, z)
                    .getMaterial();
                return material != Material.snow && material != Material.craftedSnow ? this.grassSideOverlay
                    : this.grassSideSnowed;
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
        if (meta >= this.icons.length) {
            meta = 0;
        }
        if (isInventory()) {
            if (getPass() == 0) {
                if (side == 1) {
                    return this.empty;
                } else {
                    return this.icons[meta];
                }
            } else {
                if (side == 1) {
                    return this.grassTop;
                } else if (side == 0) {
                    return this.empty;
                } else {
                    return this.grassSideOverlay;
                }
            }
        } else {
            return this.icons[meta];
        }
    }

    @Override
    public int getStateID() {
        return 1;
    }

    @Override
    public boolean hasOverlay() {
        return true;
    }

    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
        IPlantable plantable) {
        return Blocks.mycelium.canSustainPlant(world, x, y, z, direction, plantable);
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
    public ISimpleBlockRenderingHandler getRenderer() {
        return Primal.proxy.grassRenderer;
    }

    @Override
    public int getRenderType() {
        return getRenderId();
    }
}
