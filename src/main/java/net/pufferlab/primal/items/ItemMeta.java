package net.pufferlab.primal.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMeta extends Item implements IMetaItem {

    private String[] elements;
    private String[] elementsBlacklist;
    private String[] elementsHidden;
    private IIcon[] icons;
    private String name;
    private boolean hasSuffix;
    private boolean registerOre;

    public ItemMeta(String[] materials, String type) {
        elements = materials;
        name = type;
        elementsBlacklist = Constants.none;
        elementsHidden = Constants.none;
        this.setHasSubtypes(true);
    }

    public ItemMeta setMaterials(String[] materials) {
        this.elements = materials;
        return this;
    }

    public ItemMeta setBlacklist(String[] blacklist) {
        this.elementsBlacklist = blacklist;
        return this;
    }

    public ItemMeta setHidden(String[] blacklist) {
        this.elementsHidden = blacklist;
        return this;
    }

    public ItemMeta setHiddenAll() {
        this.elementsHidden = elements;
        this.hasSubtypes = false;
        return this;
    }

    public ItemMeta setHasSuffix() {
        this.hasSuffix = true;
        return this;
    }

    public ItemMeta setRegisterOre() {
        this.registerOre = true;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        for (int i = 0; i < elements.length; i++) {
            if (!Utils.contains(elementsBlacklist, elements[i])) {
                if (hasSuffix) {
                    icons[i] = register.registerIcon(Primal.MODID + ":" + elements[i] + "_" + name);
                } else {
                    icons[i] = register.registerIcon(Primal.MODID + ":" + elements[i]);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<net.minecraft.item.ItemStack> list) {
        for (int i = 0; i < elements.length; i++) {
            if (!Utils.contains(elementsBlacklist, elements[i]) && !Utils.contains(elementsHidden, elements[i])) {
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
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() >= elements.length
            || Utils.contains(elementsBlacklist, elements[stack.getItemDamage()])) {
            return "item." + Primal.MODID + ".error";
        }
        if (hasSuffix) {
            return "item." + Primal.MODID + "." + elements[stack.getItemDamage()] + "_" + name;
        } else {
            return "item." + Primal.MODID + "." + elements[stack.getItemDamage()];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        if (meta >= elements.length || Utils.contains(elementsBlacklist, elements[meta])) {
            return null;
        }
        return icons[meta];
    }

    public String[] getElements() {
        return elements;
    }

    @Override
    public String[] getElementsBlacklist() {
        return elementsBlacklist;
    }

    public boolean hasSuffix() {
        return hasSuffix;
    }

    @Override
    public boolean registerOre() {
        return this.registerOre;
    }

    public String getElementName() {
        return name;
    }
}
