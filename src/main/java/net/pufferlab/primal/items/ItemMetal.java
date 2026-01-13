package net.pufferlab.primal.items;

public class ItemMetal extends ItemMetaHeatable {

    public MetalType[] metalTypes;

    public ItemMetal(MetalType[] materials, String type) {
        super(MetalType.getNames(materials), type);
        this.metalTypes = materials;
    }

}
