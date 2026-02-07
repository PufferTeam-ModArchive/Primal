package net.pufferlab.primal.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.MetalType;
import net.pufferlab.primal.utils.OreType;

public class ItemOre extends ItemMetaHeatable {

    public OreType[] oreTypes;
    public boolean isMainOre;

    public ItemOre(OreType[] oreTypes, String type) {
        this(oreTypes, type, false);
    }

    public ItemOre(OreType[] oreTypes, String type, boolean isMainOre) {
        super(OreType.getNames(oreTypes), type);
        this.oreTypes = oreTypes;
        this.isMainOre = isMainOre;
        this.setRegisterOre();
        this.setHasSuffix();
        if (this.isMainOre) {
            for (int i = 0; i < oreTypes.length; i++) {
                oreTypes[i].oreItem = this;
                oreTypes[i].oreMeta = i;
            }
        }
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        if (stack.getItemDamage() >= oreTypes.length) return null;
        return oreTypes[stack.getItemDamage()].metalType;
    }

    @Override
    public int getMeltingTemperature(ItemStack stack) {
        if (stack.getItemDamage() >= oreTypes.length) return -1;
        return oreTypes[stack.getItemDamage()].metalType.meltingTemperature;
    }

    @Override
    public int getWeldingTemperature(ItemStack stack) {
        if (stack.getItemDamage() >= oreTypes.length) return -1;
        return oreTypes[stack.getItemDamage()].metalType.weldingTemperature;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
