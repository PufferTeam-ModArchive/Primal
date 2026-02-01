package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHammerPrimitive extends Item {

    String toolName;
    ToolMaterial toolMaterial;

    public ItemHammerPrimitive(ToolMaterial tool, String name) {
        toolMaterial = tool;
        toolName = name;
        this.maxStackSize = 1;
        this.setMaxDamage(tool.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(Primal.MODID + ":" + toolName);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item." + Primal.MODID + "." + toolName;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean getIsRepairable(ItemStack damagedItem, ItemStack repairMaterial) {
        String material = toolMaterial.name();
        return Utils.containsOreDict(repairMaterial, Utils.getOreDictionaryName("ingot", material));
    }
}
