package net.pufferlab.primal.items;

import net.minecraft.creativetab.CreativeTabs;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.OreType;

public class ItemGem extends ItemMeta {

    public OreType[] oreTypes;
    public boolean isMainOre;

    public ItemGem(OreType[] oreTypes, String type) {
        this(oreTypes, type, false);
    }

    public ItemGem(OreType[] oreTypes, String type, boolean isMainOre) {
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
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
