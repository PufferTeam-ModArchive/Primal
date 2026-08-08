package net.pufferlab.primal.compat.nei;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.ItemUtils;

import codechicken.nei.NEIClientConfig;
import codechicken.nei.recipe.TemplateRecipeHandler;
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

    public static void sendCatalyst(Class<?> aName, ItemStack aStack) {
        String name = getOverlayIdentifier(aName);
        sendCatalyst(name, aStack);
    }

    public static void sendCatalyst(String aName, ItemStack aStack) {
        sendCatalyst(aName, aStack, 0);
    }

    public static void sendOrder(Class<?> aName, int order) {
        String name = getOverlayIdentifier(aName);
        NEIClientConfig.handlerOrdering.put(name, order);
    }

    public static final Map<Class<?>, String> overlayMap = new HashMap<>();

    public static String getOverlayIdentifier(Class<?> aName) {
        String overlayName = overlayMap.get(aName);
        if (overlayName == null) {
            try {
                TemplateRecipeHandler t = (TemplateRecipeHandler) aName.getConstructor()
                    .newInstance();
                overlayName = t.getOverlayIdentifier();
                overlayMap.put(aName, overlayName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return overlayName;
    }
}
