package net.pufferlab.primal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.pufferlab.primal.client.renderer.*;
import net.pufferlab.primal.inventory.container.ContainerKnapping;
import net.pufferlab.primal.inventory.gui.GuiKnapping;
import net.pufferlab.primal.inventory.gui.GuiLargeVessel;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.tileentities.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    int pitKilnRenderID;
    int logPileRenderID;
    int charcoalPileRenderID;
    int ashPileRenderID;
    int campfireRenderID;
    int largeVesselRenderID;
    int barrelRenderID;
    int faucetRenderID;
    int groundcoverRenderID;
    int tanningRenderID;
    int ovenRenderID;
    int chimneyRenderID;

    @Override
    public void setupRenders() {
        pitKilnRenderID = RenderingRegistry.getNextAvailableRenderId();
        logPileRenderID = RenderingRegistry.getNextAvailableRenderId();
        charcoalPileRenderID = RenderingRegistry.getNextAvailableRenderId();
        ashPileRenderID = RenderingRegistry.getNextAvailableRenderId();
        campfireRenderID = RenderingRegistry.getNextAvailableRenderId();
        largeVesselRenderID = RenderingRegistry.getNextAvailableRenderId();
        barrelRenderID = RenderingRegistry.getNextAvailableRenderId();
        faucetRenderID = RenderingRegistry.getNextAvailableRenderId();
        groundcoverRenderID = RenderingRegistry.getNextAvailableRenderId();
        tanningRenderID = RenderingRegistry.getNextAvailableRenderId();
        ovenRenderID = RenderingRegistry.getNextAvailableRenderId();
        chimneyRenderID = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler(new BlockPitKilnRenderer());
        RenderingRegistry.registerBlockHandler(new BlockLogPileRenderer());
        RenderingRegistry.registerBlockHandler(new BlockCharcoalPileRenderer());
        RenderingRegistry.registerBlockHandler(new BlockAshPileRenderer());
        RenderingRegistry.registerBlockHandler(new BlockCampfireRenderer());
        RenderingRegistry.registerBlockHandler(new BlockLargeVesselRenderer());
        RenderingRegistry.registerBlockHandler(new BlockBarrelRenderer());
        RenderingRegistry.registerBlockHandler(new BlockFaucetRenderer());
        RenderingRegistry.registerBlockHandler(new BlockGroundcoverRenderer());
        RenderingRegistry.registerBlockHandler(new BlockTanningRenderer());
        RenderingRegistry.registerBlockHandler(new BlockOvenRenderer());
        RenderingRegistry.registerBlockHandler(new BlockChimneyRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPitKiln.class, new TileEntityPitKilnRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChoppingLog.class, new TileEntityChoppingLogRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampfire.class, new TileEntityCampfireRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBarrel.class, new TileEntityBarrelRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTanning.class, new TileEntityTanningRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new TileEntityOvenRenderer());

        MinecraftForgeClient.registerItemRenderer(Registry.wood, new ItemWoodRenderer());
        MinecraftForgeClient.registerItemRenderer(Registry.clay, new ItemClayRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Registry.barrel), new ItemBarrelRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Registry.faucet), new ItemFaucetRenderer());
        MinecraftForgeClient
            .registerItemRenderer(Item.getItemFromBlock(Registry.large_vessel), new ItemLargeVesselRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Registry.oven), new ItemOvenRenderer());
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        KnappingType knappingType = KnappingType.getHandler(ID);
        if (knappingType != null) {
            return new GuiKnapping(new ContainerKnapping(knappingType, player.inventory));
        }
        if (ID == largeVesselContainerID) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TileEntityLargeVessel tef) {
                return new GuiLargeVessel(player.inventory, tef);
            }
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

    @Override
    public int getLargeVesselRenderID() {
        return largeVesselRenderID;
    }

    @Override
    public int getBarrelRenderID() {
        return barrelRenderID;
    }

    @Override
    public int getFaucetRenderID() {
        return faucetRenderID;
    }

    @Override
    public int getGroundcoverRenderID() {
        return groundcoverRenderID;
    }

    @Override
    public int getTanningRenderID() {
        return tanningRenderID;
    }

    @Override
    public int getOvenRenderID() {
        return ovenRenderID;
    }

    @Override
    public int getChimneyRenderID() {
        return chimneyRenderID;
    }
}
