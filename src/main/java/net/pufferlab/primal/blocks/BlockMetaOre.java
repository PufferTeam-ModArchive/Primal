package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.itemblocks.ItemBlockMeta;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaOre extends BlockStone implements IPrimalBlock, IMetaBlock {

    protected String[] elements;
    protected String[] elementsBlacklist;
    protected IIcon[] icons;
    public IIcon[] oreIcons;
    public static final int oreOverlay = 0;
    public static final int empty = 1;
    protected String name;
    protected String[] elementsTextures;
    public boolean hasSuffix;
    public BlockMetaOre blockTexture;
    public boolean isEmissive;
    public String topTexture;
    public int color = 16777215;

    public int renderPass;
    public int renderPass2;
    public boolean isInventory;

    public BlockMetaOre(Material material, String[] materials, String type, String[] blacklist, int level) {
        super();
        elements = materials;
        name = type;
        elementsBlacklist = blacklist;
        blockTexture = this;

        for (int i = 0; i < elements.length; i++) {
            this.setHarvestLevel("pickaxe", level, i);
        }

    }

    public BlockMetaOre(Material material, String[] materials, String type, int level) {
        this(material, materials, type, Constants.none, level);
    }

    public BlockMetaOre() {
        super();
    }

    public BlockMetaOre setTextureOverride(String[] elementsTextures) {
        this.elementsTextures = elementsTextures;
        return this;
    }

    public BlockMetaOre setBlacklist(String[] blacklist) {
        this.elementsBlacklist = blacklist;
        return this;
    }

    public BlockMetaOre setHasSuffix() {
        this.hasSuffix = true;
        return this;
    }

    public BlockMetaOre setEmissive(int color) {
        this.isEmissive = true;
        this.color = color;
        return this;
    }

    public BlockMetaOre setEmissiveTexture(String topTexture) {
        this.topTexture = topTexture;
        return this;
    }

    @Override
    public boolean hasOverlay() {
        return true;
    }

    @Override
    public boolean isEmissive() {
        return this.blockTexture.isEmissive;
    }

    @Override
    public int getBlockColor() {
        return this.blockTexture.color;
    }

    @Override
    public int colorMultiplier(IBlockAccess worldIn, int x, int y, int z) {
        return this.blockTexture.color;
    }

    @Override
    public int getRenderColor(int meta) {
        return this.blockTexture.color;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        for (int i = 0; i < elements.length; i++) {
            icons[i] = register.registerIcon(Primal.MODID + ":" + elements[i] + "_raw");
        }

        this.oreIcons = new IIcon[2];

        if (topTexture != null) {
            this.oreIcons[oreOverlay] = register.registerIcon(Primal.MODID + ":" + topTexture);
        } else {
            this.oreIcons[oreOverlay] = register.registerIcon(Primal.MODID + ":" + name + "_ore");
        }
        this.oreIcons[empty] = register.registerIcon(Primal.MODID + ":empty");
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
        BlockMetaOre inc = this.blockTexture;
        if (getPass() == 0) {
            return inc.icons[meta];
        } else {
            return inc.oreIcons[oreOverlay];
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        BlockMetaOre inc = this.blockTexture;
        if (isInventory) {
            if (getPass() == 0) {
                return inc.icons[meta];
            } else {
                return inc.oreIcons[oreOverlay];
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
        return Primal.proxy.getOreRenderID();
    }
}
