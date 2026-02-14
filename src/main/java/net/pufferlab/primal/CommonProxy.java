package net.pufferlab.primal;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.inventory.*;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.tileentities.TileEntityAnvil;
import net.pufferlab.primal.tileentities.TileEntityCrucible;
import net.pufferlab.primal.tileentities.TileEntityLargeVessel;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements IGuiHandler {

    public MinecraftServer server;

    public final int largeVesselGuiID = 0;
    public final int crucibleGuiID = 1;
    public final int generatorGuiID = 2;
    public final int anvilWorkGuiID = 3;
    public final int anvilPlanGuiID = 4;

    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Primal.instance, Primal.proxy);
    }

    public void setupRenders() {}

    public void setupResources() {}

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {}

    public void serverStarting(FMLServerStartingEvent event) {
        server = event.getServer();
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        KnappingType knappingType = KnappingType.getHandler(ID);
        if (knappingType != null) {
            return new ContainerKnapping(knappingType, player.inventory);
        }
        TileEntity te = world.getTileEntity(x, y, z);
        if (ID == largeVesselGuiID && te instanceof TileEntityLargeVessel tef) {
            return new ContainerLargeVessel(player.inventory, tef);
        }
        if (ID == crucibleGuiID && te instanceof TileEntityCrucible tef) {
            return new ContainerCrucible(player.inventory, tef);
        }
        if (ID == anvilWorkGuiID && te instanceof TileEntityAnvil tef) {
            return new ContainerAnvilWork(tef);
        }
        if (ID == anvilPlanGuiID && te instanceof TileEntityAnvil tef) {
            return new ContainerAnvilPlan(player.inventory, tef);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public World getOverworld() {
        return server.worldServers[0];
    }

    public World getClientWorld() {
        return null;
    }

    public World getWorld(MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            return ctx.getServerHandler().playerEntity.getEntityWorld();
        }
        if (ctx.side == Side.CLIENT) {
            return getClientWorld();
        }
        return null;
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public void playClientSound(TileEntity te) {}

    public void renderFX(World world, int x, int y, int z, Block block, int meta) {
        world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
    }

    public void renderFX(TileEntity te, double x, double y, double z, ItemStack stack) {}

    public <T extends IMessage> void sendPacketToClient(T object) {
        Primal.network.sendToAll(object);
    }

    public <T extends IMessage> void sendPacketToServer(T object) {};

    public void openLargeVesselGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        openPrimalGui(largeVesselGuiID, player, worldIn, x, y, z);
    }

    public void openCrucibleGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        openPrimalGui(crucibleGuiID, player, worldIn, x, y, z);
    }

    public void openGeneratorGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        openPrimalGui(generatorGuiID, player, worldIn, x, y, z);
    }

    public void openAnvilWorkGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        openPrimalGui(anvilWorkGuiID, player, worldIn, x, y, z);
    }

    public void openAnvilPlanGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        openPrimalGui(anvilPlanGuiID, player, worldIn, x, y, z);
    }

    public void openPrimalGui(int containerID, EntityPlayer player, World worldIn, int x, int y, int z) {
        player.openGui(Primal.instance, containerID, worldIn, x, y, z);
    }

    public int getSlabRenderID() {
        return 0;
    }

    public int getStairsRenderID() {
        return 0;
    }

    public int getGrassRenderID() {
        return 0;
    }

    public int getPathRenderID() {
        return 0;
    }

    public int getOreRenderID() {
        return 0;
    }

    public int getPitKilnRenderID() {
        return 0;
    }

    public int getLogPileRenderID() {
        return 0;
    }

    public int getCharcoalPileRenderID() {
        return 0;
    }

    public int getAshPileRenderID() {
        return 0;
    }

    public int getCampfireRenderID() {
        return 0;
    }

    public int getLargeVesselRenderID() {
        return 0;
    }

    public int getBarrelRenderID() {
        return 0;
    }

    public int getFaucetRenderID() {
        return 0;
    }

    public int getGroundcoverRenderID() {
        return 0;
    }

    public int getTanningRenderID() {
        return 0;
    }

    public int getOvenRenderID() {
        return 0;
    }

    public int getChimneyRenderID() {
        return 0;
    }

    public int getCrucibleRenderID() {
        return 0;
    }

    public int getForgeRenderID() {
        return 0;
    }

    public int getCastRenderID() {
        return 0;
    }

    public int getQuernRenderID() {
        return 0;
    }

    public int getAxleRenderID() {
        return 0;
    }

    public int getGeneratorRenderID() {
        return 0;
    }

    public int getAnvilRenderID() {
        return 0;
    }
}
