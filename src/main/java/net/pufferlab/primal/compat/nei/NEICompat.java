package net.pufferlab.primal.compat.nei;

import static net.pufferlab.primal.compat.nei.IMCSenderGTNH.*;
import static net.pufferlab.primal.utils.ItemUtils.getModItem;

import java.awt.*;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.MetalType;
import net.pufferlab.primal.utils.Utils;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEICompat implements IConfigureNEI {

    @Override
    public void loadConfig() {
        if (Utils.isClient()) {
            if (Mods.gtnhnei.isLoaded()) {
                loadHandlersGTNH();
            }
            loadHandlers();
        }
    }

    public void loadHandlersGTNH() {
        for (String filter : ItemUtils.getHideFilter()) {
            API.hideItem(filter);
        }
        for (ItemStack stack : ItemUtils.getItemHideFilter()) {
            API.hideItem(stack);
        }
        loadGTNHIMC();
    }

    public void loadHandlers() {
        registerHandler(new NEIChoppingLogHandler());
        registerHandler(new NEIKnappingHandler());
        registerHandler(new NEIPitKilnHandler());
        registerHandler(new NEICampfireHandler());
        registerHandler(new NEIBarrelHandler());
        registerHandler(new NEITanningHandler());
        registerHandler(new NEIQuernHandler());
        registerHandler(new NEIMeltingHandler());
        registerHandler(new NEIAlloyingHandler());
        registerHandler(new NEICastingHandler());
        registerHandler(new NEIAnvilHandler());
    }

    public void registerHandler(TemplateRecipeHandler handler) {
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }

    public void loadGTNHIMC() {
        sendHandler(NEIKnappingHandler.class, getModItem("knapping_icon", 1), 3, 166, 110);
        sendHandler(NEIPitKilnHandler.class, getModItem("pit_kiln", 1), 4, 166, 65);
        sendHandler(NEIBarrelHandler.class, getModItem("barrel", 1), 4, 166, 65);
        sendHandler(NEIChoppingLogHandler.class, getModItem("chopping_log", 1), 4, 166, 65);
        sendHandler(NEICampfireHandler.class, getModItem("campfire", 1), 4, 166, 65);
        sendHandler(NEITanningHandler.class, getModItem("tanning_frame", 1), 4, 166, 65);
        sendHandler(NEIQuernHandler.class, getModItem("quern", 1), 4, 166, 65);
        sendHandler(NEIMeltingHandler.class, getModItem("crucible", 1), 4, 166, 65);
        sendHandler(NEIAlloyingHandler.class, getModItem("crucible", 1), 4, 166, 65);
        sendHandler(NEICastingHandler.class, getModItem("ingot_mold", 1), 4, 166, 65);
        sendHandler(NEIAnvilHandler.class, getModItem("iron_anvil", 1), 4, 166, 65);

        sendCatalyst(NEIChoppingLogHandler.class, getModItem("chopping_log", 1));
        sendCatalyst(NEIBarrelHandler.class, getModItem("barrel", 1));
        sendCatalyst(NEICampfireHandler.class, getModItem("campfire", 1));
        sendCatalyst(NEICampfireHandler.class, getModItem("oven", 1));
        sendCatalyst(NEIPitKilnHandler.class, getModItem("pit_kiln", 1));
        sendCatalyst(NEITanningHandler.class, getModItem("tanning_frame", 1));
        sendCatalyst(NEIQuernHandler.class, getModItem("quern", 1));
        sendCatalyst(NEIQuernHandler.class, getModItem("handstone", 1));
        sendCatalyst(NEIMeltingHandler.class, getModItem("crucible", 1));
        for (MetalType metal : Constants.anvilMetalTypes) {
            sendCatalyst(NEIAnvilHandler.class, getModItem(metal.name + "_anvil", 1));
        }

        sendOrder(NEIChoppingLogHandler.class, 1);
        sendOrder(NEIKnappingHandler.class, 1);
        sendOrder(NEIPitKilnHandler.class, 1);
        sendOrder(NEIBarrelHandler.class, 1);
        sendOrder(NEICampfireHandler.class, 1);
        sendOrder(NEIAlloyingHandler.class, 1);
        sendOrder(NEIAnvilHandler.class, 1);
        sendOrder(NEICastingHandler.class, 1);
        sendOrder(NEIMeltingHandler.class, 1);
        sendOrder(NEIQuernHandler.class, 1);
        sendOrder(NEITanningHandler.class, 1);
    }

    public static boolean isHovering(PositionedStack stack, GuiRecipe<?> gui, int recipeId) {
        if (stack == null) return false;

        return isHovering(stack.relx, stack.rely, gui, recipeId);
    }

    public static boolean isHovering(int relX, int relY, GuiRecipe<?> gui, int recipeId) {
        Point mouse = GuiDraw.getMousePosition();
        Point offset = gui.getRecipePosition(recipeId);

        int mouseX = mouse.x + 1;
        int mouseY = mouse.y + 1;
        int x = gui.guiLeft + offset.x + relX;
        int y = gui.guiTop + offset.y + relY;

        return mouseX >= x && mouseX < x + 18 && mouseY >= y && mouseY < y + 18;
    }

    public static boolean transferFluid(boolean usage, FluidStack stack) {
        if (usage) {
            return GuiUsageRecipe.openRecipeGui("liquid", stack);
        } else {
            return GuiCraftingRecipe.openRecipeGui("liquid", stack);
        }
    }

    public static String translateNEI(String handler) {
        return Utils.translate("nei.recipe.primal." + handler + ".name");
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
