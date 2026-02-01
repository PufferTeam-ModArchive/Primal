package net.pufferlab.primal.recipes;

import net.minecraft.nbt.NBTTagCompound;

public enum AnvilOrder {

    any(0),
    last(1),
    notLast(2),
    secondLast(3),
    thirdLast(4);

    public int id;
    private static final int lastIndex = 0;
    private static final int secondLastIndex = 1;
    private static final int thirdLastIndex = 2;

    AnvilOrder(int id) {
        this.id = id;
    }

    public boolean isValidOrder(int order) {
        if (this == any) return true;
        if (this == last) return order == lastIndex;
        if (this == notLast) return order != lastIndex;
        if (this == secondLast) return order == secondLastIndex;
        if (this == thirdLast) return order == thirdLastIndex;
        return false;
    }

    public static AnvilOrder get(int id) {
        for (AnvilOrder orders : values()) {
            if (id == orders.id) {
                return orders;
            }
        }
        return null;
    }

    public static void writeToNBT(NBTTagCompound tag, AnvilOrder[] orders) {
        if (orders != null) {
            int[] ordersID = new int[orders.length];
            for (int i = 0; i < orders.length; i++) {
                ordersID[i] = orders[i].id;
            }
            tag.setIntArray("orders", ordersID);
        }
    }

    public static AnvilOrder[] readFromNBT(NBTTagCompound tag) {
        if (tag.hasKey("orders")) {
            int[] ordersID = tag.getIntArray("orders");
            AnvilOrder[] orders = new AnvilOrder[ordersID.length];
            for (int i = 0; i < ordersID.length; i++) {
                orders[i] = AnvilOrder.get(ordersID[i]);
            }
            return orders;
        } else {
            return new AnvilOrder[3];
        }
    }

    @Override
    public String toString() {
        return "AnvilOrder{" + "id=" + id + '}';
    }
}
