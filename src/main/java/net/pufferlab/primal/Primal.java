package net.pufferlab.primal;

import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.events.*;
import net.pufferlab.primal.scripts.ScriptRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Primal.MODID, version = Tags.VERSION, name = Primal.MODNAME, acceptedMinecraftVersions = "[1.7.10]")
public class Primal {

    public static final String MODNAME = "Primal";
    public static final String MODID = "primal";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "net.pufferlab.primal.ClientProxy", serverSide = "net.pufferlab.primal.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(Primal.MODID)
    public static Primal instance;

    public static Registry registry = new Registry();
    public static ScriptRegistry scriptRegistry = new ScriptRegistry();

    public static SimpleNetworkWrapper networkWrapper;

    public static boolean EFRLoaded = Loader.isModLoaded("etfuturum");
    public static boolean NEILoaded = Loader.isModLoaded("NotEnoughItems");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        registry.setup();
        registry.setupTiles();
        registry.setupFluids();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        proxy.setupRenders();

        registry.setupPackets();
        registry.setupNEI();

        registry.setupEvents();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void completeInit(FMLLoadCompleteEvent event) {
        scriptRegistry.run();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}
