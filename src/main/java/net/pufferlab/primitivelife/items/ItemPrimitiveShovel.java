package net.pufferlab.primitivelife.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPrimitiveShovel extends ItemSpade {

    String toolName;
    ToolMaterial toolMaterial;

    public ItemPrimitiveShovel(ToolMaterial tool, String name) {
        super(tool);

        toolMaterial = tool;
        toolName = name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(PrimitiveLife.MODID + ":" + toolName);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item." + PrimitiveLife.MODID + "." + toolName;
    }

    @Override
    public boolean getIsRepairable(ItemStack damagedItem, ItemStack repairMaterial) {
        String material = toolMaterial.name();
        return Utils.containsOreDict(repairMaterial, Utils.getOreDictionaryName("ingot", material));
    }
}
