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

public class ItemMeta extends Item {

    private String[] elements;
    private String[] elementsBlacklist;
    private String[] elementsHidden;
    private IIcon[] icons;
    private String name;

    public ItemMeta(String[] materials, String type) {
        elements = materials;
        name = type;
        elementsBlacklist = Constants.none;
        elementsHidden = Constants.none;
        this.setHasSubtypes(true);
    }

    public ItemMeta setBlacklist(String[] blacklist) {
        this.elementsBlacklist = blacklist;
        return this;
    }

    public ItemMeta setHidden(String[] blacklist) {
        this.elementsHidden = blacklist;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        for (int i = 0; i < elements.length; i++) {
            if (!Utils.containsExactMatch(elementsBlacklist, elements[i])) {
                if (Utils.containsExactMatch(Constants.uniqueItemTypes, name)) {
                    icons[i] = register.registerIcon(Primal.MODID + ":" + elements[i]);
                } else {
                    icons[i] = register.registerIcon(Primal.MODID + ":" + elements[i] + "_" + name);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<net.minecraft.item.ItemStack> list) {
        for (int i = 0; i < elements.length; i++) {
            if (!Utils.containsExactMatch(elementsBlacklist, elements[i])
                && !Utils.containsExactMatch(elementsHidden, elements[i])) {
                list.add(new ItemStack(item, 1, i));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta >= elements.length || Utils.containsExactMatch(elementsBlacklist, elements[meta])) {
            return null;
        }
        return icons[meta];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() >= elements.length
            || Utils.containsExactMatch(elementsBlacklist, elements[stack.getItemDamage()])) {
            return "item." + Primal.MODID + ".error";
        }
        if (Utils.containsExactMatch(Constants.uniqueItemTypes, name)) {
            return "item." + Primal.MODID + "." + elements[stack.getItemDamage()];
        }
        return "item." + Primal.MODID + "." + elements[stack.getItemDamage()] + "_" + name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        if (meta >= elements.length || Utils.containsExactMatch(elementsBlacklist, elements[meta])) {
            return null;
        }
        return icons[meta];
    }

}
