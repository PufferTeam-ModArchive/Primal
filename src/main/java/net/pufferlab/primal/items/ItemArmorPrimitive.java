package net.pufferlab.primal.items;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorPrimitive extends ItemArmor {

    String armorName;
    ArmorMaterial armorMaterial;

    public static final Map<ArmorMaterial, Integer> armorMap = new HashMap<>();

    public ItemArmorPrimitive(ItemArmor.ArmorMaterial armor, String name, int armorType) {
        this(armor, name, getPrefix(armor), armorType);
    }

    public ItemArmorPrimitive(ItemArmor.ArmorMaterial armor, String name, int renderIndex, int armorType) {
        super(armor, renderIndex, armorType);

        armorMaterial = armor;
        armorName = name;
    }

    public static int getPrefix(ArmorMaterial material) {
        if (Utils.isClient()) {
            if (armorMap.containsKey(material)) {
                return armorMap.get(material);
            } else {
                int index = RenderingRegistry.addNewArmourRendererPrefix(material.name());
                armorMap.put(material, index);
                return index;
            }
        }
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(Primal.MODID + ":" + armorName);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item." + Primal.MODID + "." + armorName;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        int layer = (slot == 2) ? 2 : 1;
        return Primal.MODID + ":textures/models/"
            + getArmorMaterial().name()
                .toLowerCase()
            + "_"
            + layer
            + ".png";
    }

    @Override
    public boolean getIsRepairable(ItemStack damagedItem, ItemStack repairMaterial) {
        String material = armorMaterial.name();
        return Utils.containsOreDict(repairMaterial, Utils.getOreDictionaryName("ingot", material));
    }

}
