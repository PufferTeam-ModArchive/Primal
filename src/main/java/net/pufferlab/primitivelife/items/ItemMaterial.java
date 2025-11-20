package net.pufferlab.primitivelife.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.pufferlab.primitivelife.Constants;
import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMaterial extends Item {

    private String[] elements;
    private String[] elementsBlacklist;
    private IIcon[] icons;
    private String name;

    public ItemMaterial(String[] materials, String type, String[] blacklist) {
        elements = materials;
        name = type;
        elementsBlacklist = blacklist;
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    public ItemMaterial(String[] materials, String type) {
        elements = materials;
        name = type;
        elementsBlacklist = Constants.none;
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        for (int i = 0; i < elements.length; i++) {
            if (!Utils.containsExactMatch(elementsBlacklist, elements[i])) {
                if (name.equals("item")) {
                    icons[i] = register.registerIcon(PrimitiveLife.MODID + ":" + elements[i]);
                } else {
                    icons[i] = register.registerIcon(PrimitiveLife.MODID + ":" + elements[i] + "_" + name);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<net.minecraft.item.ItemStack> list) {
        for (int i = 0; i < elements.length; i++) {
            if (!Utils.containsExactMatch(elementsBlacklist, elements[i])) {
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
            return "item." + PrimitiveLife.MODID + ".error";
        }
        if (name.equals("item")) {
            return "item." + PrimitiveLife.MODID + "." + elements[stack.getItemDamage()];
        }
        return "item." + PrimitiveLife.MODID + "." + elements[stack.getItemDamage()] + "_" + name;
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
