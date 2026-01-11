package net.pufferlab.primal;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.pufferlab.primal.client.audio.SoundQuernGrinding;
import net.pufferlab.primal.client.particle.EntityGrindingFX;
import net.pufferlab.primal.client.renderer.blocks.*;
import net.pufferlab.primal.client.renderer.items.*;
import net.pufferlab.primal.client.renderer.tileentities.*;
import net.pufferlab.primal.inventory.container.ContainerKnapping;
import net.pufferlab.primal.inventory.gui.GuiCrucible;
import net.pufferlab.primal.inventory.gui.GuiGenerator;
import net.pufferlab.primal.inventory.gui.GuiKnapping;
import net.pufferlab.primal.inventory.gui.GuiLargeVessel;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.tileentities.*;
import net.pufferlab.primal.utils.TemperatureUtils;

import org.apache.commons.io.FileUtils;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class ClientProxy extends CommonProxy {

    private int pitKilnRenderID;
    private int logPileRenderID;
    private int charcoalPileRenderID;
    private int ashPileRenderID;
    private int campfireRenderID;
    private int largeVesselRenderID;
    private int barrelRenderID;
    private int faucetRenderID;
    private int groundcoverRenderID;
    private int tanningRenderID;
    private int ovenRenderID;
    private int chimneyRenderID;
    private int crucibleRenderID;
    private int forgeRenderID;
    private int castRenderID;
    private int quernRenderID;
    private int axleRenderID;
    private int generatorRenderID;

    @Override
    public void setupRenders() {
        pitKilnRenderID = getNextId();
        logPileRenderID = getNextId();
        charcoalPileRenderID = getNextId();
        ashPileRenderID = getNextId();
        campfireRenderID = getNextId();
        largeVesselRenderID = getNextId();
        barrelRenderID = getNextId();
        faucetRenderID = getNextId();
        groundcoverRenderID = getNextId();
        tanningRenderID = getNextId();
        ovenRenderID = getNextId();
        chimneyRenderID = getNextId();
        crucibleRenderID = getNextId();
        forgeRenderID = getNextId();
        castRenderID = getNextId();
        quernRenderID = getNextId();
        axleRenderID = getNextId();
        generatorRenderID = getNextId();

        register(new BlockPitKilnRenderer());
        register(new BlockLogPileRenderer());
        register(new BlockCharcoalPileRenderer());
        register(new BlockAshPileRenderer());
        register(new BlockCampfireRenderer());
        register(new BlockLargeVesselRenderer());
        register(new BlockBarrelRenderer());
        register(new BlockFaucetRenderer());
        register(new BlockGroundcoverRenderer());
        register(new BlockTanningRenderer());
        register(new BlockOvenRenderer());
        register(new BlockChimneyRenderer());
        register(new BlockCrucibleRenderer());
        register(new BlockForgeRenderer());
        register(new BlockCastRenderer());
        register(new BlockQuernRenderer());
        register(new BlockAxleRenderer());
        register(new BlockGeneratorRenderer());

        register(TileEntityPitKiln.class, new TileEntityPitKilnRenderer());
        register(TileEntityChoppingLog.class, new TileEntityChoppingLogRenderer());
        register(TileEntityCampfire.class, new TileEntityCampfireRenderer());
        register(TileEntityBarrel.class, new TileEntityBarrelRenderer());
        register(TileEntityTanning.class, new TileEntityTanningRenderer());
        register(TileEntityOven.class, new TileEntityOvenRenderer());
        register(TileEntityCast.class, new TileEntityMoldRenderer());
        register(TileEntityCrucible.class, new TileEntityCrucibleRenderer());
        register(TileEntityQuern.class, new TileEntityQuernRenderer());
        register(TileEntityAxle.class, new TileEntityAxleRenderer());
        register(TileEntityGenerator.class, new TileEntityGeneratorRenderer());
        register(TileEntityWaterwheel.class, new TileEntityWaterwheelRenderer());
        register(TileEntityWindmill.class, new TileEntityWindmillRenderer());

        register(Registry.wood, new ItemWoodRenderer());
        register(Registry.clay, new ItemClayRenderer());
        register(Registry.barrel, new ItemBarrelRenderer());
        register(Registry.faucet, new ItemFaucetRenderer());
        register(Registry.large_vessel, new ItemLargeVesselRenderer());
        register(Registry.oven, new ItemOvenRenderer());
        register(Registry.crucible, new ItemCrucibleRenderer());
        register(Registry.forge, new ItemForgeRenderer());
        register(Registry.handstone, new ItemHandstoneRenderer());
        register(Registry.quern, new ItemQuernRenderer());
        register(Registry.bucket, new ItemBucketRenderer());
        register(Registry.ceramic_bucket, new ItemBucketCeramicRenderer());
        register(Registry.axle, new ItemAxleRenderer());
        register(Registry.generator, new ItemGeneratorRenderer());
        register(Registry.waterwheel, new ItemWaterwheelRenderer());
        register(Registry.windmill, new ItemWindmillRenderer());
        register(Registry.ingot, new ItemHeatableRenderer());

        for (Item item : TemperatureUtils.getHeatableItems()) {
            register(
                item,
                new ItemHeatableRenderer(
                    TemperatureUtils.getHeatableMask(item),
                    TemperatureUtils.getHeatableMeta(item)));
        }
    }

    public int getNextId() {
        return RenderingRegistry.getNextAvailableRenderId();
    }

    public <T extends ISimpleBlockRenderingHandler> void register(T object) {
        RenderingRegistry.registerBlockHandler(object);
    }

    public <T extends TileEntitySpecialRenderer> void register(Class<? extends TileEntity> cl, T object) {
        ClientRegistry.bindTileEntitySpecialRenderer(cl, object);
    }

    public <T extends IItemRenderer> void register(Item item, T object) {
        MinecraftForgeClient.registerItemRenderer(item, object);
    }

    public <T extends IItemRenderer> void register(Block block, T object) {
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(block), object);
    }

    @Override
    public void setupResources() {
        try {
            File rpDir = new File(Minecraft.getMinecraft().mcDataDir, "resourcepacks");

            if (!rpDir.exists()) rpDir.mkdirs();

            File out = new File(rpDir, "Primal-Modern-Resources.zip");

            if (out.exists()) return;

            URL url = new URL(
                "https://github.com/PufferTeam-ModArchive/Primal/raw/refs/heads/main/builtin/Primal-Modern-Resources.zip");
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            try (InputStream in = connection.getInputStream()) {
                FileUtils.copyInputStreamToFile(in, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        KnappingType knappingType = KnappingType.getHandler(ID);
        if (knappingType != null) {
            return new GuiKnapping(new ContainerKnapping(knappingType, player.inventory));
        }
        TileEntity te = world.getTileEntity(x, y, z);
        if (ID == largeVesselContainerID && te instanceof TileEntityLargeVessel tef) {
            return new GuiLargeVessel(player.inventory, tef);
        }
        if (ID == crucibleContainerID && te instanceof TileEntityCrucible tef) {
            return new GuiCrucible(player.inventory, tef);
        }
        if (ID == generatorGuiID && te instanceof TileEntityGenerator tef) {
            return new GuiGenerator(tef);
        }
        return null;
    }

    @Override
    public <T extends IMessage> void sendPacketToServer(T object) {
        Primal.network.sendToServer(object);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft().theWorld;
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public void playClientSound(TileEntity te) {
        if (te instanceof TileEntityQuern tef) {
            Minecraft.getMinecraft()
                .getSoundHandler()
                .playSound(new SoundQuernGrinding(tef));
        }
    }

    @Override
    public void renderFX(TileEntity te, double x, double y, double z, ItemStack stack) {
        if (te instanceof TileEntityQuern) {
            if (stack != null) {
                Item item = stack.getItem();
                int meta = stack.getItemDamage();
                EntityGrindingFX fx = new EntityGrindingFX(
                    te.getWorldObj(),
                    te.xCoord + x,
                    te.yCoord + y,
                    te.zCoord + z,
                    item,
                    meta);
                Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            }
        }
    }

    @Override
    public void openGeneratorGui(EntityPlayer player, World worldIn, int x, int y, int z) {
        player.openGui(Primal.instance, generatorGuiID, worldIn, x, y, z);
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

    @Override
    public int getCrucibleRenderID() {
        return crucibleRenderID;
    }

    @Override
    public int getForgeRenderID() {
        return forgeRenderID;
    }

    @Override
    public int getCastRenderID() {
        return castRenderID;
    }

    @Override
    public int getQuernRenderID() {
        return quernRenderID;
    }

    @Override
    public int getAxleRenderID() {
        return axleRenderID;
    }

    @Override
    public int getGeneratorRenderID() {
        return generatorRenderID;
    }
}
