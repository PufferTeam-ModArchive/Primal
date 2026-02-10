package net.pufferlab.primal;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.pufferlab.primal.utils.ConfigUtils;

public enum Config {

    // Vanilla Tweaks
    noTreeFistPunching(Module.early_game$tweaks, true,
        "Whether to enable no tree punching mechanics. Meaning that you cannot break logs with your fist."),
    harderSoil(Module.early_game$tweaks, true,
        "Whether to make soil (dirt/sand) slightly harder to break, giving shovels more use"),
    destructiveFallingBlocks(Module.early_game$tweaks, true,
        "Whether to make it so instead of the falling block getting destroyed on replaceable blocks, the replaceable block get destroyed instead."),
    sidewayFallingBlocks(Module.early_game$tweaks, true,
        "Whether you want to enable sideway gravity for falling blocks, put to false if you dont want the gravity changes"),
    vanillaToolsRemovalMode(Module.early_game$tweaks, 1, 0, 2,
        "0: Don't remove vanilla tools. 1: Remove the recipes. 2: Keep the recipes but make tools unusable."),
    leatherDropReplacement(Module.early_game$tweaks, true,
        "Whether to replace leather drops with raw hides to balance the leather recipes."),
    stickDropChance(Module.early_game$tweaks, 0.166F,
        "The chance from 0 (0%) to 1 (100%) for a stick to drop from leaves. Putting this to 0 will stop dropping."),

    // Mod Content
    fireStarterSuccessChance(Module.early_game, 0.2F,
        "The chance from 0 (0%) to 1 (100%) for the fire starter to succeed making a fire. Putting this to 0 will stop the fire starter from working."),
    ceramicBucketLiquids(Module.early_game, new String[] { "fluiddeath", "fluidpure" },
        "The extra liquids that the ceramic bucket will be able to hold"),
    ceramicBucketLiquidsHotCap(Module.early_game, 1000,
        "The temperature for a liquid to be considered hot and break the ceramic bucket."),

    // Campfire
    campfireBurnTime(Module.early_game, 20 * 120,
        "The time in ticks that it takes for the campfire to consume one of its fuel."),
    campfireSmeltTime(Module.early_game, 20 * 15,
        "The time in ticks that it will take the Campfire to smelt one of its slot."),
    ovenSmeltTime(Module.early_game, 20 * 7, "The time in ticks that it will take the Oven to smelt one of its slot."),

    // Forge
    forgeBurnTime(Module.early_game, 20 * 120,
        "The time in ticks that it takes for the forge to consume one of its fuel."),

    // Pit Kiln
    pitKilnSmeltTime(Module.early_game, 20 * 120,
        "The time in ticks that it will take the Pit Kiln to smelt it's content."),

    // Log Pile
    logPileSmeltTime(Module.early_game, 20 * 120,
        "The time in ticks that it will take the LogPile to smelt into charcoal."),

    // Torch
    litTorches(Module.early_game$lighting, true, "Put to false if you don't want to enable the torches with burn time"),
    torchBurnTime(Module.early_game$lighting, 20 * 60 * 20,
        "The time in ticks that lit torches will burn before going out."),
    torchRebalance(Module.early_game$lighting, true,
        "Whether to make vanilla torches require glowstone to balance the lit torches"),

    // Metal
    temperatureCap(Module.metalworking, 1,
        "The minimum temperature will be displayed, anything lower will not show in tooltips"),
    metalHeatRendering(Module.metalworking, true,
        "Put to false if you don't want heat rendering on items. Will globally disable all rendering even on modded ingots."),
    modMetalHeatRendering(Module.metalworking, true,
        "Put to false if you are getting some rendering issue with other mod ingots that get registered Primal Heat Rendering overlay."),
    metalPriority(Module.metalworking, new String[] { "minecraft", "primal", "etfuturum" },
        "The ingot priority list, higher means the mod items will be taken in priority."),
    metalPriorityOverride(Module.metalworking,
        new String[] { "ingotIron=minecraft:iron_ingot", "ingotGold=minecraft:gold_ingot" },
        "Override so certain ore dictionary give specific materials"),
    metalMelting(Module.metalworking, ConfigUtils.getDefaultMetalMelting(Constants.metalTypes),
        "The melting temperature for the correspond metals."),
    metalLiquids(Module.metalworking, ConfigUtils.getDefaultMetalFluid(Constants.metalTypes),
        "The liquids that will be used for the corresponding metals"),
    metalHighTierCasting(Module.metalworking, false,
        "Whether to enable high tier metals being able to be casted in a crucible."),
    metalOreValue(Module.metalworking, 36, "The value that one ore should give."),
    metalSmallOreValue(Module.metalworking, 16, "The value that one small ore should give."),
    metalIngotValue(Module.metalworking, 144, "The value that one ingot of metal should give."),
    metalVanillaToolValue(Module.metalworking, false,
        "Put to true if you want the casting to be the actual values of ingots in vanilla tools instead of 1 ingot"),
    metalNuggetValue(Module.metalworking, 16, "The value that one nugget of metal should give."),

    // Forging
    anvilActionStep(Module.metalworking$forging, ConfigUtils.getDefaultAnvilStep(),
        "The step value that the anvil action will take when clicked."),
    anvilLineRange(Module.metalworking$forging, 1,
        "The range that you need to be to the recipe line to complete an anvil recipe."),

    // Waterwheel
    waterwheelDefaultSpeed(Module.mechanical_power, 5F, "The default speed that the waterwheel will have."),
    waterwheelRestrictBiome(Module.mechanical_power, false,
        "Whether waterwheel should be limited to River/Oceans biomes."),

