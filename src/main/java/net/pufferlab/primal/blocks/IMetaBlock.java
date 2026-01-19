package net.pufferlab.primal.blocks;

public interface IMetaBlock {

    public String[] getElements();

    default String[] getElementsBlacklist() {
        return null;
    };

    public String getElementName();

    public boolean hasSuffix();

    default boolean registerOre() {
        return false;
    };
}
