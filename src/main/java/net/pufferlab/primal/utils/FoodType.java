package net.pufferlab.primal.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.pufferlab.primal.Config;

public class FoodType implements IPrimalType {

    public Item foodItem;
    public int foodMeta;
    public String name;
    public int hunger;
    public float saturation;
    public boolean isMeat;
    public String extraItem;
    public int effectId;
    public int effectDuration;
    public float effectProbability;
    public boolean hasFoodItem = true;

    public FoodType(String name) {
        this.name = name;
    }

    public FoodType(String name, int hunger, float saturation) {
        this.name = name;
        this.hunger = hunger;
        this.saturation = saturation;
    }

    public FoodType(String name, int hunger, float saturation, boolean isMeat) {
        this(name, hunger, saturation);
        this.isMeat = isMeat;
    }

    public FoodType(String name, int hunger, float saturation, boolean isMeat, String extraItem) {
        this(name, hunger, saturation, isMeat);
        this.extraItem = extraItem;
    }

    public FoodType(String name, int hunger, float saturation, boolean isMeat, int effectId, int effectDuration,
        float effectProbability) {
        this(name, hunger, saturation, isMeat);
        this.effectId = effectId;
        this.effectDuration = effectDuration;
        this.effectProbability = effectProbability;
    }

    public FoodType(String name, int hunger, float saturation, boolean isMeat, String extraItem, int effectId,
        int effectDuration, float effectProbability) {
        this(name, hunger, saturation, isMeat);
        this.extraItem = extraItem;
        this.effectId = effectId;
        this.effectDuration = effectDuration;
        this.effectProbability = effectProbability;
    }

    public FoodType setFoodItem(Item item, int meta) {
        this.foodItem = item;
        this.foodMeta = meta;
        return this;
    }

    public FoodType hasNoFoodItem() {
        this.hasFoodItem = false;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getInt(Config.Value.Type index) {
        if (index == Config.Value.Type.food) {
            return hunger;
        }
        return 0;
    }

    @Override
    public float getFloat(Config.Value.Type index) {
        if (index == Config.Value.Type.food) {
            return saturation;
        }
        return 0.0F;
    }

    @Override
    public void setInt(Config.Value.Type index, int primary) {
        if (index == Config.Value.Type.food) {
            this.hunger = primary;
        }
    }

    @Override
    public void setFloat(Config.Value.Type index, float primary) {
        if (index == Config.Value.Type.food) {
            this.saturation = primary;
        }
    }

    public void updateFoodValues() {
        Item item = this.foodItem;
        if (item instanceof ItemFood foodItem2) {
            foodItem2.healAmount = this.hunger;
            foodItem2.saturationModifier = this.saturation;
        }
    }

    public FoodType setFoodValues(int hunger, float saturation) {
        this.hunger = hunger;
        this.saturation = saturation;
        return this;
    }

    public boolean hasEffect() {
        return effectId != 0;
    }

    public boolean hasExtraItem() {
        return extraItem != null;
    }

    public static String[] getNames(FoodType[] foods) {
        String[] names = new String[foods.length];
        for (int i = 0; i < foods.length; i++) {
            names[i] = foods[i].name;
        }
        return names;
    }

    public static String[] getBlacklistNames(FoodType[] foods) {
        String[] names = new String[foods.length];
        for (int i = 0; i < foods.length; i++) {
            if (!foods[i].hasFoodItem) {
                names[i] = foods[i].name;
            }
        }
        return names;
    }

    public static String[] getSeedNames(FoodType[] foods) {
        String[] names = new String[foods.length];
        for (int i = 0; i < foods.length; i++) {
            names[i] = foods[i].name + "_seed";
        }
        return names;
    }
}
