package net.pufferlab.primal.compat.nei;

import static net.pufferlab.primal.compat.nei.IMCSenderGTNH.*;

import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIRegistry implements IConfigureNEI {

    @Override
    public void loadConfig() {
        API.hideItem("primal:icon");
        if (Utils.isClient()) {
            registerHandler(new NEIChoppingLogHandler());
            registerHandler(new NEIKnappingHandler());
            registerHandler(new NEIPitKilnHandler());
            registerHandler(new NEICampfireHandler());
            registerHandler(new NEIBarrelHandler());
            registerHandler(new NEITanningHandler());
        }
        loadGTNH();
    }

    public void registerHandler(TemplateRecipeHandler handler) {
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
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
            Primal.MODID + ":pit_kiln",
            2,
            Primal.MODNAME,
            Primal.MODID,
            166,
            65);
        sendHandler(
            "net.pufferlab.primal.compat.nei.NEIBarrelHandler",
            Primal.MODID + ":barrel",
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
            Primal.MODID + ":campfire",
            2,
            Primal.MODNAME,
            Primal.MODID,
            166,
            65);
        sendHandler(
            "net.pufferlab.primal.compat.nei.NEITanningHandler",
            Primal.MODID + ":tanning",
            2,
            Primal.MODNAME,
            Primal.MODID,
            166,
            65);
        sendCatalyst(Primal.MODID + ".chopping_log", Primal.MODID + ":chopping_log");
        sendCatalyst(Primal.MODID + ".barrel", Primal.MODID + ":barrel");
        sendCatalyst(Primal.MODID + ".campfire", Primal.MODID + ":campfire");
        sendCatalyst(Primal.MODID + ".pit_kiln", Primal.MODID + ":pit_kiln");
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
