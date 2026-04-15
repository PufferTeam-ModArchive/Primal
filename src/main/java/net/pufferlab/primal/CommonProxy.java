package net.pufferlab.primal;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.command.CommandHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.client.gui.*;
import net.pufferlab.primal.client.renderer.blocks.*;
import net.pufferlab.primal.inventory.*;
import net.pufferlab.primal.network.NetworkPacket;
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
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class CommonProxy implements IGuiHandler {

    public MinecraftServer server;
    public final NetworkPacket packet = new NetworkPacket();

    public BlockSlabRenderer slabRenderer;
    public BlockSlabVerticalRenderer slabVerticalRenderer;
    public BlockStairsRenderer stairsRenderer;
    public BlockWallRenderer wallRenderer;
    public BlockRopeLadderRenderer ropeLadderRenderer;
    public BlockGrassRenderer grassRenderer;
    public BlockPathRenderer pathRenderer;
    public BlockOreRenderer oreRenderer;
    public BlockPitKilnRenderer pitKilnRenderer;
    public BlockLogPileRenderer logPileRenderer;
    public BlockCharcoalPileRenderer charcoalPileRenderer;
    public BlockAshPileRenderer ashPileRenderer;
    public BlockCampfireRenderer campfireRenderer;
    public BlockLargeVesselRenderer largeVesselRenderer;
    public BlockBarrelRenderer barrelRenderer;
    public BlockFaucetRenderer faucetRenderer;
    public BlockGroundcoverRenderer groundcoverRenderer;
    public BlockTanningRenderer tanningRenderer;
    public BlockOvenRenderer ovenRenderer;
    public BlockChimneyRenderer chimneyRenderer;
    public BlockCrucibleRenderer crucibleRenderer;
    public BlockForgeRenderer forgeRenderer;
    public BlockCastRenderer castRenderer;
    public BlockQuernRenderer quernRenderer;
    public BlockAxleRenderer axleRenderer;
    public BlockGeneratorRenderer generatorRenderer;
    public BlockAnvilRenderer anvilRenderer;
    public BlockBloomeryRenderer bloomeryRenderer;
    public BlockCropsRenderer cropsRenderer;

    public ContainerLargeVessel largeVesselGui;
    public ContainerCrucible crucibleGui;
    public ContainerGenerator generatorGui;
    public ContainerAnvilWork anvilWorkGui;
    public ContainerAnvilPlan anvilPlanGui;

    public int nextGuiID;

    public void preInit(FMLPreInitializationEvent event) {}

    public TIntObjectMap<ContainerPrimal> guiMap = new TIntObjectHashMap<>();

    public void setupGUIs() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Primal.instance, Primal.proxy);

        largeVesselGui = new ContainerLargeVessel();
        crucibleGui = new ContainerCrucible();
        generatorGui = new ContainerGenerator();
        anvilWorkGui = new ContainerAnvilWork();
        anvilPlanGui = new ContainerAnvilPlan();

        register(largeVesselGui);
        register(crucibleGui);
        register(generatorGui);
        register(anvilWorkGui);
        register(anvilPlanGui);
    }

    public <T extends ContainerPrimal> void register(T object) {
        object.setGuiId(getNextGuiID());
        guiMap.put(object.getGuiId(), object);
    }

    public int getNextGuiID() {
        return nextGuiID++;
    }

    public int getLastGuiID() {
        return nextGuiID + 1;
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
        ContainerPrimal gui = getGui(ID);
        TileEntity te = world.getTileEntity(x, y, z);
        if (gui instanceof ContainerLargeVessel && te instanceof TileEntityLargeVessel tef) {
            return new ContainerLargeVessel(player.inventory, tef);
        }
        if (gui instanceof ContainerCrucible && te instanceof TileEntityCrucible tef) {
            return new ContainerCrucible(player.inventory, tef);
        }
        if (gui instanceof ContainerAnvilWork && te instanceof TileEntityAnvil tef) {
            return new ContainerAnvilWork(tef);
        }
        if (gui instanceof ContainerAnvilPlan && te instanceof TileEntityAnvil tef) {
            return new ContainerAnvilPlan(player.inventory, tef);
        }
        return null;
    }

    public ContainerPrimal getGui(int id) {
        return guiMap.get(id);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public CommandHandler getCommandHandler() {
        return (CommandHandler) getServer().getCommandManager();
    }

    public World getOverworld() {
        return server.worldServers[0];
    }

    public boolean isOverworld(World world) {
        return world.provider.dimensionId == 0;
    }

    public World getClientWorld() {
        return null;
    }

    public void renderFX(EntityFX entityFX) {}

    public World getWorld(MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            return ctx.getServerHandler().playerEntity.getEntityWorld();
        }
        if (ctx.side == Side.CLIENT) {
            return getClientWorld();
        }
        return null;
    }

    public World getWorldFromID(int id) {
        return server.worldServerForDimension(id);
    }

    public EntityPlayer getPlayer(MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            return ctx.getServerHandler().playerEntity;
        }
        if (ctx.side == Side.CLIENT) {
            return getClientPlayer();
        }
        return null;
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public int getClientRenderDistance() {
        return Integer.MAX_VALUE;
    }

    public int getClientMaxRenderDistanceSquared() {
        return Short.MAX_VALUE * 2;
    }

    public void playClientSound(TileEntity te) {}

    public void renderSFX(World world, int x, int y, int z, Block block, int meta) {
        world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
    }

    public void renderFX(TileEntity te, double x, double y, double z, ItemStack stack) {}

    public void renderFX(World world, int x, int y, int z, Block block, int meta) {}

    public <T extends IMessage> void sendPacketToClient(T object) {
        Primal.network.sendToAll(object);
    }

    public <T extends IMessage> void sendPacketToClientInDimension(World world, T object) {
        Primal.network.sendToDimension(object, world.provider.dimensionId);
    }

    public <T extends IMessage> void playPacketToClientNear(T object, World world, int x, int y, int z) {
        Primal.network
            .sendToAllAround(object, new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 64.0D));
    }

    public <T extends IMessage> void sendPacketToServer(T object) {};

    public void openLargeVesselGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        openPrimalGui(largeVesselGui, player, worldIn, x, y, z);
    }

    public void openCrucibleGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        openPrimalGui(crucibleGui, player, worldIn, x, y, z);
    }

    public void openGeneratorGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        openPrimalGui(generatorGui, player, worldIn, x, y, z);
    }

    public void openAnvilWorkGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        openPrimalGui(anvilWorkGui, player, worldIn, x, y, z);
    }

    public void openAnvilPlanGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        openPrimalGui(anvilPlanGui, player, worldIn, x, y, z);
    }

    public void openPrimalGui(ContainerPrimal container, EntityPlayer player, World worldIn, int x, int y, int z) {
        player.openGui(Primal.instance, container.getGuiId(), worldIn, x, y, z);
    }
}