    // Windmill
    windmillDefaultSpeed(Module.mechanical_power, 5F, "The default speed that the windmill will have."),
    windmillIdealHeight(Module.mechanical_power, 100, 0, 256, "The height which the windmill will spin the fastest."),
    windmillRange(Module.mechanical_power, 60, 0, 256,
        "The range around the ideal height in which the windmill will operate."),

    // WorldGen
    rockWorldGen(Module.world$generation, true, "Whether to enable loose rocks generating in the world."),
    shellWorldGen(Module.world$generation, true, "Whether to enable loose sheel generating bear beaches."),

    // Strata
    strataStoneTypes(Module.world, true, "Put to false if you want to disable all of the stone types of the mod."),
    strataBiomeSpecific(Module.world$generation, true,
        "Put to false if you don't want biome-specific stones (ex. Desert will have sandstone, BOP Volcanos have basalt) etc.."),
    strataWorldGen(Module.world$generation, true, "Whether to enable the generation of the strata stone types"),
    soilTypes(Module.world, true, "Put to false if you want to disable all of the soil types of the mod."),
    soilWorldGen(Module.world$generation, true, "Whether to enable the generation of the soil types"),
    enableVanillaOres(Module.world$generation, false, "Put to true if you want the vanilla ores back"),
    oreVeins(Module.world, true, "Whether to enable large ore veins"),
    oreVeinsWorldGen(Module.world$generation, true, "Whether to enable the generation of the ore types"),
    oreVeinsHeightRange(Module.world$generation, ConfigUtils.getDefaultHeight(Constants.veinTypesAll),
        "The corresponding min/max Y Value that the ores will be able to spawn."),
    oreVeinsSizeRange(Module.world$generation, ConfigUtils.getDefaultSize(Constants.veinTypesAll),
        "The corresponding min/max size values that the ore veins will randomly generate between these values."),
    oreVeinsSizeClamp(Module.world$generation, true,
        "Clamp the veins to be maximum 8 blocks in size to prevent crashes/cascading worldgen. Enable at your own risk if you want huge veins."),
    thaumcraftOreVeins(Module.world, true, "Whether to enable large ore veins for Thaumcraft"),
    thaumcraftOreVeinsWorldGen(Module.world$generation, true,
        "Whether to enable the generation of the ore types for Thaumcraft"),

    // Mixins
    dragonAPIPlantFix(Module.world$mod_compat, true,
        "This enables the mixins to DragonAPI to make it recognize the mod dirt, and avoid plants popping off."),
    bopPlantFix(Module.world$mod_compat, true,
        "This enables the mixins to Biomes O' Plenty to make it recognize the mod dirt, and avoid plants popping off."),
    exblPlantFix(Module.world$mod_compat, true,
        "This enables the mixins to Extra Biomes XL to make it recognize the mod dirt, and avoid plants popping off.");

    public boolean isBoolean;
    public boolean isInt;
    public boolean isFloat;
    public boolean isStringList;
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
    String[] slValue;
    String[] slDefault;

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

    Config(Module category, String[] defaultList, String comment) {
        this.isStringList = true;
        this.name = this.name();
        this.category = category.name;
        this.comment = comment;
        this.slDefault = defaultList;
        this.slValue = slDefault;
    }

    public boolean getBoolean() {
        if (!this.isBoolean) {
            throw new NullPointerException();
        }
        return bValue;
    }

    public boolean getDefaultBoolean() {
        if (!this.isBoolean) {
            throw new NullPointerException();
        }
        return bDefault;
    }

    public int getInt() {
        if (!this.isInt) {
            throw new NullPointerException();
        }
        return iValue;
    }

    public int getDefaultInt() {
        if (!this.isInt) {
            throw new NullPointerException();
        }
        return iDefault;
    }

    public float getFloat() {
        if (!this.isFloat) {
            throw new NullPointerException();
        }
        return fValue;
    }

    public float getDefaultFloat() {
        if (!this.isFloat) {
            throw new NullPointerException();
        }
        return fDefault;
    }

    public int getChance() {
        if (!this.isFloat) {
            throw new NullPointerException();
        }
        return (int) Math.floor(1 / getFloat());
    }

    public String[] getStringList() {
        if (!this.isStringList) {
            throw new NullPointerException();
        }
        return slValue;
    }

    public static Configuration configuration;

    public static void synchronizeConfiguration(File configFile) {
        configuration = new Configuration(configFile);
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
            if (config.isStringList) {
                config.slValue = configuration
                    .getStringList(config.name, config.category, config.slDefault, config.comment);
            }
        }
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static enum Module {

        early_game(true,
            "Includes all the early game stuff such as campfire, pitkiln and everything you will use early on."),
        early_game$tweaks(true, "Multiple vanilla tweaks to spice up the early game."),
        early_game$lighting(true, "Includes all of the changes related to lighting."),
        metalworking(true, "Includes all of the metalworking aspect of the mod, such as the forge, crucible and such."),
        metalworking$forging(true, "Includes all options for the Anvil Forging minigame"),
        mechanical_power(true,
            "Includes all of the mechanical power machinery, such as windmill, waterwheel and anything that moves."),
        world(true, "Includes all of the world stuff from the mod."),
        world$generation(true, "All options related to world generation"),
        world$mod_compat(true, "Includes all of the changes made to other mods to make the mod work correctly.");

        public String name;
        public String comment;
        public boolean isMainModule;
        boolean enabled;
        boolean enabledDefault;

        Module(boolean enabled, String comment) {
            this.name = this.name()
                .replace("$", ".");
            this.isMainModule = this.name.indexOf('.') == -1;
            this.enabledDefault = enabled;
            this.comment = comment;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }
}
