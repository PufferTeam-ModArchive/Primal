package net.pufferlab.primal.world.scheduling;

import net.pufferlab.primal.utils.ItemUtils;

public enum Task {

    fuel,
    process,
    item1,
    item2,
    item3,
    item4,
    rain(false),
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

    Task() {
        this.serialize = true;
    }

    Task(boolean serialize) {
        this.serialize = serialize;
    }

    public static Task getTask(int ordinal) {
        return values()[ordinal];
    }

    public static int getID(Task task) {
        return task.ordinal();
    }

    public static String getCapitalizedName(Task task) {
        return ItemUtils.getCapitalizedName(task.name());
    }

    public static boolean shouldSerialize(Task task) {
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
