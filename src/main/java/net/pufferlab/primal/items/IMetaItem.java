package net.pufferlab.primal.items;

public interface IMetaItem {

    public String[] getElements();

    default String[] getElementsBlacklist() {
        return null;
    };

    public String getElementName();

    public boolean hasSuffix();

    default String getSuffix() {
        String suffix = "";
        if (hasSuffix()) {
            suffix = "_" + getElementName();
        }
        return suffix;
    }

    default boolean registerOre() {
        return false;
    }

    default boolean registerModItem() {
        return true;
    }
}
