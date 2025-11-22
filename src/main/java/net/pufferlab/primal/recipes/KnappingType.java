package net.pufferlab.primal.recipes;

import java.util.Objects;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

public enum KnappingType {

    clay(0, "clay", Utils.getItem("minecraft", "clay_ball", 0, 5), "knapping.clay", -0.3F),
    straw(1, "straw", Utils.getModItem("misc", "item", "straw", 2), "dig.thatch", -0.3F),
    flint(2, "flint", Utils.getItem("minecraft", "flint", 0, 2), "knapping.flint", 0F);

    public final int id;
    public final String name;
    public final ItemStack item;
    public final int amount;
    public final String sound;
    public final float pitch;

    KnappingType(int id, String name, ItemStack item, String sound, float pitch) {
        this.id = id;
        this.name = name;
        this.item = Objects.requireNonNull(item);
        this.amount = item.stackSize;
        this.sound = Primal.MODID + ":" + sound;
        this.pitch = pitch;
    }

    public boolean equals(KnappingType type) {
        return this.id == type.id;
    }

    public Item getItem() {
        return item.getItem();
    }

    public static int getHandler(KnappingType type) {
        return type.id + 100;
    }

    public static KnappingType getHandler(int id) {
        for (KnappingType t : values()) {
            if (t.id == (id - 100)) return t;
        }
        return null;
    }

    public static KnappingType getType(ItemStack stack) {
        for (KnappingType t : values()) {
            if (Utils.containsStack(t.item, stack) && t.amount <= stack.stackSize) return t;
        }
        return null;
    }

    public static KnappingType getType(String name) {
        for (KnappingType t : values()) {
            if (t.name.equalsIgnoreCase(name)) return t;
        }
        return null;
    }

}
