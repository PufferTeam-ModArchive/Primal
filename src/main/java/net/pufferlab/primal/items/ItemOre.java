package net.pufferlab.primal.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.MetalType;
import net.pufferlab.primal.utils.OreType;

public class ItemOre extends ItemMetaHeatable {

    public OreType[] oreTypes;

    public ItemOre(OreType[] oreTypes, String type) {
        super(OreType.getNames(oreTypes), type);
        this.oreTypes = oreTypes;
        this.setRegisterOre();
        this.setHasSuffix();
        if (type.equals("medium_ore")) {
            for (int i = 0; i < oreTypes.length; i++) {
                oreTypes[i].oreItem = this;
                oreTypes[i].oreMeta = i;
            }
        }
    }

    @Override
    public MetalType getMetal(ItemStack stack) {
        return oreTypes[stack.getItemDamage()].metalType;
    }

    @Override
    public int getMeltingTemperature(ItemStack stack) {
        return oreTypes[stack.getItemDamage()].metalType.meltingTemperature;
    }

    @Override
    public int getWeldingTemperature(ItemStack stack) {
        return oreTypes[stack.getItemDamage()].metalType.weldingTemperature;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
