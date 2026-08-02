package net.pufferlab.primal.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;

public class WoodType implements IPrimalType {

    public String name;
    public String[] types;
    public String[] thinTypes;
    public boolean hasLog = true;

    public Block log;
    public int logMeta;
    public Block strippedLog;
    public int strippedLogMeta;
    public Block wood;
    public int woodMeta;
    public Block strippedWood;
    public int strippedWoodMeta;
    public Item bark;
    public int barkMeta;

    public WoodType(String name) {
        this.name = name;
        this.types = new String[] { this.name + "_log", "stripped_" + this.name + "_log", this.name + "_wood",
            "stripped_" + this.name + "_wood" };
        this.thinTypes = new String[] { this.name + "_thin_log", "stripped_" + this.name + "_thin_log",
            this.name + "_thin_wood", "stripped_" + this.name + "_thin_wood" };
    }

    @Override
    public String getName() {
        return name;
    }

    public WoodType hasNoLog() {
        this.hasLog = false;
        return this;
    }

    public WoodType setLogBlock(Block log, int meta) {
        this.log = log;
        this.logMeta = meta;
        return this;
    }

    public WoodType setStrippedLogBlock(Block log, int meta) {
        this.strippedLog = log;
        this.strippedLogMeta = meta;
        return this;
    }

    public WoodType setWoodBlock(Block log, int meta) {
        this.wood = log;
        this.woodMeta = meta;
        return this;
    }

    public WoodType setStrippedWoodBlock(Block log, int meta) {
        this.strippedWood = log;
        this.strippedWoodMeta = meta;
        return this;
    }

    public WoodType setBarkBlock(Item item, int meta) {
        this.bark = item;
        this.barkMeta = meta;
        return this;
    }

    public ItemStack getLogBlock() {
        return new ItemStack(this.log, 1, this.logMeta);
    }

    public static String[] getNames(WoodType[] woodTypes) {
        String[] woods = new String[woodTypes.length];
        for (int i = 0; i < woodTypes.length; i++) {
            woods[i] = woodTypes[i].name;
        }
        return woods;
    }

    public String getSideTexture(boolean stripped) {
        return getActualTextureName(stripped);
    }

    public String getTopTexture(boolean stripped) {
        return getActualTextureName(stripped) + "_top";
    }

    public String getActualTextureName(boolean stripped) {
        String name = this.name;
        if (stripped) {
            if (Utils.contains(Constants.vanillaWoodTypes, name)) {
                return "minecraft:stripped_" + name + "_log";
            }
            return Primal.MODID + ":stripped_" + name + "_log";
        } else {
            if (Utils.contains(Constants.vanillaWoodTypes, name)) {
                if (name.equals("dark_oak")) {
                    name = "big_oak";
                }
                return "minecraft:log_" + name;
            }
            return Primal.MODID + ":" + name + "_log";
        }
    }
}
