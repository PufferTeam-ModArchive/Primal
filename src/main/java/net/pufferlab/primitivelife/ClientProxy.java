package net.pufferlab.primitivelife;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.pufferlab.primitivelife.client.render.BlockPitKilnRenderer;
import net.pufferlab.primitivelife.client.render.TileEntityPitKilnRenderer;
import net.pufferlab.primitivelife.inventory.container.ContainerKnapping;
import net.pufferlab.primitivelife.inventory.gui.GuiKnapping;
import net.pufferlab.primitivelife.recipes.KnappingType;
import net.pufferlab.primitivelife.tileentities.TileEntityPitKiln;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    int pitKilnRenderID;

    public void registerRenders() {
        pitKilnRenderID = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler(new BlockPitKilnRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPitKiln.class, new TileEntityPitKilnRenderer());
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_KNAPPING_CLAY:
                return new GuiKnapping(new ContainerKnapping(KnappingType.clay, player.inventory));
            case GUI_KNAPPING_STRAW:
                return new GuiKnapping(new ContainerKnapping(KnappingType.straw, player.inventory));
            case GUI_KNAPPING_FLINT:
                return new GuiKnapping(new ContainerKnapping(KnappingType.flint, player.inventory));
        }
        return null;
    }

    @Override
    public int getPitKilnRenderID() {
        return pitKilnRenderID;
    }

}
