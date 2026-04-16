package net.pufferlab.primal.utils;

import java.util.Random;

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

    public VeinType setRarity(float rarity) {
        this.rarity = rarity;
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

    public int getHeight(Random random) {
        int y = minY + random.nextInt(Math.abs(maxY - minY));
        return y;
    }
}
