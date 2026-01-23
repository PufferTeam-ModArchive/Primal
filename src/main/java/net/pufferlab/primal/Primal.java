package net.pufferlab.primal;

import static net.pufferlab.primal.Constants.*;

import net.pufferlab.primal.scripts.ScriptRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(
    modid = Primal.MODID,
    version = Tags.VERSION,
    name = Primal.MODNAME,
    guiFactory = guiFactory,
    acceptedMinecraftVersions = version)
public class Primal {

    public static final String MODNAME = "Primal";
    public static final String MODID = "primal";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = clientProxy, serverSide = commonProxy)
    public static CommonProxy proxy;

    @Mod.Instance(Primal.MODID)
    public static Primal instance;

    public static boolean debugMode = false;

    public static Registry registry = new Registry();
    public static ScriptRegistry scriptRegistry = new ScriptRegistry();

    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        registry.setup();
        registry.setupTiles();
        registry.setupFluids();
        registry.setupWorldGen();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        proxy.setupResources();

        registry.setupPackets();
        registry.setupNEI();
        registry.setupMT();
        registry.setupWAILA();
        registry.setupHeatables();
        registry.setupMetals();
        registry.setupModCompat();

        if (debugMode && Utils.isDev()) {
            registry.setupDebug();
        }

        proxy.setupRenders();

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
        registry.setupCommands();
    }
}
