package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockMeta;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockMetaContainer extends BlockContainerPrimal implements IPrimalBlock, IMetaBlock {

    protected String[] elements;
    protected String[] elementsBlacklist;
    protected IIcon[] icons;
    protected String name;
    protected String textureName;
    protected String[] elementsTextures;
    public boolean hasSuffix;
    public boolean isItemTexture;

    public BlockMetaContainer(Material material, String[] materials, String type, String[] blacklist, String[] tools,
        int[] levels) {
        super(material);
        elements = materials;
        name = type;
        elementsBlacklist = blacklist;

        for (int i = 0; i < elements.length; i++) {
            if (!Utils.contains(elementsBlacklist, elements[i]) && tools != null) {
                this.setHarvestLevel(tools[i], levels[i], i);
            } else {
                if (material == Material.rock) {
                    this.setHarvestLevel("pickaxe", 0, i);
                } else if (material == Material.wood) {
                    this.setHarvestLevel("axe", 0, i);
                }
            }
        }

    }

    public BlockMetaContainer(Material material, String[] materials, String type) {
        this(material, materials, type, Constants.none, null, null);
    }

    public BlockMetaContainer setTextureOverride(String[] elementsTextures) {
        this.elementsTextures = elementsTextures;
        return this;
    }

    public BlockMetaContainer setBlacklist(String[] blacklist) {
        this.elementsBlacklist = blacklist;
        return this;
    }

    public BlockMetaContainer setHasSuffix() {
        this.hasSuffix = true;
        return this;
    }

    public BlockMetaContainer setItemTexture() {
        this.isItemTexture = true;
        return this;
    }

    public BlockMetaContainer setPrefix(String prefix) {
        this.textureName = prefix;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        String prefix = "";
        if (isItemTexture) {
            prefix = "items/";
        }
        for (int i = 0; i < elements.length; i++) {
            if (elementsTextures == null) {
                if (!Utils.contains(elementsBlacklist, elements[i])) {
                    if (hasSuffix) {
                        if (textureName != null) {
                            icons[i] = register
                                .registerIcon(Primal.MODID + ":" + prefix + elements[i] + "_" + textureName);
                        } else {
                            icons[i] = register.registerIcon(Primal.MODID + ":" + prefix + elements[i] + "_" + name);
                        }
                    } else {
                        icons[i] = register.registerIcon(Primal.MODID + ":" + prefix + elements[i]);
                    }
                }
            } else {
                icons[i] = register.registerIcon(elementsTextures[i]);
            }
        }
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
    public IIcon getIcon(int side, int meta) {
        if (meta >= elements.length || Utils.contains(elementsBlacklist, elements[meta])) {
            return null;
        }
        return icons[meta];
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
}
