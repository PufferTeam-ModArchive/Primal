package net.pufferlab.primal;

import java.io.File;
import java.io.IOException;

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
import net.pufferlab.primal.client.gui.*;
import net.pufferlab.primal.client.particle.EntityGrindingFX;
import net.pufferlab.primal.client.renderer.blocks.*;
import net.pufferlab.primal.client.renderer.items.*;
import net.pufferlab.primal.client.renderer.tileentities.*;
import net.pufferlab.primal.inventory.ContainerKnapping;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.tileentities.*;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.IOUtils;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class ClientProxy extends CommonProxy {

    private int slabRenderID;
    private int verticalSlabRenderID;
    private int stairsRenderID;
    private int grassRenderID;
    private int pathRenderID;
    private int oreRenderID;
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
    private int anvilRenderID;

    @Override
    public void setupRenders() {
        slabRenderID = getNextId();
        verticalSlabRenderID = getNextId();
        stairsRenderID = getNextId();
        grassRenderID = getNextId();
        pathRenderID = getNextId();
        oreRenderID = getNextId();
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
        anvilRenderID = getNextId();

        register(new BlockSlabRenderer());
        register(new BlockSlabVerticalRenderer());
        register(new BlockStairsRenderer());
        register(new BlockGrassRenderer());
        register(new BlockPathRenderer());
        register(new BlockOreRenderer());
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
        register(new BlockAnvilRenderer());

        register(TileEntityPitKiln.class, new TileEntityPitKilnRenderer());
        register(TileEntityChoppingLog.class, new TileEntityChoppingLogRenderer());
        register(TileEntityCampfire.class, new TileEntityCampfireRenderer());
        register(TileEntityBarrel.class, new TileEntityBarrelRenderer());
        register(TileEntityTanning.class, new TileEntityTanningRenderer());
        register(TileEntityOven.class, new TileEntityOvenRenderer());
        register(TileEntityCast.class, new TileEntityCastRenderer());
        register(TileEntityCrucible.class, new TileEntityCrucibleRenderer());
        register(TileEntityQuern.class, new TileEntityQuernRenderer());
        register(TileEntityAxle.class, new TileEntityAxleRenderer());
        register(TileEntityGenerator.class, new TileEntityGeneratorRenderer());
        register(TileEntityWaterwheel.class, new TileEntityWaterwheelRenderer());
        register(TileEntityWindmill.class, new TileEntityWindmillRenderer());
        register(TileEntityAnvil.class, new TileEntityAnvilRenderer());

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
        register(Registry.ceramic_bucket_modded, new ItemBucketRenderer());
        register(Registry.axle, new ItemAxleRenderer());
        register(Registry.generator, new ItemGeneratorRenderer());
        register(Registry.waterwheel, new ItemWaterwheelRenderer());
        register(Registry.windmill, new ItemWindmillRenderer());
        register(Registry.anvil, new ItemAnvilRenderer());

        if (Config.metalHeatRendering.getBoolean()) {
            register(Registry.ingot, new ItemHeatableRenderer());
            register(Registry.nugget, new ItemHeatableRenderer());
            register(Registry.axe_head, new ItemHeatableRenderer());
            register(Registry.pickaxe_head, new ItemHeatableRenderer());
            register(Registry.shovel_head, new ItemHeatableRenderer());
            register(Registry.sword_blade, new ItemHeatableRenderer());
            register(Registry.hoe_head, new ItemHeatableRenderer());

            if (Config.modMetalHeatRendering.getBoolean()) {
                for (Item item : HeatUtils.getHeatableItems()) {
                    register(
                        item,
                        new ItemHeatableRenderer(HeatUtils.getHeatableMask(item), HeatUtils.getHeatableMeta(item)));
                }
            }
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

            File out = new File(rpDir, Primal.textureFile + ".zip");
            File outTemp = File.createTempFile("texture", ".tmp");
            File infoFile = new File(rpDir, Primal.textureFile + ".txt");
            File infoTemp = File.createTempFile("download", ".tmp");
            IOUtils.downloadFile(Primal.downloadPath + Primal.textureFile + ".txt", infoTemp);
            String newHash = IOUtils.readFile(infoTemp);
            String oldHash = IOUtils.readFile(infoFile);
            if (newHash == null || newHash.equals(oldHash)) {
                return;
            }

            IOUtils.copyFile(infoTemp, infoFile);
            try {
                IOUtils.downloadFile(Primal.downloadPath + Primal.textureFile + ".zip", outTemp);
            } catch (IOException e) {
                return;
            }
            IOUtils.copyFile(outTemp, out);
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
        if (ID == largeVesselGuiID && te instanceof TileEntityLargeVessel tef) {
            return new GuiLargeVessel(player.inventory, tef);
        }
        if (ID == crucibleGuiID && te instanceof TileEntityCrucible tef) {
            return new GuiCrucible(player.inventory, tef);
        }
        if (ID == generatorGuiID && te instanceof TileEntityGenerator tef) {
            return new GuiGenerator(tef);
        }
        if (ID == anvilWorkGuiID && te instanceof TileEntityAnvil tef) {
            return new GuiAnvilWork(tef);
        }
        if (ID == anvilPlanGuiID && te instanceof TileEntityAnvil tef) {
            return new GuiAnvilPlan(player.inventory, tef);
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
    public int getSlabRenderID() {
        return slabRenderID;
    }

    @Override
    public int getVerticalSlabRenderID() {
        return verticalSlabRenderID;
    }

    @Override
    public int getStairsRenderID() {
        return stairsRenderID;
    }

    @Override
    public int getGrassRenderID() {
        return grassRenderID;
    }

    @Override
    public int getPathRenderID() {
        return pathRenderID;
    }

    @Override
    public int getOreRenderID() {
        return oreRenderID;
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

    @Override
    public int getAnvilRenderID() {
        return anvilRenderID;
    }
}
