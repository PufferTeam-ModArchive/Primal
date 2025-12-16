package net.pufferlab.primal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.*;
import net.pufferlab.primal.blocks.*;
import net.pufferlab.primal.compat.minetweaker.PrimalTweaker;
import net.pufferlab.primal.compat.nei.NEIRegistry;
import net.pufferlab.primal.events.*;
import net.pufferlab.primal.items.*;
import net.pufferlab.primal.tileentities.*;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import minetweaker.MineTweakerAPI;

public class Registry {

    public static final CreativeTabs creativeTab = new CreativeTabs(Primal.MODID + "CreativeTab") {

        @Override
        public Item getTabIconItem() {
            return Registry.firestarter;
        }

        @Override
        public String getTranslatedTabLabel() {
            return Primal.MODNAME;
        }
    };

    public static Block ground_rock;
    public static Block pit_kiln;
    public static Block thatch;
    public static Block thatch_roof;
    public static Block log_pile;
    public static Block charcoal_pile;
    public static Block ash_pile;
    public static Block tanning;
    public static Block chopping_log;
    public static Block campfire;
    public static Block large_vessel;
    public static Block barrel;
    public static Block faucet;
    public static Item icons;
    public static Item straw;
    public static Item hide;
    public static Item rock;
    public static Item flint;
    public static Item wood;
    public static Item powder;
    public static Item clay;
    public static Item ceramic_bucket;
    public static Item flint_axe;
    public static Item flint_pickaxe;
    public static Item flint_shovel;
    public static Item flint_knife;
    public static Item firestarter;
    public static Item bucket;
    public static Item.ToolMaterial toolFlint;

    public void setup() {
        toolFlint = EnumHelper.addToolMaterial("flint", 0, 100, 2.0F, 0.0F, 15);

        ground_rock = new BlockGroundcover(Material.rock, Constants.rockTypes, "ground_rock")
            .setTextureOverride(Constants.rockTextures);
        pit_kiln = new BlockPitKiln();
        log_pile = new BlockLogPile();
        charcoal_pile = new BlockCharcoalPile();
        ash_pile = new BlockAshPile();
        tanning = new BlockTanning();
        chopping_log = new BlockChoppingLog();
        campfire = new BlockCampfire();
        large_vessel = new BlockLargeVessel();
        barrel = new BlockBarrel();
        faucet = new BlockFaucet();

        thatch = new BlockThatch();
        thatch_roof = new BlockThatchRoof();

        icons = new ItemMeta(Constants.icons, "icon").setHiddenAll();
        straw = new ItemMeta(Constants.strawItems, "straw");
        hide = new ItemMeta(Constants.hideItems, "hide");
        wood = new ItemMeta(Constants.woodItems, "wood");
        flint = new ItemMeta(Constants.flintItems, "flint");
        rock = new ItemMeta(Constants.rockTypes, "rock");
        powder = new ItemMeta(Constants.powderItems, "powder");
        clay = new ItemMeta(Constants.clayItems, "clay");
        ((BlockGroundcover) ground_rock).setItem(rock);

        bucket = new ItemBucketMeta("bucket");
        ceramic_bucket = new ItemBucketCeramic("ceramic_bucket");

        flint_axe = new ItemAxePrimitive(toolFlint, "flint_axe");
        flint_pickaxe = new ItemPickaxePrimitive(toolFlint, "flint_pickaxe");
        flint_shovel = new ItemShovelPrimitive(toolFlint, "flint_shovel");
        flint_knife = new ItemKnifePrimitive(toolFlint, "flint_knife");

        firestarter = new ItemFireStarter();

        register(thatch, "thatch");
        register(thatch_roof, "thatch_roof");
        register(ground_rock, "ground_rock");
        register(pit_kiln, "pit_kiln");
        register(log_pile, "log_pile");
        register(charcoal_pile, "charcoal_pile");
        register(ash_pile, "ash_pile");
        register(chopping_log, "chopping_log");
        register(campfire, "campfire");
        register(large_vessel, "large_vessel");
        register(barrel, "barrel");
        register(faucet, "faucet");
        register(tanning, "tanning_frame");

        register(icons, "icon");
        register(straw, "straw");
        register(hide, "hide");
        register(wood, "wood");
        register(powder, "powder");
        register(rock, "rock");
        register(flint, "flint");
        register(clay, "clay");
        register(flint_axe, "flint_axe");
        register(flint_pickaxe, "flint_pickaxe");
        register(flint_shovel, "flint_shovel");
        register(flint_knife, "flint_knife");
        register(firestarter, "firestarter");
        register(bucket, "bucket");
        register(ceramic_bucket, "ceramic_bucket");
    }

