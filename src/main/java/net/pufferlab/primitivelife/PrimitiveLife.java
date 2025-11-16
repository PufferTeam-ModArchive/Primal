package net.pufferlab.primitivelife;

import net.minecraftforge.common.MinecraftForge;
import net.pufferlab.primitivelife.compat.nei.NEIConfig;
import net.pufferlab.primitivelife.events.*;
import net.pufferlab.primitivelife.scripts.ScriptRegistry;

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

@Mod(modid = PrimitiveLife.MODID, version = "1.0", name = PrimitiveLife.MODNAME, acceptedMinecraftVersions = "[1.7.10]")
public class PrimitiveLife {

    public static final String MODNAME = "PrimitiveLife";
    public static final String MODID = "primitivelife";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(
        clientSide = "net.pufferlab.primitivelife.ClientProxy",
        serverSide = "net.pufferlab.primitivelife.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(PrimitiveLife.MODID)
    public static PrimitiveLife instance;
    public static Registry registry = new Registry();
    public static ScriptRegistry scriptRegistry = new ScriptRegistry();
    public static SimpleNetworkWrapper networkWrapper;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        registry.preInit();
        registry.preInitTE();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        proxy.registerRenders();
        registry.initOreDicts();

        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(PrimitiveLife.MODID);
        networkWrapper.registerMessage(PacketSwingArm.class, PacketSwingArm.class, 0, Side.CLIENT);
        networkWrapper.registerMessage(PacketKnappingClick.class, PacketKnappingClick.class, 1, Side.SERVER);

        if (Loader.isModLoaded("NotEnoughItems")) {
            new NEIConfig().loadConfig();
        }

        MinecraftForge.EVENT_BUS.register(new PitKilnHandler());
        MinecraftForge.EVENT_BUS.register(new KnappingHandler());
        MinecraftForge.EVENT_BUS.register(new BucketHandler());
        MinecraftForge.EVENT_BUS.register(new ToolHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        scriptRegistry.run();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
