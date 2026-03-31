package net.pufferlab.primal.world;

public enum Tasks {

    fuel,
    process,
    item1,
    item2,
    item3,
    item4,
    rain,
    moisture,
    potassium,
    nitrogen,
    phosphorus,
    network,
    networkSpread,
    generator,
    generatorLate,
    removal,
    wind,
    flow,
    growth,
    replenishment;

    boolean serialize;

    Tasks() {

    }

    Tasks(boolean serialize) {
        this.serialize = serialize;
    }

    public static Tasks getTask(int ordinal) {
        return values()[ordinal];
    }

    @Override
    public String toString() {
        return this.name();
    }
}
