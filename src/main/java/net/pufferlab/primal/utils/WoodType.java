package net.pufferlab.primal.utils;

import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;

public class WoodType {

    public String name;
    public String[] types;
    public String[] thinTypes;

    public WoodType(String name) {
        this.name = name;
        this.types = new String[] { this.name + "_log", "stripped_" + this.name + "_log", this.name + "_wood",
            "stripped_" + this.name + "_wood" };
        this.thinTypes = new String[] { this.name + "_thin_log", "stripped_" + this.name + "_thin_log",
            this.name + "_thin_wood", "stripped_" + this.name + "_thin_wood" };
    }

    public String getSideTexture(boolean stripped) {
        return getActualTextureName(stripped);
    }

    public String getTopTexture(boolean stripped) {
        return getActualTextureName(stripped) + "_top";
    }

    public String getActualTextureName(boolean stripped) {
        if (stripped) {
            if (Utils.contains(Constants.vanillaWoodTypes, name)) {
                return "minecraft:stripped_" + name + "_log";
            }
            return Primal.MODID + ":stripped_" + name + "_log";
        } else {
            if (Utils.contains(Constants.vanillaWoodTypes, name)) {
                return "minecraft:log_" + name;
            }
            return Primal.MODID + ":" + name + "_log";
        }
    }
}
