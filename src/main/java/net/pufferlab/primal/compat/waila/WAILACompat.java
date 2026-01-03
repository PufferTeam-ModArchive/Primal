package net.pufferlab.primal.compat.waila;

import net.pufferlab.primal.tileentities.TileEntityBarrel;

import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;

@SuppressWarnings("deprecation")
public class WAILACompat {

    public static void register(IWailaRegistrar registrar) {

        registrar.registerBodyProvider(new WAILABarrelHandler(), TileEntityBarrel.class);
    }

    public static void loadConfig() {
        FMLInterModComms.sendMessage("Waila", "register", "net.pufferlab.primal.compat.waila.WAILACompat.register");
    }
}
