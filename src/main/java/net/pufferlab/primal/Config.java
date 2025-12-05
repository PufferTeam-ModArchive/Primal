package net.pufferlab.primal;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static String CATEGORY_TECHNICAL = "technical";
    public static boolean enableTechnical;
    public static int vanillaToolsRemovalMode;
    public static float stickDropChance;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        vanillaToolsRemovalMode = configuration.getInt(
            "vanillaToolsRemovalMode",
            Configuration.CATEGORY_GENERAL,
            1,
            0,
            2,
            "0: Don't remove vanilla tools. 1: Remove the recipes. 2: Keep the recipes but make tools unusable.");
        stickDropChance = configuration.getFloat(
            "leaveStickDropChance",
            Configuration.CATEGORY_GENERAL,
            0.166F,
            0.0F,
            1.0F,
            "The chance from 0 (0%) to 1 (100%) for a stick to drop from leaves. Putting this to 0 will stop dropping.");

        enableTechnical = configuration.getBoolean(
            "enableTechnicalChanges",
            CATEGORY_TECHNICAL,
            false,
            "Enable this if you want to change technical values in the mod, disabled by default so people don't have to refresh their config when I make a internal change. Keep this to false if you don't intend in changing the mod.");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
