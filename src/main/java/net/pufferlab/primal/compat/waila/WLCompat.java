package net.pufferlab.primal.compat.waila;

import net.pufferlab.primal.Mods;
import net.pufferlab.primal.tileentities.*;

import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;

@SuppressWarnings("deprecation")
public class WLCompat {

    public static void register(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WLBarrelHandler(), TileEntityBarrel.class);
        registrar.registerBodyProvider(new WLHeatHandler(), TileEntityCrucible.class);
        registrar.registerBodyProvider(new WLHeatHandler(), TileEntityForge.class);
        registrar.registerBodyProvider(new WLHeatHandler(), TileEntityCast.class);
        registrar.registerBodyProvider(new WLCampfireHandler(), TileEntityCampfire.class);
        registrar.registerBodyProvider(new WLPitKilnHandler(), TileEntityPitKiln.class);
        registrar.registerBodyProvider(new WLLargeVesselHandler(), TileEntityLargeVessel.class);
        registrar.registerBodyProvider(new WLQuernHandler(), TileEntityQuern.class);
    }

    public void loadConfig() {
        FMLInterModComms.sendMessage(Mods.wl.MODID, "register", WLCompat.class.getName() + ".register");
    }
}
