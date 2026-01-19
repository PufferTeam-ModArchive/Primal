package net.pufferlab.primal.inventory;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

public class CreativeTabsPrimal extends CreativeTabs {

    String item;
    String name;

    public CreativeTabsPrimal(String name, String item) {
        super(Primal.MODID + "CreativeTab" + name);
        this.name = name;
        this.item = Primal.MODID + ":" + item;
    }

    @Override
    public Item getTabIconItem() {
        return Utils.getItem(item)
            .getItem();
    }

    @Override
    public String getTranslatedTabLabel() {
        return Primal.MODNAME + " " + name;
    }

}
