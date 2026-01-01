package net.pufferlab.primal.inventory;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class PrimalCreativeTab extends CreativeTabs {

    public PrimalCreativeTab(String label) {
        super(label);
    }

    @Override
    public Item getTabIconItem() {
        return Items.flint;
    }
}
