package net.pufferlab.primal.items;

import net.minecraft.item.Item;

public interface IMetaItem {

    public String[] getElements();

    default String[] getElementsBlacklist() {
        return null;
    };

    public String getElementName();

    public boolean hasSuffix();

    public Item getItemObject();
}
