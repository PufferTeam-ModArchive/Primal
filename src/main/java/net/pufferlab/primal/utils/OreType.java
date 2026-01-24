package net.pufferlab.primal.utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.pufferlab.primal.Primal;

public class OreType {

    public MetalType metalType;
    public String name;
    public int miningLevel;
    public Item oreItem;
    public int oreMeta;
    public Block oreBlock;

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

    public static String[] getTextures(OreType[] stones) {
        String[] names = new String[stones.length];
        for (int i = 0; i < stones.length; i++) {
            names[i] = Primal.MODID + ":" + stones[i].name + "_full_ore";
        }
        return names;
    }

    public static Map<String, Block> oreMap;

    public static Block getOre(OreType[] oreTypes, String name) {
        if (oreMap == null) {
            oreMap = new HashMap<>();
            for (int i = 0; i < oreTypes.length; i++) {
                oreMap.put(oreTypes[i].name, oreTypes[i].oreBlock);
            }
        }
        if (oreMap.containsKey(name)) {
            return oreMap.get(name);
        }
        return null;
    }

}
