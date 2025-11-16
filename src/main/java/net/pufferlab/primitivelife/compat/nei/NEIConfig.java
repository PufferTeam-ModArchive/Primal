package net.pufferlab.primitivelife.compat.nei;

import static net.pufferlab.primitivelife.compat.nei.IMCSenderGTNH.sendHandler;

import net.pufferlab.primitivelife.PrimitiveLife;

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
            "net.pufferlab.primitivelife.compat.nei.NEIKnappingHandler",
            "minecraft:flint",
            1,
            PrimitiveLife.MODNAME,
            PrimitiveLife.MODID,
            166,
            110);
        sendHandler(
            "net.pufferlab.primitivelife.compat.nei.NEIPitKilnHandler",
            PrimitiveLife.MODID + ":thatch",
            2,
            PrimitiveLife.MODNAME,
            PrimitiveLife.MODID,
            166,
            65);
    }

    @Override
    public String getName() {
        return "PrimitiveLife NEI Plugin";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
