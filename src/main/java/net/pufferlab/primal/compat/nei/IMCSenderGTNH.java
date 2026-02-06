package net.pufferlab.primal.compat.nei;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.ItemUtils;

import cpw.mods.fml.common.event.FMLInterModComms;

public class IMCSenderGTNH {

    /*
     * These were copied from GTNewHorizons/GoodGenerator (Fork of GlodBlock/GoodGenerator)
     * Author: GlodBlock
     */

    public static void sendHandler(String aName, ItemStack aBlock) {
        sendHandler(aName, aBlock, 1, Primal.MODNAME, Primal.MODID, 166, 65);
    }

    public static void sendHandler(Class<?> aName, ItemStack aStack, int maxRecipesPerPage, int width, int height) {
        sendHandler(aName.getName(), aStack, maxRecipesPerPage, Primal.MODNAME, Primal.MODID, width, height);
    }

    public static void sendHandler(String aName, ItemStack aStack, int maxRecipesPerPage, String modName, String modID,
        int width, int height) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", aName);
        aNBT.setString("modName", modName);
        aNBT.setString("modId", modID);
        aNBT.setBoolean("modRequired", true);
        aNBT.setString("itemName", ItemUtils.getName(aStack) + ":" + aStack.getItemDamage());
        aNBT.setInteger("handlerHeight", height);
        aNBT.setInteger("handlerWidth", width);
        aNBT.setInteger("maxRecipesPerPage", maxRecipesPerPage);
        aNBT.setInteger("yShift", 0);
        FMLInterModComms.sendMessage(Mods.nei.MODID, "registerHandlerInfo", aNBT);
    }

    public static void sendCatalyst(String aName, ItemStack aStack, int aPriority) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handlerID", aName);
        aNBT.setString("itemName", ItemUtils.getName(aStack) + ":" + aStack.getItemDamage());
        aNBT.setInteger("priority", aPriority);
        FMLInterModComms.sendMessage(Mods.nei.MODID, "registerCatalystInfo", aNBT);
    }

    public static void sendCatalyst(String aName, ItemStack aStack) {
        sendCatalyst(aName, aStack, 0);
    }
}
