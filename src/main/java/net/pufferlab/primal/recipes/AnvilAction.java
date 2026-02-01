package net.pufferlab.primal.recipes;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

public enum AnvilAction {

    hitLight(0, "light_hit", -3),
    hitMedium(1, "medium_hit", -6),
    hitHeavy(2, "heavy_hit", -9),
    draw(3, "draw", -15),
    punch(4, "punch", 2),
    bend(5, "bend", 7),
    upset(6, "upset", 13),
    shrink(7, "shrink", 16);

    public int id;
    public int step;
    public String name;

    AnvilAction(int id, String name, int step) {
        this.id = id;
        this.name = name;
        this.step = step;
    }

    public static AnvilAction get(int id) {
        for (AnvilAction actions : values()) {
            if (id == actions.id) {
                return actions;
            }
        }
        return null;
    }

    public static void writeToNBT(NBTTagCompound tag, AnvilAction[] actions) {
        if (actions != null) {
            int[] actionsID = new int[actions.length];
            for (int i = 0; i < actions.length; i++) {
                if (actions[i] == null) {
                    actionsID[i] = -1;
                } else {
                    actionsID[i] = actions[i].id;
                }
            }
            tag.setIntArray("workActions", actionsID);
        }
    }

    public static AnvilAction[] readFromNBT(NBTTagCompound tag) {
        if (tag.hasKey("workActions")) {
            int[] actionsID = tag.getIntArray("workActions");
            AnvilAction[] actions = new AnvilAction[actionsID.length];
            for (int i = 0; i < actionsID.length; i++) {
                actions[i] = AnvilAction.get(actionsID[i]);
            }
            return actions;
        } else {
            return new AnvilAction[3];
        }
    }

    public boolean isHitAction() {
        return this == hitLight || this == hitMedium || this == hitHeavy;
    }

    public boolean equals(AnvilAction action) {
        if (action.isHitAction() && this.isHitAction()) return true;
        return this == action;
    }

    public String getTranslatedName() {
        return Utils.translate("gui." + Primal.MODID + ".anvil." + this.name + ".name");
    }

    @Override
    public String toString() {
        return "AnvilAction{" + "id=" + id + ", step=" + step + ", name='" + name + '\'' + '}';
    }
}
