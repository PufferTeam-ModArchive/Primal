package net.pufferlab.primal.compat.nei;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Primal;

import cpw.mods.fml.common.event.FMLInterModComms;

public class IMCSenderGTNH {

    /*
     * These were copied from GTNewHorizons/GoodGenerator (Fork of GlodBlock/GoodGenerator)
     * Author: GlodBlock
     */

    public static void sendHandler(String aName, String aBlock) {
        sendHandler(aName, aBlock, 1, Primal.MODNAME, Primal.MODID, 166, 65);
    }

    public static void sendHandler(String aName, String aBlock, int maxRecipesPerPage, String modName, String modID,
        int width, int height) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", aName);
        aNBT.setString("modName", modName);
        aNBT.setString("modId", modID);
        aNBT.setBoolean("modRequired", true);
        aNBT.setString("itemName", aBlock);
        aNBT.setInteger("handlerHeight", height);
        aNBT.setInteger("handlerWidth", width);
        aNBT.setInteger("maxRecipesPerPage", maxRecipesPerPage);
        aNBT.setInteger("yShift", 0);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);
    }

    public static void sendCatalyst(String aName, String aStack, int aPriority) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handlerID", aName);
        aNBT.setString("itemName", aStack);
        aNBT.setInteger("priority", aPriority);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
    }

    public static void sendCatalyst(String aName, String aStack) {
        sendCatalyst(aName, aStack, 0);
    }
}
