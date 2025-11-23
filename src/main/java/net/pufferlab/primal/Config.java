package net.pufferlab.primal;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static String CATEGORY_TECHNICAL = "technical";
    public static boolean enableTechnical;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

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
