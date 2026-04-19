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
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class CommonProxy implements IGuiHandler {

    public MinecraftServer server;
    public final NetworkPacket packet = new NetworkPacket();

    protected BlockSlabRenderer slabRenderer;
    protected BlockSlabVerticalRenderer slabVerticalRenderer;
    protected BlockStairsRenderer stairsRenderer;
    protected BlockWallRenderer wallRenderer;
    protected BlockPressurePlateRenderer pressurePlateRenderer;
    protected BlockButtonRenderer buttonRenderer;
    protected BlockRopeLadderRenderer ropeLadderRenderer;
    protected BlockGrassRenderer grassRenderer;
    protected BlockPathRenderer pathRenderer;
    protected BlockOreRenderer oreRenderer;
    protected BlockPitKilnRenderer pitKilnRenderer;
    protected BlockLogPileRenderer logPileRenderer;
    protected BlockCharcoalPileRenderer charcoalPileRenderer;
    protected BlockAshPileRenderer ashPileRenderer;
    protected BlockCampfireRenderer campfireRenderer;
    protected BlockLargeVesselRenderer largeVesselRenderer;
    protected BlockBarrelRenderer barrelRenderer;
    protected BlockFaucetRenderer faucetRenderer;
    protected BlockGroundcoverRenderer groundcoverRenderer;
    protected BlockTanningRenderer tanningRenderer;
    protected BlockOvenRenderer ovenRenderer;
    protected BlockChimneyRenderer chimneyRenderer;
    protected BlockCrucibleRenderer crucibleRenderer;
    protected BlockForgeRenderer forgeRenderer;
    protected BlockCastRenderer castRenderer;
    protected BlockQuernRenderer quernRenderer;
    protected BlockAxleRenderer axleRenderer;
    protected BlockGeneratorRenderer generatorRenderer;
    protected BlockAnvilRenderer anvilRenderer;
    protected BlockBloomeryRenderer bloomeryRenderer;
    protected BlockCropsRenderer cropsRenderer;

    public ContainerLargeVessel largeVesselGui;
    public ContainerCrucible crucibleGui;
    public ContainerGenerator generatorGui;
    public ContainerAnvilWork anvilWorkGui;
    public ContainerAnvilPlan anvilPlanGui;

    private int nextGuiID;

    public void preInit(FMLPreInitializationEvent event) {}

    private final TIntObjectMap<ContainerPrimal> guiMap = new TIntObjectHashMap<>();
    private final TObjectIntMap<Class<? extends ContainerPrimal>> guiIdMap = new TObjectIntHashMap<>();

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
        int id = getNextGuiID();
        guiIdMap.put(object.getClass(), id);
        guiMap.put(id, object);
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

    public int getGuiId(ContainerPrimal container) {
        return guiIdMap.get(container.getClass());
    }

    public int getRenderId(BlockPrimalRenderer container) {
        return 0;
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

    public void openKnappingGui(KnappingType type, EntityPlayer player, World worldIn, int x, int y, int z) {
        int containerId = KnappingType.getHandler(type);
        player.openGui(Primal.instance, containerId, worldIn, x, y, z);
    }

    public void openPrimalGui(ContainerPrimal container, EntityPlayer player, World worldIn, int x, int y, int z) {
        player.openGui(Primal.instance, getGuiId(container), worldIn, x, y, z);
    }

    public BlockSlabRenderer getSlabRenderer() {
        return slabRenderer;
    }

    public BlockSlabVerticalRenderer getSlabVerticalRenderer() {
        return slabVerticalRenderer;
    }

    public BlockStairsRenderer getStairsRenderer() {
        return stairsRenderer;
    }

    public BlockWallRenderer getWallRenderer() {
        return wallRenderer;
    }

    public BlockPressurePlateRenderer getPressurePlateRenderer() {
        return pressurePlateRenderer;
    }

    public BlockButtonRenderer getButtonRenderer() {
        return buttonRenderer;
    }

    public BlockRopeLadderRenderer getRopeLadderRenderer() {
        return ropeLadderRenderer;
    }

    public BlockGrassRenderer getGrassRenderer() {
        return grassRenderer;
    }

    public BlockPathRenderer getPathRenderer() {
        return pathRenderer;
    }

    public BlockOreRenderer getOreRenderer() {
        return oreRenderer;
    }

    public BlockPitKilnRenderer getPitKilnRenderer() {
        return pitKilnRenderer;
    }

    public BlockLogPileRenderer getLogPileRenderer() {
        return logPileRenderer;
    }

    public BlockCharcoalPileRenderer getCharcoalPileRenderer() {
        return charcoalPileRenderer;
    }

    public BlockAshPileRenderer getAshPileRenderer() {
        return ashPileRenderer;
    }

    public BlockCampfireRenderer getCampfireRenderer() {
        return campfireRenderer;
    }

    public BlockLargeVesselRenderer getLargeVesselRenderer() {
        return largeVesselRenderer;
    }

    public BlockBarrelRenderer getBarrelRenderer() {
        return barrelRenderer;
    }

    public BlockFaucetRenderer getFaucetRenderer() {
        return faucetRenderer;
    }

    public BlockGroundcoverRenderer getGroundcoverRenderer() {
        return groundcoverRenderer;
    }

    public BlockTanningRenderer getTanningRenderer() {
        return tanningRenderer;
    }

    public BlockOvenRenderer getOvenRenderer() {
        return ovenRenderer;
    }

    public BlockChimneyRenderer getChimneyRenderer() {
        return chimneyRenderer;
    }

    public BlockCrucibleRenderer getCrucibleRenderer() {
        return crucibleRenderer;
    }

    public BlockForgeRenderer getForgeRenderer() {
        return forgeRenderer;
    }

    public BlockCastRenderer getCastRenderer() {
        return castRenderer;
    }

    public BlockQuernRenderer getQuernRenderer() {
        return quernRenderer;
    }

    public BlockAxleRenderer getAxleRenderer() {
        return axleRenderer;
    }

    public BlockGeneratorRenderer getGeneratorRenderer() {
        return generatorRenderer;
    }

    public BlockAnvilRenderer getAnvilRenderer() {
        return anvilRenderer;
    }

    public BlockBloomeryRenderer getBloomeryRenderer() {
        return bloomeryRenderer;
    }

    public BlockCropsRenderer getCropsRenderer() {
        return cropsRenderer;
    }
}
