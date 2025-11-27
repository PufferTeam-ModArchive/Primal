package net.pufferlab.primal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.pufferlab.primal.client.renderer.*;
import net.pufferlab.primal.inventory.container.ContainerKnapping;
import net.pufferlab.primal.inventory.gui.GuiKnapping;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.tileentities.TileEntityCampfire;
import net.pufferlab.primal.tileentities.TileEntityChoppingLog;
import net.pufferlab.primal.tileentities.TileEntityPitKiln;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    int pitKilnRenderID;
    int logPileRenderID;
    int charcoalPileRenderID;
    int ashPileRenderID;
    int campfireRenderID;

    public void registerRenders() {
        pitKilnRenderID = RenderingRegistry.getNextAvailableRenderId();
        logPileRenderID = RenderingRegistry.getNextAvailableRenderId();
        charcoalPileRenderID = RenderingRegistry.getNextAvailableRenderId();
        ashPileRenderID = RenderingRegistry.getNextAvailableRenderId();
        campfireRenderID = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler(new BlockPitKilnRenderer());
        RenderingRegistry.registerBlockHandler(new BlockLogPileRenderer());
        RenderingRegistry.registerBlockHandler(new BlockCharcoalPileRenderer());
        RenderingRegistry.registerBlockHandler(new BlockAshPileRenderer());
        RenderingRegistry.registerBlockHandler(new BlockCampfireRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPitKiln.class, new TileEntityPitKilnRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChoppingLog.class, new TileEntityChoppingLogRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampfire.class, new TileEntityCampfireRenderer());

        MinecraftForgeClient.registerItemRenderer(Registry.wood, new ItemWoodRenderer());
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        KnappingType knappingType = KnappingType.getHandler(ID);
        if (knappingType != null) {
            return new GuiKnapping(new ContainerKnapping(knappingType, player.inventory));
        }
        return null;
    }

    @Override
    public int getPitKilnRenderID() {
        return pitKilnRenderID;
    }

    @Override
    public int getLogPileRenderID() {
        return logPileRenderID;
    }

    @Override
    public int getCharcoalPileRenderID() {
        return charcoalPileRenderID;
    }

    @Override
    public int getAshPileRenderID() {
        return ashPileRenderID;
    }

    @Override
    public int getCampfireRenderID() {
        return campfireRenderID;
    }
}
