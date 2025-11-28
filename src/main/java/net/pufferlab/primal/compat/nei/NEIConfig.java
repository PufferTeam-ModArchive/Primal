package net.pufferlab.primal.compat.nei;

import static net.pufferlab.primal.compat.nei.IMCSenderGTNH.*;

import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.FMLCommonHandler;

public class NEIConfig implements IConfigureNEI {

    public static final NEIKnappingHandler knappingHandler = new NEIKnappingHandler();
    public static final NEIPitKilnHandler pitKilnHandler = new NEIPitKilnHandler();
    public static final NEIChoppingLogHandler choppingLogHandler = new NEIChoppingLogHandler();
    public static final NEICampfireHandler campfireHandler = new NEICampfireHandler();

    @Override
    public void loadConfig() {
        if (FMLCommonHandler.instance()
            .getSide()
            .isClient()) {
            API.registerRecipeHandler(choppingLogHandler);
            API.registerRecipeHandler(knappingHandler);
            API.registerRecipeHandler(pitKilnHandler);
            API.registerRecipeHandler(campfireHandler);
        }
        loadGTNH();
    }

    public void loadGTNH() {
        sendHandler(
            "net.pufferlab.primal.compat.nei.NEIKnappingHandler",
            Primal.MODID + ":icon:" + Utils.getItemFromArray(Constants.icons, "knapping"),
            1,
            Primal.MODNAME,
            Primal.MODID,
            166,
            110);
        sendHandler(
            "net.pufferlab.primal.compat.nei.NEIPitKilnHandler",
            Primal.MODID + ":icon:" + Utils.getItemFromArray(Constants.icons, "pit_kiln"),
            2,
            Primal.MODNAME,
            Primal.MODID,
            166,
            65);
        sendHandler(
            "net.pufferlab.primal.compat.nei.NEIChoppingLogHandler",
            Primal.MODID + ":chopping_log",
            2,
            Primal.MODNAME,
            Primal.MODID,
            166,
            65);
        sendHandler(
            "net.pufferlab.primal.compat.nei.NEICampfireHandler",
            Primal.MODID + ":icon:" + Utils.getItemFromArray(Constants.icons, "campfire"),
            2,
            Primal.MODNAME,
            Primal.MODID,
            166,
            65);
        sendCatalyst("primal.chopping_log", Primal.MODID + ":chopping_log");
    }

    @Override
    public String getName() {
        return Primal.MODNAME + " NEI Plugin";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
