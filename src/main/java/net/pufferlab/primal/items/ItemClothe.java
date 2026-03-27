package net.pufferlab.primal.items;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.compat.baubles.IBaubleItem;

public class ItemClothe extends Item implements IBaubleItem, IPrimalItem {

    public String[] baubleType;
    public String baubleTypeName;
    public String name;
    public IIcon icon;

    public ItemClothe(String name, String baubleType) {
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.baubleTypeName = baubleType;
        this.name = name;
    }

    @Override
    public String getUnlocalizedName() {
        return "item." + Primal.MODID + "." + this.name;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.icon = register.registerIcon(Primal.MODID + ":" + this.name);
    }

    @Override
    public IIcon getIconFromDamage(int p_77617_1_) {
        return getIcon();
    }

    public IIcon getIcon() {
        return icon;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName();
    }

    @Override
    public String[] getBaubleTypes(ItemStack itemstack) {
        if (baubleType == null) {
            baubleType = new String[] { baubleTypeName };
        }
        return baubleType;
    }

    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
        return null;
    }
}
