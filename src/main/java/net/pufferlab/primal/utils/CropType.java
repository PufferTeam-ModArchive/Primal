package net.pufferlab.primal.utils;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Config;

public class CropType implements IPrimalType {

    public String name;
    public String cropName;
    public Block cropBlock;
    public FoodType food;
    public int growStages;
    public int minCrops;
    public int maxCrops;
    public Item seedItem;
    public int seedMeta;
    public Item cropItem;
    public int cropMeta;
    public int cropRenderType;
    public boolean hasCropItem = true;
    public boolean hasSeedItem = true;
    public boolean hasCropFood = true;
    public char nutrient;
    public float nutrientConsumption;
    public float growthMultiplier;
    public static final Set<Item> cropTypes = new HashSet<>();

    public CropType(FoodType food, String cropName, int growStages, int minDrops, int maxDrops, char nutrientName,
        float nutrient, float growthMultiplier, int cropRenderType) {
        this.food = food;
        this.name = food.name;
        this.cropName = cropName;
        this.growStages = growStages;
        this.minCrops = minDrops;
        this.maxCrops = maxDrops;
        this.nutrient = nutrientName;
        this.nutrientConsumption = nutrient;
        this.growthMultiplier = growthMultiplier;
        this.cropRenderType = cropRenderType;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getGrowthTicks(Random random) {
        int adjustedTick = (int) (Config.foodBaseGrowth.getInt() * this.growthMultiplier);
        int margin = (int) (adjustedTick * 0.10F);
        return Utils.getRandomInRange(random, adjustedTick - margin, adjustedTick + margin);
    }

    public CropType setCropItem(Item item, int meta) {
        this.cropItem = item;
        this.cropMeta = meta;
        return this;
    }

    public ItemStack getCropItem() {
        return new ItemStack(this.cropItem, 1, this.cropMeta);
    }

    public CropType setCropSeedItem(Item item, int meta) {
        this.seedItem = item;
        this.seedMeta = meta;
        return this;
    }

    public ItemStack getSeedItem() {
        return new ItemStack(this.seedItem, 1, this.seedMeta);
    }

    public CropType updateFoodValues() {
        Item item = this.cropItem;
        if (item instanceof ItemFood foodItem) {
            foodItem.healAmount = food.hunger;
            foodItem.saturationModifier = food.saturation;
        }
        Item seedItem = this.seedItem;
        if (seedItem instanceof ItemSeeds seeds) {
            seeds.field_150925_a = cropBlock;
        }
        cropTypes.add(item);
        return this;
    }

    public static FoodType[] getFoodTypes(CropType[] cropTypes) {
        FoodType[] foods = new FoodType[cropTypes.length];
        for (int i = 0; i < cropTypes.length; i++) {
            foods[i] = cropTypes[i].food;
        }
        return foods;
    }

    public static String[] getNames(CropType[] cropTypes) {
        String[] foods = new String[cropTypes.length];
        for (int i = 0; i < cropTypes.length; i++) {
            foods[i] = cropTypes[i].name;
        }
        return foods;
    }

    public CropType hasNoCropItem() {
        this.hasCropItem = false;
        return this;
    }

    public CropType hasNoSeedItem() {
        this.hasSeedItem = false;
        return this;
    }

    public CropType hasNoCropFood() {
        this.hasCropFood = false;
        return this;
    }
}
