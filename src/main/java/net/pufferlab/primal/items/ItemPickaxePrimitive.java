package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.utils.ItemUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPickaxePrimitive extends ItemPickaxe {

    String toolName;
    ToolMaterial toolMaterial;

    public ItemPickaxePrimitive(ToolMaterial tool, String name) {
        super(tool);

        toolMaterial = tool;
        toolName = name;
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

    @Override
    public boolean getIsRepairable(ItemStack damagedItem, ItemStack repairMaterial) {
        String material = toolMaterial.name();
        return Utils.containsOreDict(repairMaterial, ItemUtils.getOreDictionaryName("ingot", material));
    }
}
