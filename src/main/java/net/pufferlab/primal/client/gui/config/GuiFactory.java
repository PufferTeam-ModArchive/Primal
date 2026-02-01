package net.pufferlab.primal.client.gui.config;

import java.util.Set;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.client.IModGuiFactory;

public class GuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public Class<? extends net.minecraft.client.gui.GuiScreen> mainConfigGuiClass() {
        return GuiConfigPrimal.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }
}
