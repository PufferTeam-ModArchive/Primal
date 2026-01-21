package net.pufferlab.primal.items;

import net.minecraft.creativetab.CreativeTabs;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.OreType;

public class ItemOre extends ItemMetaHeatable {

    public ItemOre(OreType[] oreTypes, String type) {
        super(OreType.getNames(oreTypes), type);
        this.setHasSuffix();
        for (int i = 0; i < oreTypes.length; i++) {
            oreTypes[i].oreItem = this;
            oreTypes[i].oreMeta = i;
        }
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
