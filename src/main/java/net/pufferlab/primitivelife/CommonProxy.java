package net.pufferlab.primitivelife;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.pufferlab.primitivelife.inventory.container.ContainerKnapping;
import net.pufferlab.primitivelife.recipes.KnappingType;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

    public static final int GUI_KNAPPING_CLAY = 1;
    public static final int GUI_KNAPPING_STRAW = 2;
    public static final int GUI_KNAPPING_FLINT = 3;

    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        PrimitiveLife.LOG.info(Config.greeting);
    }

    public void registerRenders() {}

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {}

    public void serverStarting(FMLServerStartingEvent event) {}

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_KNAPPING_CLAY: {
                return new ContainerKnapping(KnappingType.clay, player.inventory);
            }
            case GUI_KNAPPING_STRAW: {
                return new ContainerKnapping(KnappingType.straw, player.inventory);
            }
            case GUI_KNAPPING_FLINT: {
                return new ContainerKnapping(KnappingType.flint, player.inventory);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public int getPitKilnRenderID() {
        return 0;
    }
}
