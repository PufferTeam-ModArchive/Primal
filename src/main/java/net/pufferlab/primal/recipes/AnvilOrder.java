package net.pufferlab.primal.recipes;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

public enum AnvilOrder {

    any(0, "any"),
    last(1, "last"),
    notLast(2, "not_last"),
    secondLast(3, "second_last"),
    thirdLast(4, "third_last");

    public int id;
    public String name;
    private static final int lastIndex = 0;
    private static final int secondLastIndex = 1;
    private static final int thirdLastIndex = 2;

    AnvilOrder(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isValidOrder(int order) {
        if (this == any) return true;
        if (this == last) return order == lastIndex;
        if (this == notLast) return order != lastIndex;
        if (this == secondLast) return order == secondLastIndex;
        if (this == thirdLast) return order == thirdLastIndex;
        return false;
    }

    public int getTargetIndex() {
        return switch (this) {
            case last -> 0;
            case secondLast -> 1;
            case thirdLast -> 2;
            case notLast, any -> -1;
        };
    }

    public int getStartIndex() {
        return switch (this) {
            case last, secondLast, thirdLast -> -1;
            case notLast -> 1;
            case any -> 0;
        };
    }

    public static AnvilOrder get(int id) {
        if (id < 0) return null;
        if (id < values().length) return values()[id];
        return null;
    }

    public static void writeToNBT(NBTTagCompound tag, AnvilOrder[] orders) {
        if (orders != null) {
            int[] ordersID = new int[orders.length];
            for (int i = 0; i < orders.length; i++) {
                if (orders[i] == null) {
                    ordersID[i] = -1;
                } else {
                    ordersID[i] = orders[i].id;
                }
            }
            tag.setIntArray("workOrders", ordersID);
        }
    }

    public static AnvilOrder[] readFromNBT(NBTTagCompound tag) {
        if (tag.hasKey("workOrders")) {
            int[] ordersID = tag.getIntArray("workOrders");
            AnvilOrder[] orders = new AnvilOrder[ordersID.length];
            for (int i = 0; i < ordersID.length; i++) {
                orders[i] = AnvilOrder.get(ordersID[i]);
            }
            return orders;
        } else {
            return new AnvilOrder[3];
        }
    }

    public String getTranslatedName() {
        return Utils.translate("gui." + Primal.MODID + ".anvil." + this.name + ".name");
    }

    @Override
    public String toString() {
        return "AnvilOrder{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
