package net.pufferlab.primal;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public enum Config {

    vanillaToolsRemovalMode(Module.early, 1, 0, 2,
        "0: Don't remove vanilla tools. 1: Remove the recipes. 2: Keep the recipes but make tools unusable."),
    stickDropChance(Module.early, 0.166F,
        "The chance from 0 (0%) to 1 (100%) for a stick to drop from leaves. Putting this to 0 will stop dropping."),
    fireStarterSuccessChance(Module.early, 0.2F,
        "The chance from 0 (0%) to 1 (100%) for the fire starter to succeed making a fire. Putting this to 0 will stop the fire starter from working."),

    // Campfire
    campfireBurnTime(Module.early, 20 * 120,
        "The time in ticks that it takes for the campfire to consume one of its fuel."),
    campfireSmeltTime(Module.early, 20 * 60,
        "The time in ticks that it will take the Campfire to smelt one of its slot."),

    // Forge
    forgeBurnTime(Module.early, 20 * 120, "The time in ticks that it takes for the forge to consume one of its fuel."),

    // Pit Kiln
    pitKilnSmeltTime(Module.early, 20 * 120, "The time in ticks that it will take the Pit Kiln to smelt it's content."),

    // Log Pile
    logPileSmeltTime(Module.early, 20 * 120, "The time in ticks that it will take the LogPile to smelt into charcoal."),

    // Torch
    torchBurnTime(Module.lighting, 20 * 60 * 20, "The time in ticks that lit torches will burn before going out."),
    torchRebalance(Module.lighting, true,
        "Whether to make vanilla torches require glowstone to balance the lit torches"),

    // Waterwheel
    waterwheelDefaultSpeed(Module.mechanical, 5F, "The default speed that the waterwheel will have."),
    waterwheelRestrictBiome(Module.mechanical, false, "Whether waterwheel should be limited to River/Oceans biomes."),

    // Windmill
    windmillDefaultSpeed(Module.mechanical, 5F, "The default speed that the windmill will have."),
    windmillIdealHeight(Module.mechanical, 100, 0, 256, "The height which the windmill will spin the fastest."),
    windmillRange(Module.mechanical, 60, 0, 256,
        "The range around the ideal height in which the windmill will operate."),

    // WorldGen
    rockWorldGen(Module.worldgen, true, "Whether to enable loose rocks generating in the world.");

    public boolean isBoolean;
    public boolean isInt;
    public boolean isFloat;
    String name;
    String category;
    String comment;

    boolean bValue;
    boolean bDefault;
    int iValue;
    int iDefault;
    int iMinValue;
    int iMaxValue;
    float fValue;
    float fDefault;
    float fMinValue;
    float fMaxValue;

    Config(Module category, boolean defaultValue, String comment) {
        this.isBoolean = true;
        this.name = this.name();
        this.category = category.name;
        this.comment = comment;
        this.bDefault = defaultValue;
        this.bValue = bDefault;
    }

    Config(Module category, int defaultValue, String comment) {
        this.isInt = true;
        this.name = this.name();
        this.iMinValue = 0;
        this.iMaxValue = 50000;
        this.category = category.name;
        this.comment = comment;
        this.iDefault = defaultValue;
        this.iValue = iDefault;
    }

    Config(Module category, int defaultValue, int minValue, int maxValue, String comment) {
        this.isInt = true;
        this.name = this.name();
        this.iMinValue = minValue;
        this.iMaxValue = maxValue;
        this.category = category.name;
        this.comment = comment;
        this.iDefault = defaultValue;
        this.iValue = iDefault;
    }

    Config(Module category, float defaultValue, String comment) {
        this.isFloat = true;
        this.name = this.name();
        this.category = category.name;
        this.comment = comment;
        this.fDefault = defaultValue;
        if (defaultValue > 1.0F) {
            this.fMinValue = 0.0F;
            this.fMaxValue = 256.0F;
        } else {
            this.fMinValue = 0.0F;
            this.fMaxValue = 1.0F;
        }
        this.fValue = fDefault;
    }

    public boolean getBoolean() {
        if (!this.isBoolean) {
            throw new IllegalArgumentException();
        }
        return bValue;
    }

    public boolean getDefaultBoolean() {
        if (!this.isBoolean) {
            throw new IllegalArgumentException();
        }
        return bDefault;
    }

    public int getInt() {
        if (!this.isInt) {
            throw new IllegalArgumentException();
        }
        return iValue;
    }

    public int getDefaultInt() {
        if (!this.isInt) {
            throw new IllegalArgumentException();
        }
        return iDefault;
    }

    public float getFloat() {
        if (!this.isFloat) {
            throw new IllegalArgumentException();
        }
        return fValue;
    }

    public float getDefaultFloat() {
        if (!this.isFloat) {
            throw new IllegalArgumentException();
        }
        return fDefault;
    }

    public int getChance() {
        if (!this.isFloat) {
            throw new IllegalArgumentException();
        }
        return (int) Math.floor(1 / getFloat());
    }

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        for (Config config : Config.values()) {
            if (config.isBoolean) {
                config.bValue = configuration.getBoolean(config.name, config.category, config.bDefault, config.comment);
            }
            if (config.isInt) {
                config.iValue = configuration.getInt(
                    config.name,
                    config.category,
                    config.iDefault,
                    config.iMinValue,
                    config.iMaxValue,
                    config.comment);
            }
            if (config.isFloat) {
                config.fValue = configuration.getFloat(
                    config.name,
                    config.category,
                    config.fDefault,
                    config.fMinValue,
                    config.fMaxValue,
                    config.comment);
            }
        }
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static enum Module {

        early(true),
        metal(true),
        mechanical(true),
        worldgen(true),
        lighting(true);

        String name;
        boolean enabled;
        boolean enabledDefault;

        Module(boolean enabled) {
            this.name = this.name();
            this.enabledDefault = enabled;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }
}
