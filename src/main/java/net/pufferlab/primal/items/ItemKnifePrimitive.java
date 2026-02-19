package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.utils.ItemUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemKnifePrimitive extends ItemSword {

    String toolName;
    protected ToolMaterial toolMaterial;

    public ItemKnifePrimitive(ToolMaterial material, String name) {
        super(material);
        this.toolMaterial = material;
        this.maxStackSize = 1;
        this.toolName = name;
        this.setMaxDamage(material.getMaxUses());
        this.field_150934_a = 3.0F + toolMaterial.getDamageVsEntity();
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        return itemStackIn;
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
