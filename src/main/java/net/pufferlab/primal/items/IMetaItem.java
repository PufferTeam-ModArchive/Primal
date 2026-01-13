package net.pufferlab.primal.items;

import net.minecraft.item.Item;

public interface IMetaItem {

    public String[] getElements();

    default String[] getElementsBlacklist() {
        return null;
    };

    public String getElementName();

    public boolean hasSuffix();

    default boolean registerOre() {
        return false;
    };

    public Item getItemObject();
}
