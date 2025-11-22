package net.pufferlab.primal.compat.nei;

import static net.pufferlab.primal.compat.nei.IMCSenderGTNH.sendHandler;

import net.pufferlab.primal.Primal;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.FMLCommonHandler;

public class NEIConfig implements IConfigureNEI {

    public static final NEIKnappingHandler knappingHandler = new NEIKnappingHandler();
    public static final NEIPitKilnHandler pitKilnHandler = new NEIPitKilnHandler();

    @Override
    public void loadConfig() {
        if (FMLCommonHandler.instance()
            .getSide()
            .isClient()) {
            API.registerRecipeHandler(knappingHandler);
            API.registerRecipeHandler(pitKilnHandler);
        }
        loadGTNH();
    }

    public void loadGTNH() {
        sendHandler(
            "net.pufferlab.primal.compat.nei.NEIKnappingHandler",
            "minecraft:flint",
            1,
            Primal.MODNAME,
            Primal.MODID,
            166,
            110);
        sendHandler(
            "net.pufferlab.primal.compat.nei.NEIPitKilnHandler",
            Primal.MODID + ":thatch",
            2,
            Primal.MODNAME,
            Primal.MODID,
            166,
            65);
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
