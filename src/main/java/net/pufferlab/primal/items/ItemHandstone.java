package net.pufferlab.primal.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.pufferlab.primal.Primal;

public class ItemHandstone extends Item {

    public ItemHandstone() {
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
    }

    @Override
    public String getUnlocalizedName() {
        return "item." + Primal.MODID + ".handstone";
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName();
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return null;
    }
}
