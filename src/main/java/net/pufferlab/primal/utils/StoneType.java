package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class StoneType {

    public StoneCategory category;
    public String name;
    int minHeight;
    int maxHeight;
    int weight;

    public StoneType(StoneCategory category, String name) {
        this.category = category;
        this.name = name;
    }

    public StoneType(StoneCategory category, String name, int minHeight, int maxHeight, int weight) {
        this(category, name);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.weight = weight;
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

    public StoneType setHeight(int min, int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
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

    public static StoneType pickOneStoneType(int height, int index) {
        StoneType current = pickRaw(height, index);
        StoneType below = pickRaw(height - 1, index);
        StoneType above = pickRaw(height + 1, index);

        if (current != below && current != above) {
            return below;
        }

        return current;
    }

    public static final TIntObjectMap<StoneType[]> stoneLayerCache = new TIntObjectHashMap<>();

    private static StoneType pickRaw(int height, int index) {
        StoneType[] cache = stoneLayerCache.get(height);

        if (cache == null || cache.length == 0) return Constants.dacite;

        int indexM = Math.floorMod(index, cache.length);
        return cache[indexM];
    }

    public static void genLayerCache(StoneType[] stoneTypes) {
        for (int i = Constants.minHeight; i < Constants.maxHeight; i++) {
            List<StoneType> cacheStone = new ArrayList<>(stoneTypes.length);

            for (StoneType stone : stoneTypes) {
                if (stone.canGenerate(i)) {
                    cacheStone.add(stone);
                }
            }

            if (!cacheStone.isEmpty()) {
                stoneLayerCache.put(i, cacheStone.toArray(new StoneType[0]));
            }
        }
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

    public static final TIntObjectMap<StoneType> typeMap = new TIntObjectHashMap<>();

    public static void registerStone(StoneType[] stoneTypes, Block block) {
        for (int i = 0; i < stoneTypes.length; i++) {
            typeMap.put(Utils.getBlockKey(block, i), stoneTypes[i]);
        }
    }

    public static StoneType getStoneType(Block block, int meta) {
        int id = Utils.getBlockKey(block, meta);
        if (typeMap.containsKey(id)) {
            return typeMap.get(id);
        }
        return null;
    }
}
