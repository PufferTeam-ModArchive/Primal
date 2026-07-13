package net.pufferlab.primal.items;

import net.minecraft.item.Item;
import net.pufferlab.primal.Registry;

public class ItemWrench extends Item {

    public ItemWrench() {
        this.maxStackSize = 1;
        this.setMaxDamage(Registry.toolCopper.getMaxUses());
    }
}
