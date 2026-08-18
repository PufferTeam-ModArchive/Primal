package net.pufferlab.primal.utils;

import java.util.Random;

import net.minecraft.block.Block;
import net.pufferlab.primal.Constants;

public class PlantType implements IPrimalType {

    public boolean isDesertic;
    public boolean isSnowy;
    public boolean isWater;
    public String name;
    public boolean doublePlant = false;
    public Block plantBlock;
    public int plantMeta;
    public Block plantBlock2;
    public int plantMeta2;
    public float rarity;

    public float minRainfall;
    public float maxRainfall;
    public float minTemperature;
    public float maxTemperature;

    public int modelType;

    public PlantType(String name) {
        this.name = name;
    }

    public PlantType(String name, int plantType, int modelType) {
        this(name);
        this.isDesertic = plantType == Constants.desertic;
        this.isSnowy = plantType == Constants.snowy;
        this.isWater = plantType == Constants.wet;
        this.modelType = modelType;
    }

    public PlantType(String name, int plantType, float rarity, float minRainfall, float maxRainfall,
        float minTemperature, float maxTemperature) {
        this(name, plantType, Constants.crossedModel);
        this.rarity = rarity;
        this.minRainfall = minRainfall;
        this.maxRainfall = maxRainfall;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public boolean getChance(Random random) {
        if (random.nextFloat() < this.rarity) {
            return true;
        }
        return false;
    }

    public boolean canSpawn(float rainfall, float temperature) {
        if (rainfall > this.minRainfall && rainfall < this.maxRainfall
            && temperature > this.minTemperature
            && temperature < this.maxTemperature) {
            return true;
        }
        return false;
    }

    public PlantType setPlantItem(Block block, int meta) {
        this.plantBlock = block;
        this.plantMeta = meta;
        return this;
    }

    public PlantType setDoublePlantItem(Block block, int meta, Block block2, int meta2) {
        this.plantBlock = block;
        this.plantMeta = meta;
        this.plantBlock2 = block2;
        this.plantMeta2 = meta2;
        this.doublePlant = true;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
