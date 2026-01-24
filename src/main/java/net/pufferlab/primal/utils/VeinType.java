package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Utils;

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
        return rand.nextInt(sizeMax - sizeMin + 1) + sizeMin;
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

    public static VeinType pickOneVeinType(Random rand, int y) {
        VeinType[] cache = Constants.veinTypesLayer[y];
        if (cache != null) {
            return cache[rand.nextInt(cache.length)];
        }
        return null;
    }

    public static VeinType[][] generateVeinCache(VeinType[] stoneTypes) {
        VeinType[][] cache = new VeinType[Constants.maxHeight][];

        for (int i = 0; i < Constants.maxHeight; i++) {
            List<VeinType> cacheStone = new ArrayList<>(stoneTypes.length);

            for (VeinType stone : stoneTypes) {
                if (stone.canGenerate(i)) {
                    cacheStone.add(stone);
                }
            }

            if (!cacheStone.isEmpty()) {
                cache[i] = cacheStone.toArray(new VeinType[0]);
            }
        }

        return cache;
    }
}
