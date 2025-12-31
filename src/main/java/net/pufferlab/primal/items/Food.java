package net.pufferlab.primal.items;

public class Food {

    public String name;
    public int hunger;
    public float saturation;
    public boolean isMeat;
    public String extraItem;
    public int effectId;
    public int effectDuration;
    public float effectProbability;

    public Food(String name, int hunger, float saturation) {
        this.name = name;
        this.hunger = hunger;
        this.saturation = saturation;
    }

    public Food(String name, int hunger, float saturation, boolean isMeat) {
        this(name, hunger, saturation);
        this.isMeat = isMeat;
    }

    public Food(String name, int hunger, float saturation, boolean isMeat, String extraItem) {
        this(name, hunger, saturation, isMeat);
        this.extraItem = extraItem;
    }

    public Food(String name, int hunger, float saturation, boolean isMeat, int effectId, int effectDuration,
        float effectProbability) {
        this(name, hunger, saturation, isMeat);
        this.effectId = effectId;
        this.effectDuration = effectDuration;
        this.effectProbability = effectProbability;
    }

    public Food(String name, int hunger, float saturation, boolean isMeat, String extraItem, int effectId,
        int effectDuration, float effectProbability) {
        this(name, hunger, saturation, isMeat);
        this.extraItem = extraItem;
        this.effectId = effectId;
        this.effectDuration = effectDuration;
        this.effectProbability = effectProbability;
    }

    public boolean hasEffect() {
        return effectId != 0;
    }

    public boolean hasExtraItem() {
        return extraItem != null;
    }
}
