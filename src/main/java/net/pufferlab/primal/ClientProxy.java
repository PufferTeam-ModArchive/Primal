package net.pufferlab.primal;

import java.io.File;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.pufferlab.primal.blocks.IPrimalBlock;
import net.pufferlab.primal.client.audio.SoundQuernGrinding;
import net.pufferlab.primal.client.gui.*;
import net.pufferlab.primal.client.particle.EntityGrindingFX;
import net.pufferlab.primal.client.renderer.RenderAccessory;
import net.pufferlab.primal.client.renderer.blocks.*;
import net.pufferlab.primal.client.renderer.entities.*;
import net.pufferlab.primal.client.renderer.items.*;
import net.pufferlab.primal.client.renderer.tileentities.*;
import net.pufferlab.primal.inventory.*;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.tileentities.*;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.IOUtils;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class ClientProxy extends CommonProxy {

    @Override
    public void setupRenders() {
        slabRenderer = new BlockSlabRenderer();
        slabVerticalRenderer = new BlockSlabVerticalRenderer();
        stairsRenderer = new BlockStairsRenderer();
        wallRenderer = new BlockWallRenderer();
        ropeLadderRenderer = new BlockRopeLadderRenderer();
        grassRenderer = new BlockGrassRenderer();
        pathRenderer = new BlockPathRenderer();
        oreRenderer = new BlockOreRenderer();
        pitKilnRenderer = new BlockPitKilnRenderer();
        logPileRenderer = new BlockLogPileRenderer();
        charcoalPileRenderer = new BlockCharcoalPileRenderer();
        ashPileRenderer = new BlockAshPileRenderer();
        campfireRenderer = new BlockCampfireRenderer();
        largeVesselRenderer = new BlockLargeVesselRenderer();
        barrelRenderer = new BlockBarrelRenderer();
        faucetRenderer = new BlockFaucetRenderer();
        groundcoverRenderer = new BlockGroundcoverRenderer();
        tanningRenderer = new BlockTanningRenderer();
        ovenRenderer = new BlockOvenRenderer();
        chimneyRenderer = new BlockChimneyRenderer();
        crucibleRenderer = new BlockCrucibleRenderer();
        forgeRenderer = new BlockForgeRenderer();
        castRenderer = new BlockCastRenderer();
        quernRenderer = new BlockQuernRenderer();
        axleRenderer = new BlockAxleRenderer();
        generatorRenderer = new BlockGeneratorRenderer();
        anvilRenderer = new BlockAnvilRenderer();
        bloomeryRenderer = new BlockBloomeryRenderer();
        cropsRenderer = new BlockCropsRenderer();

        register(slabRenderer);
        register(slabVerticalRenderer);
        register(stairsRenderer);
        register(wallRenderer);
        register(ropeLadderRenderer);
        register(grassRenderer);
        register(pathRenderer);
        register(oreRenderer);
        register(pitKilnRenderer);
        register(logPileRenderer);
        register(charcoalPileRenderer);
        register(ashPileRenderer);
        register(campfireRenderer);
        register(largeVesselRenderer);
        register(barrelRenderer);
        register(faucetRenderer);
        register(groundcoverRenderer);
        register(tanningRenderer);
        register(ovenRenderer);
        register(chimneyRenderer);
        register(crucibleRenderer);
        register(forgeRenderer);
        register(castRenderer);
        register(quernRenderer);
        register(axleRenderer);
        register(generatorRenderer);
        register(anvilRenderer);
        register(bloomeryRenderer);
        register(cropsRenderer);

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
        register(Registry.bloomery, new ItemBloomeryRenderer());

        if (Config.metalHeatRendering.getBoolean()) {
            for (Item item : HeatUtils.getBuiltinHeatableItems()) {
                register(item, new ItemHeatableRenderer());
            }
            if (Config.modMetalHeatRendering.getBoolean()) {
                for (Item item : HeatUtils.getHeatableItems()) {
                    register(
                        item,
                        new ItemHeatableRenderer(HeatUtils.getHeatableMask(item), HeatUtils.getHeatableMeta(item)));
                }
            }
        }

        register(Registry.straw_hat, new ItemStrawHatRenderer());
        register(Registry.straw_shirt, new ItemStrawShirtRenderer());
        register(Registry.straw_coat, new ItemStrawCoatRenderer());
        register(Registry.straw_sandals, new ItemStrawSandalsRenderer());
    }

    public int getNextId() {
        return RenderingRegistry.getNextAvailableRenderId();
    }

    public <T extends BlockPrimalRenderer> void register(T object) {
        object.setRenderId(getNextId());
        RenderingRegistry.registerBlockHandler(object);
    }

    public <T extends TileEntitySpecialRenderer> void register(Class<? extends TileEntity> cl, T object) {
        ClientRegistry.bindTileEntitySpecialRenderer(cl, object);
    }

    public <T extends IItemRenderer> void register(Item item, T object) {
        MinecraftForgeClient.registerItemRenderer(item, object);
    }

    public <T extends IAccessoryRenderer> void register(Item item, T object) {
        RenderAccessory.registerRenderer(item, object);
    }

    public <T extends IItemRenderer> void register(Block block, T object) {
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(block), object);
    }

    @Override
    public void setupResources() {
        try {
            File out = IOUtils.createResourceFile(Primal.textureFile, "zip");
            File outTemp = IOUtils.createTempFile();
            File infoFile = IOUtils.createResourceFile(Primal.textureFile, "txt");
            File infoTemp = IOUtils.createTempFile();
            IOUtils.downloadFile(Primal.downloadPath + Primal.textureFile, "txt", infoTemp);
            String newHash = IOUtils.readFile(infoTemp);
            String oldHash = IOUtils.readFile(infoFile);
            if (newHash == null || newHash.equals(oldHash)) {
                return;
            }

            IOUtils.copyFile(infoTemp, infoFile);
            try {
                IOUtils.downloadFile(Primal.downloadPath + Primal.textureFile, "zip", outTemp);
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
            return new GuiKnapping(knappingType, player.inventory);
        }
        TileEntity te = world.getTileEntity(x, y, z);
        ContainerPrimal gui = getGui(ID);
        if (gui instanceof ContainerLargeVessel && te instanceof TileEntityLargeVessel tef) {
            return new GuiLargeVessel(player.inventory, tef);
        }
        if (gui instanceof ContainerCrucible && te instanceof TileEntityCrucible tef) {
            return new GuiCrucible(player.inventory, tef);
        }
        if (gui instanceof ContainerGenerator && te instanceof TileEntityGenerator tef) {
            return new GuiGenerator(tef);
        }
        if (gui instanceof ContainerAnvilWork && te instanceof TileEntityAnvil tef) {
            return new GuiAnvilWork(tef);
        }
        if (gui instanceof ContainerAnvilPlan && te instanceof TileEntityAnvil tef) {
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
    public int getClientRenderDistance() {
        return Minecraft.getMinecraft().gameSettings.renderDistanceChunks;
    }

    @Override
    public int getClientMaxRenderDistanceSquared() {
        return Utils.pow(getClientRenderDistance() * 16);
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
    public void renderFX(EntityFX entityFX) {
        Minecraft.getMinecraft().effectRenderer.addEffect(entityFX);
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
                renderFX(fx);
            }
        }
    }

    @Override
    public void renderFX(World world, int x, int y, int z, Block block, int meta) {
        byte b0 = 4;
        if (block instanceof IPrimalBlock block2) {
            b0 = block2.getBlockParticleAmount();
        }

        if (!block.isAir(world, x, y, z)) {
            for (int i1 = 0; i1 < b0; ++i1) {
                for (int j1 = 0; j1 < b0; ++j1) {
                    for (int k1 = 0; k1 < b0; ++k1) {
                        double d0 = (double) x + ((double) i1 + 0.5D) / (double) b0;
                        double d1 = (double) y + ((double) j1 + 0.5D) / (double) b0;
                        double d2 = (double) z + ((double) k1 + 0.5D) / (double) b0;
                        renderFX(
                            new EntityDiggingFX(
                                world,
                                d0,
                                d1,
                                d2,
                                d0 - (double) x - 0.5D,
                                d1 - (double) y - 0.5D,
                                d2 - (double) z - 0.5D,
                                block,
                                meta).applyColourMultiplier(x, y, z));
                    }
                }
            }
        }
    }
}
