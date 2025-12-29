package net.pufferlab.primal.recipes;

import java.util.Objects;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

public enum KnappingType {

    clay(0, "clay", "knapping_clay", Utils.getItem("minecraft", "clay_ball", 0, 5), false, "knapping.clay", -0.3F),
    straw(1, "straw", "knapping_straw", Utils.getModItem("straw", 2), false, "dig.thatch", 0.5F),
    flint(2, "flint", "knapping_flint", Utils.getItem("minecraft", "flint", 0, 2), false, "knapping.flint", 0F),
    stone(3, "stone", "knapping_stone", Utils.getItem("minecraft", "flint", 0, 2), false, "knapping.flint", 0F),
    leather(4, "leather", "knapping_leather", Utils.getItem("minecraft", "leather", 0, 5), true, "knapping.leather",
        0F);

    public final int id;
    public final String name;
    public final ItemStack item;
    public final int amount;
    public final String sound;
    public final float pitch;
    public final ResourceLocation resourceLocation;
    public final boolean needsKnife;

    KnappingType(int id, String name, String resourceName, ItemStack item, boolean needsKnife, String sound,
        float pitch) {
        this.id = id;
        this.name = name;
        this.item = Objects.requireNonNull(item);
        this.amount = item.stackSize;
        this.needsKnife = needsKnife;
        this.sound = Primal.MODID + ":" + sound;
        this.pitch = pitch;
        this.resourceLocation = new ResourceLocation(Primal.MODID + ":textures/gui/container/" + resourceName + ".png");
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
