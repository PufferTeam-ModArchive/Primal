package net.pufferlab.primal.items;

import net.minecraft.creativetab.CreativeTabs;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;

public class ItemRock extends ItemMeta {

    public ItemRock(StoneType[] materials, String type) {
        super(StoneType.getNames(materials), type);
        setHasSuffix();
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabStone;
    }
}
