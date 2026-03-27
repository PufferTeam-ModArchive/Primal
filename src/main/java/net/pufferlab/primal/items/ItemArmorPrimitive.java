package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class ItemArmorPrimitive extends ItemArmor implements IPrimalItem {

    String armorName;
    ArmorMaterial armorMaterial;

    public static final TObjectIntMap<ItemArmor.ArmorMaterial> armorMap = new TObjectIntHashMap<>();

    public ItemArmorPrimitive(ItemArmor.ArmorMaterial armor, String name, String armorType) {
        this(armor, name, getPrefix(armor), getArmor(armorType));
    }

    public ItemArmorPrimitive(ItemArmor.ArmorMaterial armor, String name, int renderIndex, int armorID) {
        super(armor, renderIndex, armorID);

        armorMaterial = armor;
        armorName = name;
    }

    public static int getPrefix(ItemArmor.ArmorMaterial material) {
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

    public static int getArmor(String armorType) {
        return switch (armorType) {
            case Constants.helmet -> 0;
            case Constants.chestplate -> 1;
            case Constants.leggings -> 2;
            case Constants.boots -> 3;
            default -> 0;
        };
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
            + "_layer_"
            + layer
            + ".png";
    }

    @Override
    public boolean getIsRepairable(ItemStack damagedItem, ItemStack repairMaterial) {
        String material = armorMaterial.name();
        return Utils.containsOreDict(repairMaterial, ItemUtils.getOreDictionaryName("ingot", material));
    }

}
