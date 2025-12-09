package net.pufferlab.primal;

import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.compat.nei.NEIConfig;
import net.pufferlab.primal.events.*;
import net.pufferlab.primal.scripts.ScriptRegistry;
import net.pufferlab.primal.scripts.ScriptRemove;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Primal.MODID, version = "0.2.0", name = Primal.MODNAME, acceptedMinecraftVersions = "[1.7.10]")
public class Primal {

    public static final String MODNAME = "Primal";
    public static final String MODID = "primal";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "net.pufferlab.primal.ClientProxy", serverSide = "net.pufferlab.primal.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(Primal.MODID)
    public static Primal instance;
    public static Registry registry = new Registry();
    public static ScriptRemove scriptRemove = new ScriptRemove();
    public static ScriptRegistry scriptRegistry = new ScriptRegistry();
    public static SimpleNetworkWrapper networkWrapper;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        registry.setup();
        registry.setupTiles();
        registry.setupFluids();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        proxy.setupRenders();
        scriptRemove.init();

        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Primal.MODID);
        networkWrapper.registerMessage(PacketSwingArm.class, PacketSwingArm.class, 0, Side.CLIENT);
        networkWrapper.registerMessage(PacketKnappingClick.class, PacketKnappingClick.class, 1, Side.SERVER);

        if (Loader.isModLoaded("NotEnoughItems")) {
            new NEIConfig().loadConfig();
        }

        registry.setupEvents();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        scriptRemove.postInit();
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
