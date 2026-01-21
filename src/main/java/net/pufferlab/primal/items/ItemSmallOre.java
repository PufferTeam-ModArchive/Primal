package net.pufferlab.primal.items;

import net.minecraft.creativetab.CreativeTabs;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.OreType;

public class ItemSmallOre extends ItemMetaHeatable {

    public ItemSmallOre(OreType[] oreTypes, String type) {
        super(OreType.getNames(oreTypes), type);
        this.setHasSuffix();
        for (int i = 0; i < oreTypes.length; i++) {
            oreTypes[i].smallOreItem = this;
            oreTypes[i].smallOreMeta = i;
        }
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
