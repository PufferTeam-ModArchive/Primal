package net.pufferlab.primal.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.utils.ItemUtils;

public class KnappingType {

    // spotless:off
    public static final KnappingType clay = new KnappingType("clay", ItemUtils.getItem("minecraft", "clay_ball", 0, 5), false, "knapping.clay", -0.3F);
    public static final KnappingType straw = new KnappingType("straw", ItemUtils.getModItem("straw", 2), false, "dig.thatch", 0.5F);
    public static final KnappingType flint = new KnappingType("flint", ItemUtils.getItem("minecraft", "flint", 0, 2), false, "knapping.flint", 0F);
    public static final KnappingType stone = new KnappingType("stone", ItemUtils.getItem("minecraft", "flint", 0, 2), false, "knapping.flint", 0F);
    public static final KnappingType leather = new KnappingType("leather", ItemUtils.getItem("minecraft", "leather", 0, 5), true, "knapping.leather", 0F);
    // spotless:on

    private static final List<KnappingType> values = new ArrayList<>(Arrays.asList(clay, straw, flint, stone, leather));

    private static int nextId = 0;
    private static final int idOffset = 100;

    public final int id;
    public final String name;
    public final ItemStack item;
    public final int amount;
    public final String sound;
    public final float pitch;
    public final ResourceLocation resourceLocation;
    public final boolean needsKnife;

    public KnappingType(String name, ItemStack item, boolean needsKnife, String sound, float pitch) {
        this.id = nextId++;
        this.name = name.toLowerCase();
        this.item = Objects.requireNonNull(item);
        this.amount = item.stackSize;
        this.needsKnife = needsKnife;
        this.sound = Primal.MODID + ":" + sound;
        this.pitch = pitch;
        this.resourceLocation = new ResourceLocation(Primal.MODID, "textures/gui/container/knapping_" + name + ".png");
    }

    public boolean equals(KnappingType type) {
        return this.id == type.id;
    }

    public Item getItem() {
        return item.getItem();
    }

    public static KnappingType getNewType(String name, ItemStack item, boolean needsKnife, String sound, float pitch) {
        return new KnappingType(name, item, needsKnife, sound, pitch);
    }

    public static void addType(KnappingType type) {
        boolean isDuplicate = false;
        for (KnappingType t : values) {
            if (Utils.equalsStack(t.item, type.item) || (t.name.equalsIgnoreCase(type.name))) {
                isDuplicate = true;
            }
        }
        if (!isDuplicate) {
            values.add(type);
        }
    }

    public static void removeType(KnappingType type) {
        values.removeIf(r -> {
            if (r.equals(type)) {
                return true;
            }
            return false;
        });
    }

    public static int getHandler(KnappingType type) {
        return type.id + idOffset;
    }

    public static KnappingType getHandler(int id) {
        for (KnappingType t : values) {
            if (t.id == (id - idOffset)) return t;
        }
        return null;
    }

    public static KnappingType getType(ItemStack stack) {
        for (KnappingType t : values) {
            if (Utils.equalsStack(t.item, stack) && t.amount <= stack.stackSize) return t;
        }
        return null;
    }

    public static KnappingType getType(String name) {
        for (KnappingType t : values) {
            if (t.name.equalsIgnoreCase(name)) return t;
        }
        return null;
    }

}
