package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;

public class StoneType {

    StoneCategory category;
    String name;
    int minHeight;
    int maxHeight;

    public StoneType(StoneCategory category, String name) {
        this.category = category;
        this.name = name;
    }

    public StoneType(StoneCategory category, String name, int minHeight, int maxHeight) {
        this(category, name);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public boolean canGenerate(int height) {
        if (height < maxHeight && height > minHeight) {
            return true;
        }
        return false;
    }

    public boolean equals(StoneType type) {
        if (type.name.equals(this.name)) {
            return true;
        }
        return false;
    }

    public static String[] getNames(StoneType[] stones) {
        String[] names = new String[stones.length];
        for (int i = 0; i < stones.length; i++) {
            names[i] = stones[i].name;
        }
        return names;
    }

    public static String[] getTextures(StoneType[] stones) {
        String[] names = new String[stones.length];
        for (int i = 0; i < stones.length; i++) {
            names[i] = Primal.MODID + ":" + stones[i].name + "_raw";
        }
        return names;
    }

    public static List<StoneType> possibleStone = new ArrayList<>();

    public static StoneType pickOneStoneType(StoneType[] stoneTypes, int height, int index) {
        StoneType current = pickRaw(stoneTypes, height, index);
        StoneType below = pickRaw(stoneTypes, height - 1, index);
        StoneType above = pickRaw(stoneTypes, height + 1, index);

        if (current != below && current != above) {
            return below;
        }

        return current;
    }

    private static StoneType pickRaw(StoneType[] stoneTypes, int height, int index) {
        possibleStone.clear();

        for (StoneType type : stoneTypes) {
            if (type.canGenerate(height)) {
                possibleStone.add(type);
            }
        }

        if (possibleStone.isEmpty()) {
            return Constants.dacite;
        }

        int indexM = Math.floorMod(index, possibleStone.size());
        return possibleStone.get(indexM);
    }

    public static Map<StoneType, Integer> metaList;

    public static int getMeta(StoneType[] stoneTypes, StoneType type) {
        if (metaList == null) {
            metaList = new HashMap<>();
            for (int i = 0; i < stoneTypes.length; i++) {
                metaList.put(stoneTypes[i], i);
            }
        }
        if (metaList.containsKey(type)) {
            return metaList.get(type);
        }
        return 0;
    }
}
