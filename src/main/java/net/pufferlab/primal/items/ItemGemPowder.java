package net.pufferlab.primal.items;

import net.minecraft.creativetab.CreativeTabs;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.OreType;

public class ItemGemPowder extends ItemMeta {

    public OreType[] oreTypes;

    public ItemGemPowder(OreType[] oreTypes, String type) {
        super(OreType.getNames(oreTypes), type);
        this.oreTypes = oreTypes;
        this.setHasSuffix();
        this.setRegisterOre();
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }

    @Override
    public boolean canRegister() {
        return Config.oreVeins.getBoolean();
    }
}
