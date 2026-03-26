package net.pufferlab.primal.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class CropType {

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

    public CropType(FoodType food, String cropName, int growStages, int minDrops, int maxDrops, int cropRenderType) {
        this.food = food;
        this.name = food.name;
        this.cropName = cropName;
        this.growStages = growStages;
        this.minCrops = minDrops;
        this.maxCrops = maxDrops;
        this.cropRenderType = cropRenderType;
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
}
