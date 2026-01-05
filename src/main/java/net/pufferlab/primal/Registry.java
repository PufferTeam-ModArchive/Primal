package net.pufferlab.primal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.*;
import net.pufferlab.primal.blocks.*;
import net.pufferlab.primal.compat.minetweaker.MTCompat;
import net.pufferlab.primal.compat.nei.NEICompat;
import net.pufferlab.primal.compat.waila.WLCompat;
import net.pufferlab.primal.events.*;
import net.pufferlab.primal.items.*;
import net.pufferlab.primal.tileentities.*;

import cpw.mods.fml.common.FMLCommonHandler;
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

    public static final Block ground_rock;
    public static final Block pit_kiln;
    public static final Block thatch;
    public static final Block thatch_roof;
    public static final Block chimney;
    public static final Block log_pile;
    public static final Block charcoal_pile;
    public static final Block ash_pile;
    public static final Block tanning;
    public static final Block chopping_log;
    public static final Block campfire;
    public static final Block oven;
    public static final Block forge;
    public static final Block crucible;
    public static final Block cast;
    public static final Block large_vessel;
    public static final Block barrel;
    public static final Block faucet;
    public static final Block quern;
    public static final Block axle;
    public static final Block generator;
    public static final Item gear;
    public static final Item icons;
    public static final Item straw;
    public static final Item hide;
    public static final Item rock;
    public static final Item flint;
    public static final Item wood;
    public static final Item bark;
    public static final Item flour;
    public static final Item dough;
    public static final Item powder;
    public static final Item handstone;
    public static final Item mold;
    public static final Item clay;
    public static final Item ceramic_bucket;
    public static final Item flint_axe;
    public static final Item flint_pickaxe;
    public static final Item flint_shovel;
    public static final Item flint_knife;
    public static final Item firestarter;
    public static final Item bucket;
    public static final Item.ToolMaterial toolFlint;

    static {
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
        oven = new BlockOven();
        quern = new BlockQuern();
        forge = new BlockForge();
        cast = new BlockCast();
        crucible = new BlockCrucible();
        large_vessel = new BlockLargeVessel();
        barrel = new BlockBarrel();
        faucet = new BlockFaucet();

        axle = new BlockAxle();
        generator = new BlockGenerator();

        thatch = new BlockThatch();
        thatch_roof = new BlockThatchRoof();
        chimney = new BlockChimney();

        icons = new ItemMeta(Constants.icons, "icon").setHiddenAll()
            .setHasSuffix();
        gear = new ItemMeta(Constants.gearItems, "gear");
        straw = new ItemMeta(Constants.strawItems, "straw");
        hide = new ItemMeta(Constants.hideItems, "hide");
        wood = new ItemMeta(Constants.woodItems, "wood");
        bark = new ItemMeta(Constants.barkItems, "bark").setHasSuffix();
        flint = new ItemMeta(Constants.flintItems, "flint");
        rock = new ItemMeta(Constants.rockTypes, "rock").setHasSuffix();
        powder = new ItemMeta(Constants.powderItems, "powder");
        mold = new ItemMeta(Constants.moldItems, "mold");
        clay = new ItemMeta(Constants.clayItems, "clay");
        ((BlockGroundcover) ground_rock).setItem(rock);

        dough = new ItemMetaFood(Constants.doughItems, "dough");
        flour = new ItemMetaFood(Constants.flourItems, "flour");

        bucket = new ItemBucketMeta("bucket");
        ceramic_bucket = new ItemBucketCeramic("ceramic_bucket");

        flint_axe = new ItemAxePrimitive(toolFlint, "flint_axe");
        flint_pickaxe = new ItemPickaxePrimitive(toolFlint, "flint_pickaxe");
        flint_shovel = new ItemShovelPrimitive(toolFlint, "flint_shovel");
        flint_knife = new ItemKnifePrimitive(toolFlint, "flint_knife");

        firestarter = new ItemFireStarter();
        handstone = new ItemHandstone();
    }

    public void setup() {
        register(thatch, "thatch");
        register(thatch_roof, "thatch_roof");
        register(ground_rock, "ground_rock");
        register(pit_kiln, "pit_kiln");
        register(log_pile, "log_pile");
        register(charcoal_pile, "charcoal_pile");
        register(ash_pile, "ash_pile");
        register(chopping_log, "chopping_log");
        register(campfire, "campfire");
        register(oven, "oven");
        register(chimney, "chimney");
        register(forge, "forge");
        register(cast, "cast");
        register(quern, "quern");
        register(large_vessel, "large_vessel");
        register(crucible, "crucible");
        register(barrel, "barrel");
        register(faucet, "faucet");
        register(tanning, "tanning_frame");
        register(axle, "axle");
        register(generator, "generator");

        register(icons, "icon");
        register(gear, "gear");
        register(straw, "straw");
        register(hide, "hide");
        register(wood, "wood");
        register(bark, "bark");
        register(flour, "flour");
        register(dough, "dough");
        register(powder, "powder");
        register(rock, "rock");
        register(flint, "flint");
        register(mold, "mold");
        register(clay, "clay");
        register(flint_axe, "flint_axe");
        register(flint_pickaxe, "flint_pickaxe");
        register(flint_shovel, "flint_shovel");
        register(flint_knife, "flint_knife");
        register(firestarter, "firestarter");
        register(handstone, "handstone");
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
        register(TileEntityOven.class, "oven");
        register(TileEntityCrucible.class, "crucible");
        register(TileEntityForge.class, "forge");
        register(TileEntityCast.class, "cast");
        register(TileEntityQuern.class, "quern");
        register(TileEntityAxle.class, "axle");
        register(TileEntityGenerator.class, "generator");
    }

    public static final Block[] fluidsBlocks = new Block[Constants.fluidsTypes.length];
    public static final Fluid[] fluidsObjects = new Fluid[Constants.fluidsTypes.length];

    public void setupFluids() {
        for (int i = 0; i < Constants.fluidsTypes.length; i++) {
            FluidType fluidType = Constants.fluidsTypes[i];
            if (fluidType.block == null && fluidType.fluid == null) {
                Fluid fluid = new FluidPrimal(fluidType.name).setDensity(fluidType.density)
                    .setViscosity(fluidType.viscosity);
                register(fluid);
                Block block = new BlockFluidPrimal(fluid, fluidType.material, fluidType.name);
                fluid.setBlock(block);
                register(block, fluidType.name);
                register(fluid, fluidType.name);
                fluidType.setFluid(fluid);
                fluidType.setBlock(block);
            }
            fluidsObjects[i] = fluidType.fluid;
            fluidsBlocks[i] = fluidType.block;
        }
    }

    public void setupPackets() {
        Primal.network = NetworkRegistry.INSTANCE.newSimpleChannel(Primal.MODID);
        registerPacket(PacketKnappingClick.class, Side.SERVER);
        registerPacket(PacketPitKilnPlace.class, Side.SERVER);
        registerPacket(PacketSwingArm.class, Side.CLIENT);
        registerPacket(PacketFireStarter.class, Side.CLIENT);
        registerPacket(PacketSpeedUpdate.class, Side.CLIENT);
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
        registerEvent(new CastHandler());
        registerEvent(new HeatHandler());
        registerEvent(new FoodHandler());
        registerEvent(new TickHandler());
    }

    public void setupNEI() {
        if (Primal.NEILoaded) {
            new NEICompat().loadConfig();
        }
    }

    public void setupMT() {
        if (Primal.MTLoaded) {
            MineTweakerAPI.registerClass(MTCompat.class);
        }
    }

    public void setupWAILA() {
        if (Primal.WLLoaded) {
            new WLCompat().loadConfig();
        }
    }

    public void register(Item item, String name) {
        GameRegistry.registerItem(item.setCreativeTab(Registry.creativeTab), name);
        registerModItem(item);
    }

    public void register(Block block, String name) {
        if (block instanceof BlockPile || block instanceof BlockGroundcover || block instanceof BlockCast) {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), null, name);
        } else if ((block instanceof BlockLargeVessel || block instanceof BlockBarrel || block == campfire)) {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), ItemBlockPrimal.class, name);
        } else if (block instanceof BlockFluidPrimal) {
            GameRegistry.registerBlock(block.setCreativeTab(null), name);
        } else if (block instanceof BlockMeta) {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), ItemBlockMeta.class, name);
        } else if (block instanceof BlockCrucible) {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), ItemBlockHeatable.class, name);
        } else if (block instanceof BlockMotion) {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), ItemBlockMotion.class, name);
        } else {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), name);
        }
        registerModItem(block);
    }

    public void register(Fluid fluid) {
        FluidRegistry.registerFluid(fluid);
    }

    public void register(Fluid fluid, String name) {
        FluidContainerRegistry.registerFluidContainer(
            new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME),
            new ItemStack(bucket, 1, Utils.getIndex(Constants.fluids, name)),
            new ItemStack(Items.bucket));
    }

    public void registerModItem(Object item) {
        if (item instanceof IMetaItem item2) {
            String[] elements = item2.getElements();
            String elementName = item2.getElementName();
            String suffix = "";
            if (item2.hasSuffix()) {
                suffix = "_" + elementName;
            }
            for (int i = 0; i < elements.length; i++) {
                Utils.registerModItem(elements[i] + suffix, new ItemStack(item2.getItemObject(), 1, i));
            }
        }
    }

    public void register(Class<? extends TileEntity> cls, String baseName) {
        GameRegistry.registerTileEntity(cls, Primal.MODID + "." + baseName);
    }

    public void registerEvent(Object event) {
        if (event instanceof TickHandler) {
            FMLCommonHandler.instance()
                .bus()
                .register(event);
        }
        MinecraftForge.EVENT_BUS.register(event);
    }

    private static int nextPacketID = 0;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void registerPacket(Class cl, Side side) {
        Primal.network.registerMessage(cl, cl, nextPacketID++, side);
    }

    public void registerWorld(IWorldGenerator world) {
        GameRegistry.registerWorldGenerator(world, 0);
    }
}
