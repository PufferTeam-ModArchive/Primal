package net.pufferlab.primal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.inventory.container.ContainerKnapping;
import net.pufferlab.primal.inventory.container.ContainerLargeVessel;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.tileentities.TileEntityLargeVessel;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

    public final int largeVesselContainerID = 0;

    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());
    }

    public void registerRenders() {}

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {}

    public void serverStarting(FMLServerStartingEvent event) {}

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        KnappingType knappingType = KnappingType.getHandler(ID);
        if (knappingType != null) {
            return new ContainerKnapping(knappingType, player.inventory);
        }
        if (ID == largeVesselContainerID) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TileEntityLargeVessel tef) {
                return new ContainerLargeVessel(player.inventory, tef);
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

    public int getLogPileRenderID() {
        return 0;
    }

    public int getCharcoalPileRenderID() {
        return 0;
    }

    public int getAshPileRenderID() {
        return 0;
    }

    public int getCampfireRenderID() {
        return 0;
    }

    public int getLargeVesselRenderID() {
        return 0;
    }

    public int getBarrelRenderID() {
        return 0;
    }

    public int getFaucetRenderID() {
        return 0;
    }
}
