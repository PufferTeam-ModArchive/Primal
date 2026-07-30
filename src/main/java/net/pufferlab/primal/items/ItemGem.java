package net.pufferlab.primal.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Config;
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
        if (this.isMainOre) {
            String[] blackListedNames = new String[oreTypes.length];
            for (int i = 0; i < oreTypes.length; i++) {
                oreTypes[i].setOreItem(this, i);
                if (!oreTypes[i].hasGem) {
                    blackListedNames[i] = oreTypes[i].name;
                }
            }
            this.setBlacklist(blackListedNames);
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        for (int i = 0; i < oreTypes.length; i++) {
            OreType oreType = oreTypes[i];
            if (!oreType.hasGem) {
                list.add(oreType.getOreItem());
            }
        }
        super.getSubItems(item, creativeTabs, list);
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
