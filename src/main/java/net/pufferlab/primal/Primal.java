package net.pufferlab.primal;

import net.pufferlab.primal.scripts.ScriptRegistry;
import net.pufferlab.primal.utils.Profiler;
import net.pufferlab.primal.utils.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(
    modid = Primal.MODID,
    version = Primal.VERSION,
    name = Primal.MODNAME,
    guiFactory = Primal.guiFactory,
    acceptedMinecraftVersions = Primal.mcVersion,
    dependencies = Primal.dependencies)
public class Primal {

    public static final String MODNAME = "Primal";
    public static final String MODID = "primal";
    public static final String VERSION = Tags.VERSION;
    public static final String mcVersion = "[1.7.10]";
    public static final String dependencies = "required-after:gtnhmixins@[2.0.0,);";
    public static final String group = "net.pufferlab.primal";
    public static final String guiFactory = group + ".client.gui.config.GuiFactory";
    public static final String clientProxy = group + ".ClientProxy";
    public static final String commonProxy = group + ".CommonProxy";
    public static final String earlyMixins = "mixins.primal.early.json";
    public static final String lateMixins = "mixins.primal.late.json";
    public static final String downloadPath = "https://github.com/PufferTeam-ModArchive/Primal/raw/refs/heads/main/builtin/";
    public static final String textureFile = "Primal-Modern-Resources";
    public static final Logger LOG = LogManager.getLogger(MODNAME);

    @SidedProxy(clientSide = clientProxy, serverSide = commonProxy)
    public static CommonProxy proxy;

    @Mod.Instance(Primal.MODID)
    public static Primal instance;

    public static boolean debugMode = false;

    public static final Registry registry = new Registry();
    public static final ScriptRegistry scriptRegistry = new ScriptRegistry();
    public static final Profiler profiler = new Profiler();

    public static SimpleNetworkWrapper network;

    public Primal() {
        Config.setupEarlyConfig();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        profiler.startProfile("PreInitialization");
        proxy.preInit(event);
        proxy.setupGUIs();
        proxy.setupResources();

        registry.setup();
        registry.setupTiles();
        registry.setupWorldGen();
        registry.setupBaubles();
        profiler.endProfile("PreInitialization");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        profiler.startProfile("Initialization");
        proxy.init(event);

        registry.setupConfig();
        registry.setupPackets();
        registry.setupNEI();
        registry.setupMT();
        registry.setupWAILA();
        registry.setupHeatables();
        registry.setupModCompat();

        if (debugMode && Utils.isDev()) {
            registry.setupDebug();
        }

        proxy.setupRenders();

        registry.setupEvents();
        profiler.endProfile("Initialization");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        profiler.startProfile("PostInitialization");
        proxy.postInit(event);
        profiler.endProfile("PostInitialization");
    }

    @Mod.EventHandler
    public void completeInit(FMLLoadCompleteEvent event) {
        profiler.startProfile("LoadComplete");
        scriptRegistry.runEarly();
        scriptRegistry.run();
        profiler.endProfile("LoadComplete");
        profiler.profileTotal();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        profiler.startProfile("ServerStarting");
        proxy.serverStarting(event);
        registry.setupServer();
        registry.setupCommands();
        profiler.endProfile("ServerStarting");
    }
}
