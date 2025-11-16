package net.pufferlab.primitivelife.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPrimitiveKnife extends Item {

    String toolName;
    protected ToolMaterial toolMaterial;

    public ItemPrimitiveKnife(ToolMaterial material, String name) {
        this.toolMaterial = material;
        this.maxStackSize = 1;
        this.toolName = name;
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int x, int y, int z,
        EntityLivingBase entity) {
        itemstack.damageItem(1, entity);
        return true;
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
