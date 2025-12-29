package net.pufferlab.primal.compat.nei;

import static net.pufferlab.primal.compat.nei.IMCSenderGTNH.*;

import java.awt.*;

import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

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
            Primal.MODID + ":tanning_frame",
            2,
            Primal.MODNAME,
            Primal.MODID,
            166,
            65);
        sendCatalyst(Primal.MODID + ".chopping_log", Primal.MODID + ":chopping_log");
        sendCatalyst(Primal.MODID + ".barrel", Primal.MODID + ":barrel");
        sendCatalyst(Primal.MODID + ".campfire", Primal.MODID + ":campfire");
        sendCatalyst(Primal.MODID + ".campfire", Primal.MODID + ":oven");
        sendCatalyst(Primal.MODID + ".pit_kiln", Primal.MODID + ":pit_kiln");
        sendCatalyst(Primal.MODID + ".tanning", Primal.MODID + ":tanning_frame");
    }

    public static boolean isHovering(PositionedStack stack, GuiRecipe gui, int recipeId) {
        if (stack == null) return false;

        return isHovering(stack.relx, stack.rely, gui, recipeId);
    }

    public static boolean isHovering(int relX, int relY, GuiRecipe gui, int recipeId) {
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

    @Override
    public String getName() {
        return Primal.MODNAME + " NEI Plugin";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