    public void setupTiles() {
        register(TileEntityPitKiln.class, "pit_kiln");
        register(TileEntityLogPile.class, "log_pile");
        register(TileEntityCharcoalPile.class, "charcoal_pile");
        register(TileEntityAshPile.class, "ash_pile");
        register(TileEntityChoppingLog.class, "chopping_log");
        register(TileEntityCampfire.class, "campfire");
        register(TileEntityLargeVessel.class, "large_vessel");
        register(TileEntityBarrel.class, "barrel");
        register(TileEntityFaucet.class, "faucet");
        register(TileEntityTanning.class, "tanning_frame");
    }

    public static final Block[] fluidsBlocks = new Block[Constants.fluids.length];
    public static final Fluid[] fluidsObjects = new Fluid[Constants.fluids.length];

    public void setupFluids() {
        fluidsBlocks[0] = Blocks.air;
        fluidsBlocks[1] = Blocks.flowing_water;
        fluidsBlocks[2] = Blocks.flowing_lava;
        fluidsObjects[1] = FluidRegistry.WATER;
        fluidsObjects[2] = FluidRegistry.LAVA;
        for (int i = 0; i < Constants.fluids.length; i++) {
            String name = Constants.fluids[i];
            if (!Utils.containsExactMatch(Constants.vanillaFluids, name)) {
                Fluid fluid = new FluidPrimal(name).setDensity(1000)
                    .setViscosity(1000);
                register(fluid);
                Block block = new BlockFluidPrimal(fluid, Constants.fluidsMaterial[i], name);
                fluid.setBlock(block);
                register(block, name);
                register(fluid, name);
                fluidsObjects[i] = fluid;
                fluidsBlocks[i] = block;
            }
        }
    }

    public void setupEvents() {
        registerEvent(new PitKilnHandler());
        registerEvent(new KnappingHandler());
        registerEvent(new BucketHandler());
        registerEvent(new ToolHandler());
        registerEvent(new LogPileHandler());
        registerEvent(new CharcoalPileHandler());
        registerEvent(new AshPileHandler());
        registerEvent(new CampfireHandler());
        registerEvent(new LargeVesselHandler());
        registerEvent(new BarrelHandler());
        registerEvent(new GroundcoverRockHandler());
        registerEvent(new MobDropHandler());
    }

    public void setupPackets() {
        Primal.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Primal.MODID);
        registerPacket(PacketSwingArm.class, Side.CLIENT);
        registerPacket(PacketKnappingClick.class, Side.SERVER);
        registerPacket(PacketFireStarter.class, Side.CLIENT);
        registerPacket(PacketPitKilnPlace.class, Side.SERVER);
    }

    public void setupNEI() {
        if (Primal.NEILoaded) {
            new NEIRegistry().loadConfig();
        }
    }

    public void setupMT() {
        if (Primal.MTLoaded) {
            MineTweakerAPI.registerClass(PrimalTweaker.class);
        }
    }

    public void register(Item item, String name) {
        GameRegistry.registerItem(item.setCreativeTab(Registry.creativeTab), name);
    }

    public void register(Block block, String name) {
        if (block instanceof BlockLogPile || block instanceof BlockCharcoalPile
            || block instanceof BlockAshPile
            || block instanceof BlockGroundcover) {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), null, name);
        } else
            if (block instanceof BlockLargeVessel || block instanceof BlockBarrel || block instanceof BlockCampfire) {
                GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), ItemBlockPrimal.class, name);
            } else if (block instanceof BlockFluidPrimal) {
                GameRegistry.registerBlock(block.setCreativeTab(null), name);
            } else if (block instanceof BlockMeta) {
                GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), ItemBlockMeta.class, name);
            } else {
                GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), name);
            }
    }

    public void register(Fluid fluid) {
        FluidRegistry.registerFluid(fluid);
    }

    public void register(Fluid fluid, String name) {
        FluidContainerRegistry.registerFluidContainer(
            new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucket, 1, Utils.getItemFromArray(Constants.fluids, name)),
            new ItemStack(Items.bucket));
    }

    public void register(Class<? extends TileEntity> cls, String baseName) {
        GameRegistry.registerTileEntity(cls, Primal.MODID + "." + baseName);
    }

    public void registerEvent(Object event) {
        MinecraftForge.EVENT_BUS.register(event);
    }

    int nextPacketID = 0;

    public void registerPacket(Class cl, Side side) {
        Primal.networkWrapper.registerMessage(cl, cl, nextPacketID++, side);
    }

    public void registerWorld(IWorldGenerator world) {
        GameRegistry.registerWorldGenerator(world, 0);
    }
}
