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
    network(false),
    networkSpread(false),
    generator(false),
    generatorLate(false),
    removal(false),
    wind(false),
    flow(false),
    growth,
    replenishment;

    boolean serialize;

    Tasks() {
        this.serialize = true;
    }

    Tasks(boolean serialize) {
        this.serialize = serialize;
    }

    public static Tasks getTask(int ordinal) {
        return values()[ordinal];
    }

    public static int getID(Tasks task) {
        return task.ordinal();
    }

    public static boolean shouldSerialize(Tasks task) {
        return task.serialize;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
