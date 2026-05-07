package net.pufferlab.primal.world;

import net.pufferlab.primal.utils.ItemUtils;

public enum Tasks {

    fuel,
    process,
    item1,
    item2,
    item3,
    item4,
    rain(false),
    moisture(false),
    network(false),
    networkSpread(false),
    generator(false),
    generatorLate(false),
    removal(false),
    wind(false),
    flow(false),
    growth,
    nutrient,
    heat,
    melting,
    alloy,
    inventory(false);

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

    public static String getCapitalizedName(Tasks task) {
        return ItemUtils.getCapitalizedName(task.name());
    }

    public static boolean shouldSerialize(Tasks task) {
        return task.serialize;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public enum Type {

        simpleTask,
        blockTask,
        tileTask;

        public static Type getTask(byte ordinal) {
            return values()[ordinal];
        }

        public static byte getID(Type taskType) {
            return (byte) taskType.ordinal();
        }

        @Override
        public String toString() {
            return this.name();
        }
    }
}
