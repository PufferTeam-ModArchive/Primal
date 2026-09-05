package net.pufferlab.primal.utils;

import java.util.Random;

public class TreeType implements IPrimalType {

    String name;
    public int variations;
    public WoodType woodType;

    public TreeType(WoodType wood, String name, int variations) {
        this.name = name;
        this.variations = variations;
        this.woodType = wood;
    }

    public String pickOneStructure(Random random) {
        int num = random.nextInt(variations) + 1;
        return name + "_" + num;
    }

    @Override
    public String getName() {
        return name;
    }
}
