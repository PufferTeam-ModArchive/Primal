package net.pufferlab.primal.utils;

import net.minecraft.item.Item;

public class OreType {

    public MetalType metalType;
    public String name;
    public int miningLevel;
    public Item oreItem;
    public int oreMeta;
    public Item smallOreItem;
    public int smallOreMeta;

    public OreType(MetalType metalType, String name) {
        this.metalType = metalType;
        this.name = name;
        this.miningLevel = metalType.level;
    }

    public static String[] getNames(OreType[] stones) {
        String[] names = new String[stones.length];
        for (int i = 0; i < stones.length; i++) {
            names[i] = stones[i].name;
        }
        return names;
    }

}
