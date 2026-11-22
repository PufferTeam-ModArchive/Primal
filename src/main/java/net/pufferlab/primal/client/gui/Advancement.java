package net.pufferlab.primal.client.gui;

import java.util.ArrayList;
import java.util.List;

public class Advancement {

    // What this advancement requires
    private final List<Advancement> dependencies = new ArrayList<>();
    // What advancements requires this
    private final List<Advancement> dependants = new ArrayList<>();

    public int x;
    public int y;

    public Advancement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Advancement addDependency(Advancement advancement) {
        advancement.dependants.add(this);
        this.dependencies.add(advancement);
        return this;
    }

    public List<Advancement> getDependencies() {
        return this.dependencies;
    }

    public static final Advancement adv1 = new Advancement(0, 0);
    public static final Advancement adv2 = new Advancement(5, 5).addDependency(adv1);
    public static final Advancement adv3 = new Advancement(-3, 2).addDependency(adv2);

}
