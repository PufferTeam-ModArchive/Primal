package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.pufferlab.primal.Constants;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class VeinType {

    public String name;
    public OreType oreType;
    public float rarity;
    public float rarityBlock;
    public float rarityIndicator;
    public int sizeMin;
    public int sizeMax;
    public int minY;
    public int maxY;
    public StoneType[] stoneTypes;

    public VeinType(OreType oreType, String name, int minY, int maxY, int sizeMin, int sizeMax, float rarityIndicator,
        float rarityBlock, float rarity, StoneType... stoneTypes) {
        this.name = name;
        this.oreType = oreType;
        this.sizeMin = sizeMin;
        this.sizeMax = sizeMax;
        this.rarityIndicator = rarityIndicator;
        this.rarityBlock = rarityBlock;
        this.rarity = rarity;
        this.maxY = maxY;
        this.minY = minY;
        this.stoneTypes = stoneTypes;
    }

    public VeinType setHeight(int min, int max) {
        this.minY = min;
        this.maxY = max;
        return this;
    }

    public VeinType setSize(int min, int max) {
        this.sizeMin = min;
        this.sizeMax = max;
        return this;
    }

    public boolean canGenerate(int height) {
        if (height < maxY && height > minY) {
            return true;
        }
        return false;
    }

    public boolean isValidStone(StoneType stone) {
        return Utils.contains(stoneTypes, stone);
    }

    public int getSize(Random rand) {
        int value = rand.nextInt(sizeMax - sizeMin + 1) + sizeMin;
        return value;
    }

    public boolean getChance(Random random) {
        if (random.nextFloat() < this.rarity) {
            return true;
        }
        return false;
    }

    public boolean getChanceBlock(Random random) {
        if (random.nextFloat() < this.rarityBlock) {
            return true;
        }
        return false;
    }

    public boolean getChanceIndicator(Random random) {
        if (random.nextFloat() < this.rarityIndicator) {
            return true;
        }
        return false;
    }

    public static final TIntObjectMap<VeinType[]> veinLayerCache = new TIntObjectHashMap<>();

    public static VeinType pickOneVeinType(Random rand, int y) {
        VeinType[] cache = veinLayerCache.get(y);
        if (cache != null) {
            return cache[rand.nextInt(cache.length)];
        }
        return null;
    }

    public static final TIntObjectMap<VeinType[]> tcVeinLayerCache = new TIntObjectHashMap<>();

    public static VeinType pickOneThaumcraftVeinType(Random rand, int y) {
        VeinType[] cache = tcVeinLayerCache.get(y);
        if (cache != null) {
            return cache[rand.nextInt(cache.length)];
        }
        return null;
    }

    public static void genVeinCache(VeinType[] stoneTypes) {
        for (int i = Constants.minHeight; i < Constants.maxHeight; i++) {
            List<VeinType> cacheStone = new ArrayList<>(stoneTypes.length);

            for (VeinType stone : stoneTypes) {
                if (stone.canGenerate(i)) {
                    cacheStone.add(stone);
                }
            }

            if (!cacheStone.isEmpty()) {
                veinLayerCache.put(i, cacheStone.toArray(new VeinType[0]));
            }
        }
    }

    public static void genTcVeinCache(VeinType[] stoneTypes) {
        for (int i = Constants.minHeight; i < Constants.maxHeight; i++) {
            List<VeinType> cacheStone = new ArrayList<>(stoneTypes.length);

            for (VeinType stone : stoneTypes) {
                if (stone.canGenerate(i)) {
                    cacheStone.add(stone);
                }
            }

            if (!cacheStone.isEmpty()) {
                tcVeinLayerCache.put(i, cacheStone.toArray(new VeinType[0]));
            }
        }
    }
}
