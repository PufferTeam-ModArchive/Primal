package net.pufferlab.primal.client.gui.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class GuiConfigPrimal extends GuiConfig {

    public GuiConfigPrimal(GuiScreen parent) {
        super(parent, getConfigElements(), Primal.MODID, false, true, Primal.MODID + ".cfg");
    }

    @SuppressWarnings("rawtypes")
    private static List<IConfigElement> getConfigElements() {
        Configuration cfg = Config.configuration;
        List<IConfigElement> config = new ArrayList<>();

        for (Config.Module module : Config.Module.values()) {
            if (module.isMainModule) {
                String name = module.name;
                ConfigCategory category = cfg.getCategory(name);
                category.setComment(module.comment);
                config.add(new ConfigElement(category));
            }
        }

        return config;
    }

}
