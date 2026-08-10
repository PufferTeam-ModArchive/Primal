package net.pufferlab.primal.utils;

public class TreeType implements IPrimalType {

    String name;
    public WoodType woodType;

    public TreeType(WoodType wood, String name) {
        this.name = name;
        this.woodType = wood;
    }

    @Override
    public String getName() {
        return name;
    }
}
