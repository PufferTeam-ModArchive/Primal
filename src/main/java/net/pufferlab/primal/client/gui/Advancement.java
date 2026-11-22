package net.pufferlab.primal.client.gui;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static net.pufferlab.primal.utils.ItemUtils.getItem;

public class Advancement {

    // What this advancement requires
    private final List<Advancement> dependencies = new ArrayList<>();
    // What advancements requires this
    private final List<Advancement> dependants = new ArrayList<>();

    public ItemStack stack;
    public int x;
    public int y;

    public Advancement(int x, int y, ItemStack stack) {
        this.x = x;
        this.y = y;
        this.stack = stack;
    }

    public Advancement addDependency(Advancement advancement) {
        advancement.dependants.add(this);
        this.dependencies.add(advancement);
        return this;
    }

    public List<Advancement> getDependencies() {
        return this.dependencies;
    }

    public static final Advancement adv1 = new Advancement(0, 0, getItem("minecraft:stone"));
    public static final Advancement adv2 = new Advancement(5, 5, getItem("minecraft:dirt")).addDependency(adv1);
    public static final Advancement adv3 = new Advancement(-3, 2, getItem("minecraft:diamond")).addDependency(adv2);

}
