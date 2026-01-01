package net.pufferlab.primal;

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
import net.pufferlab.primal.client.renderer.*;
import net.pufferlab.primal.events.PacketFireStarter;
import net.pufferlab.primal.events.PacketSpeedUpdate;
import net.pufferlab.primal.events.PacketSwingArm;
import net.pufferlab.primal.inventory.container.ContainerKnapping;
import net.pufferlab.primal.inventory.gui.GuiCrucible;
import net.pufferlab.primal.inventory.gui.GuiKnapping;
import net.pufferlab.primal.inventory.gui.GuiLargeVessel;
import net.pufferlab.primal.recipes.KnappingType;
import net.pufferlab.primal.tileentities.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;

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
    int crucibleRenderID;
    int forgeRenderID;
    int castRenderID;
    int quernRenderID;

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

        register(TileEntityPitKiln.class, new TileEntityPitKilnRenderer());
        register(TileEntityChoppingLog.class, new TileEntityChoppingLogRenderer());
        register(TileEntityCampfire.class, new TileEntityCampfireRenderer());
        register(TileEntityBarrel.class, new TileEntityBarrelRenderer());
        register(TileEntityTanning.class, new TileEntityTanningRenderer());
        register(TileEntityOven.class, new TileEntityOvenRenderer());
        register(TileEntityCast.class, new TileEntityMoldRenderer());
        register(TileEntityCrucible.class, new TileEntityCrucibleRenderer());
        register(TileEntityQuern.class, new TileEntityQuernRenderer());

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
    }

    @Override
    public void setupPackets() {
        registerPacket(PacketSwingArm.class, Side.CLIENT);
        registerPacket(PacketFireStarter.class, Side.CLIENT);
        registerPacket(PacketSpeedUpdate.class, Side.CLIENT);
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
        if (ID == crucibleContainerID) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TileEntityCrucible tef) {
                return new GuiCrucible(player.inventory, tef);
            }
        }
        return null;
    }

    @Override
    public <T extends IMessage> void sendPacketToServer(T object) {
        Primal.network.sendToServer(object);
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
}
