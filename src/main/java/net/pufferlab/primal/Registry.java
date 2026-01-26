package net.pufferlab.primal;

import java.util.Collections;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.*;
import net.pufferlab.primal.blocks.*;
import net.pufferlab.primal.commands.*;
import net.pufferlab.primal.compat.minetweaker.MTCompat;
import net.pufferlab.primal.compat.nei.NEICompat;
import net.pufferlab.primal.compat.waila.WLCompat;
import net.pufferlab.primal.events.*;
import net.pufferlab.primal.events.compat.ForbiddenMagicHandler;
import net.pufferlab.primal.events.packets.*;
import net.pufferlab.primal.inventory.CreativeTabsPrimal;
import net.pufferlab.primal.items.*;
import net.pufferlab.primal.tileentities.*;
import net.pufferlab.primal.utils.FluidType;
import net.pufferlab.primal.utils.MetalType;
import net.pufferlab.primal.utils.StoneType;
import net.pufferlab.primal.utils.TemperatureUtils;
import net.pufferlab.primal.world.PrimalDecorator;
import net.pufferlab.primal.world.PrimalEarlyGenerator;
import net.pufferlab.primal.world.PrimalLateGenerator;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import minetweaker.MineTweakerAPI;

public class Registry {

    public static final CreativeTabs creativeTab = new CreativeTabsPrimal("", "firestarter");
    public static final CreativeTabs creativeTabWorld = new CreativeTabsPrimal("World", "stone");

    public static final Block ground_ore;
    public static final Block ground_rock;
    public static final Block ground_shell;
    public static final Block pit_kiln;
    public static final Block thatch;
    public static final Block thatch_roof;
    public static final Block stone;
    public static final Block cobble;
    public static final Block small_bricks;
    public static final Block bricks;
    public static final Block smooth;
    public static final Block gravel;
    public static final Block sand;
    public static final Block dirt;
    public static final Block farmland;
    public static final Block path;
    public static final Block grass;
    public static final Block native_copper;
    public static final Block native_gold;
    public static final Block malachite;
    public static final Block cassiterite;
    public static final Block limonite;
    public static final Block magnetite;
    public static final Block block;
    public static final Block lit_torch;
    public static final Block unlit_torch;
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
    public static final Block waterwheel;
    public static final Block windmill;
    public static final Item ore;
    public static final Item small_ore;
    public static final Item coal;
    public static final Item icons;
    public static final Item straw;
    public static final Item hide;
    public static final Item rock;
    public static final Item shell;
    public static final Item flint;
    public static final Item wood;
    public static final Item glowstone;
    public static final Item bark;
    public static final Item flour;
    public static final Item dough;
    public static final Item powder;
    public static final Item handstone;
    public static final Item mold;
    public static final Item clay;
    public static final Item ceramic_bucket;
    public static final Item ceramic_bucket_modded;
    public static final Item ingot;
    public static final Item nugget;
    public static final Item axe_head;
    public static final Item pickaxe_head;
    public static final Item shovel_head;
    public static final Item sword_blade;
    public static final Item knife_blade;
    public static final Item hoe_head;
    public static final Item flint_axe;
    public static final Item flint_pickaxe;
    public static final Item flint_shovel;
    public static final Item flint_knife;
    public static final Item flint_hoe;
    public static final Item bronze_axe;
    public static final Item bronze_pickaxe;
    public static final Item bronze_shovel;
    public static final Item bronze_sword;
    public static final Item bronze_knife;
    public static final Item bronze_hoe;
    public static final Item bronze_helmet;
    public static final Item bronze_chestplate;
    public static final Item bronze_leggings;
    public static final Item bronze_boots;
    public static final Item copper_axe;
    public static final Item copper_pickaxe;
    public static final Item copper_shovel;
    public static final Item copper_sword;
    public static final Item copper_knife;
    public static final Item copper_hoe;
    public static final Item copper_helmet;
    public static final Item copper_chestplate;
    public static final Item copper_leggings;
    public static final Item copper_boots;
    public static final Item iron_knife;
    public static final Item firestarter;
    public static final Item bucket;
    public static final Item.ToolMaterial toolFlint;
    public static final Item.ToolMaterial toolCopper;
    public static final Item.ToolMaterial toolBronze;
    public static final Item.ToolMaterial toolIron;
    public static final ItemArmor.ArmorMaterial armorBronze;
    public static final ItemArmor.ArmorMaterial armorCopper;

    public static Block tc_air_ore;
    public static Block tc_fire_ore;
    public static Block tc_water_ore;
    public static Block tc_earth_ore;
    public static Block tc_order_ore;
    public static Block tc_entropy_ore;

    static {
        toolFlint = EnumHelper.addToolMaterial("flint", 0, 100, 2.0F, 0.0F, 15);
        toolCopper = EnumHelper.addToolMaterial("copper", 0, 150, 4.0F, 0.5F, 15);
        toolBronze = EnumHelper.addToolMaterial("bronze", 1, 200, 5.0F, 1.0F, 10);
        toolIron = Item.ToolMaterial.IRON;

        armorCopper = EnumHelper.addArmorMaterial("copper", 10, new int[] { 1, 4, 3, 1 }, 15);
        armorBronze = EnumHelper.addArmorMaterial("bronze", 15, new int[] { 2, 5, 4, 1 }, 15);

        ground_rock = new BlockStoneGround(Constants.stoneTypes, "ground_rock");
        ground_ore = new BlockStoneGroundOre(Constants.oreTypes, "ground_ore");
        stone = new BlockStoneRaw(Constants.stoneTypes, "raw");
        cobble = new BlockStoneRaw(Constants.stoneTypes, "cobble");
        small_bricks = new BlockStoneRaw(Constants.stoneTypes, "small_bricks");
        bricks = new BlockStoneRaw(Constants.stoneTypes, "bricks");
        smooth = new BlockStoneRaw(Constants.stoneTypes, "smooth");
        gravel = new BlockStoneGravel(Constants.stoneTypes, "gravel");
        sand = new BlockStoneSand(Constants.stoneTypes, "sand");
        dirt = new BlockSoilDirt(Constants.soilTypes, "dirt");
        grass = new BlockSoilGrass(Constants.soilTypes, "grass");
        farmland = new BlockSoilFarmland(Constants.soilTypes, "farmland");
        path = new BlockSoilPath(Constants.soilTypes, "path");

        native_copper = new BlockStoneOre(Constants.stoneTypes, Constants.native_copper);
        native_gold = new BlockStoneOre(Constants.stoneTypes, Constants.native_gold);
        malachite = new BlockStoneOre(Constants.stoneTypes, Constants.malachite);
        cassiterite = new BlockStoneOre(Constants.stoneTypes, Constants.cassiterite);
        limonite = new BlockStoneOre(Constants.stoneTypes, Constants.limonite);
        magnetite = new BlockStoneOre(Constants.stoneTypes, Constants.magnetite);

        ground_shell = new BlockGroundcover(Material.rock, Constants.shellTypes, "ground_shell").setItemTexture();
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
        waterwheel = new BlockWaterwheel();
        windmill = new BlockWindmill();

        thatch = new BlockThatch();
        thatch_roof = new BlockThatchRoof();
        block = new BlockMetal(Constants.blockMetalTypes, "block");
        lit_torch = new BlockTorchPrimitive("torch_lit").setLightLevel(0.9375F);
        unlit_torch = new BlockTorchPrimitive("torch_unlit").setLightLevel(0.0F);

        chimney = new BlockChimney();

        rock = new ItemRock(Constants.stoneTypes, "rock");
        ((BlockGroundcover) ground_rock).setItem(rock);

        ore = new ItemOre(Constants.oreTypes, "medium_ore", true);
        small_ore = new ItemOre(Constants.oreTypes, "small_ore");
        ((BlockGroundcover) ground_ore).setItem(small_ore);
        coal = new ItemGem(Constants.coalOreTypes, "coal", true).setBlacklist(Constants.coalOreBlacklist);;

        icons = new ItemMeta(Constants.icons, "icon").setHiddenAll()
            .setHasSuffix();
        straw = new ItemMeta(Constants.strawItems, "straw");
        hide = new ItemMeta(Constants.hideItems, "hide");
        wood = new ItemMeta(Constants.woodItems, "wood");
        glowstone = new ItemMeta(Constants.glowstoneItems, "glowstone");
        bark = new ItemMeta(Constants.woodTypes, "bark").setHasSuffix();
        flint = new ItemMeta(Constants.flintItems, "flint");
        shell = new ItemMeta(Constants.shellTypes, "shell");
        powder = new ItemMeta(Constants.powderItems, "powder").setHasSuffix();
        mold = new ItemMeta(Constants.moldItems, "mold");
        clay = new ItemMeta(Constants.clayItems, "clay");
        ((BlockGroundcover) ground_shell).setItem(shell);

        ingot = new ItemMetal(Constants.metalTypes, "ingot").setBlacklist(Constants.ingotBlacklist);
        nugget = new ItemMetal(Constants.metalTypes, "nugget").setBlacklist(Constants.nuggetBlacklist);

        dough = new ItemMetaFood(Constants.doughItems, "dough");
        flour = new ItemMetaFood(Constants.flourItems, "flour");

        bucket = new ItemBucketMeta("bucket");
        ceramic_bucket = new ItemBucketCeramic("ceramic_bucket");
        ceramic_bucket_modded = new ItemBucketCeramicModded("ceramic_bucket_modded");

        flint_axe = new ItemAxePrimitive(toolFlint, "flint_axe");
        flint_pickaxe = new ItemPickaxePrimitive(toolFlint, "flint_pickaxe");
        flint_shovel = new ItemShovelPrimitive(toolFlint, "flint_shovel");
        flint_knife = new ItemKnifePrimitive(toolFlint, "flint_knife");
        flint_hoe = new ItemHoePrimitive(toolFlint, "flint_hoe");

        axe_head = new ItemMetal(Constants.toolMetalTypes, "axe_head");
        pickaxe_head = new ItemMetal(Constants.toolMetalTypes, "pickaxe_head");
        shovel_head = new ItemMetal(Constants.toolMetalTypes, "shovel_head");
        sword_blade = new ItemMetal(Constants.toolMetalTypes, "sword_blade");
        knife_blade = new ItemMetal(Constants.toolMetalTypes, "knife_blade");
        hoe_head = new ItemMetal(Constants.toolMetalTypes, "hoe_head");

        copper_axe = new ItemAxePrimitive(toolCopper, "copper_axe");
        copper_pickaxe = new ItemPickaxePrimitive(toolCopper, "copper_pickaxe");
        copper_shovel = new ItemShovelPrimitive(toolCopper, "copper_shovel");
        copper_sword = new ItemSwordPrimitive(toolCopper, "copper_sword");
        copper_knife = new ItemKnifePrimitive(toolCopper, "copper_knife");
        copper_hoe = new ItemHoePrimitive(toolCopper, "copper_hoe");

        bronze_axe = new ItemAxePrimitive(toolBronze, "bronze_axe");
        bronze_pickaxe = new ItemPickaxePrimitive(toolBronze, "bronze_pickaxe");
        bronze_shovel = new ItemShovelPrimitive(toolBronze, "bronze_shovel");
        bronze_sword = new ItemSwordPrimitive(toolBronze, "bronze_sword");
        bronze_knife = new ItemKnifePrimitive(toolBronze, "bronze_knife");
        bronze_hoe = new ItemHoePrimitive(toolBronze, "bronze_hoe");

        iron_knife = new ItemKnifePrimitive(toolIron, "iron_knife");

        copper_helmet = new ItemArmorCopper(armorCopper, "copper_helmet", Constants.helmet);
        copper_chestplate = new ItemArmorCopper(armorCopper, "copper_chestplate", Constants.chestplate);
        copper_leggings = new ItemArmorCopper(armorCopper, "copper_leggings", Constants.leggings);
        copper_boots = new ItemArmorCopper(armorCopper, "copper_boots", Constants.boots);

        bronze_helmet = new ItemArmorBronze(armorBronze, "bronze_helmet", Constants.helmet);
        bronze_chestplate = new ItemArmorBronze(armorBronze, "bronze_chestplate", Constants.chestplate);
        bronze_leggings = new ItemArmorBronze(armorBronze, "bronze_leggings", Constants.leggings);
        bronze_boots = new ItemArmorBronze(armorBronze, "bronze_boots", Constants.boots);

        firestarter = new ItemFireStarter();
        handstone = new ItemHandstone();
    }

    public void setup() {
        register(thatch, "thatch");
        register(thatch_roof, "thatch_roof");
        register(stone, "stone");
        register(cobble, "cobble");
        register(small_bricks, "small_bricks");
        register(bricks, "bricks");
        register(smooth, "smooth");
        register(gravel, "gravel");
        register(sand, "sand");
        register(dirt, "dirt");
        register(grass, "grass");
        register(farmland, "farmland");
        register(path, "path");

        register(native_copper, "native_copper");
        register(malachite, "malachite");
        register(cassiterite, "cassiterite");
        register(native_gold, "native_gold");
        register(limonite, "limonite");
        register(magnetite, "magnetite");

        register(ground_rock, "ground_rock");
        register(ground_ore, "ground_ore");

        register(block, "block");
        register(lit_torch, "lit_torch");
        register(unlit_torch, "unlit_torch");
        register(ground_shell, "ground_shell");
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
        register(waterwheel, "waterwheel");
        register(windmill, "windmill");

        register(ore, "ore");
        register(small_ore, "small_ore");
        register(coal, "coal");
        register(icons, "icon");
        register(straw, "straw");
        register(hide, "hide");
        register(wood, "wood");
        register(glowstone, "glowstone");
        register(bark, "bark");
        register(flour, "flour");
        register(dough, "dough");
        register(powder, "powder");
        register(rock, "rock");
        register(shell, "shell");
        register(flint, "flint");
        register(mold, "mold");
        register(clay, "clay");

        register(ingot, "ingot");
        register(nugget, "nugget");

        register(flint_axe, "flint_axe");
        register(flint_pickaxe, "flint_pickaxe");
        register(flint_shovel, "flint_shovel");
        register(flint_knife, "flint_knife");
        register(flint_hoe, "flint_hoe");

        register(axe_head, "axe_head");
        register(pickaxe_head, "pickaxe_head");
        register(shovel_head, "shovel_head");
        register(sword_blade, "sword_blade");
        register(knife_blade, "knife_blade");
        register(hoe_head, "hoe_head");

        register(copper_axe, "copper_axe");
        register(copper_pickaxe, "copper_pickaxe");
        register(copper_shovel, "copper_shovel");
        register(copper_sword, "copper_sword");
        register(copper_knife, "copper_knife");
        register(copper_hoe, "copper_hoe");

        register(bronze_axe, "bronze_axe");
        register(bronze_pickaxe, "bronze_pickaxe");
        register(bronze_shovel, "bronze_shovel");
        register(bronze_sword, "bronze_sword");
        register(bronze_knife, "bronze_knife");
        register(bronze_hoe, "bronze_hoe");

        register(iron_knife, "iron_knife");

        register(copper_helmet, "copper_helmet");
        register(copper_chestplate, "copper_chestplate");
        register(copper_leggings, "copper_leggings");
        register(copper_boots, "copper_boots");

        register(bronze_helmet, "bronze_helmet");
        register(bronze_chestplate, "bronze_chestplate");
        register(bronze_leggings, "bronze_leggings");
        register(bronze_boots, "bronze_boots");

        register(firestarter, "firestarter");
        register(handstone, "handstone");
        register(bucket, "bucket");
        register(ceramic_bucket, "ceramic_bucket");
        register(ceramic_bucket_modded, "ceramic_bucket_modded");
    }

    public void setupThaumcraft() {
        if (Mods.tc.isLoaded()) {
            tc_air_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.aer);
            tc_fire_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.ignis);
            tc_water_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.aqua);
            tc_earth_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.terra);
            tc_order_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.ordo);
            tc_entropy_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.perditio);

            register(tc_air_ore, "tc_infused_air");
            register(tc_fire_ore, "tc_infused_fire");
            register(tc_water_ore, "tc_infused_water");
            register(tc_earth_ore, "tc_infused_earth");
            register(tc_order_ore, "tc_infused_order");
            register(tc_entropy_ore, "tc_infused_entropy");
        }
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
        register(TileEntityWaterwheel.class, "waterwheel");
        register(TileEntityWindmill.class, "windmill");
    }

    public static final Block[] fluidsBlocks = new Block[Constants.fluidsTypes.length];
    public static final Fluid[] fluidsObjects = new Fluid[Constants.fluidsTypes.length];

    public void setupFluids() {
        for (int i = 0; i < Constants.fluidsTypes.length; i++) {
            FluidType fluidType = Constants.fluidsTypes[i];
            if (fluidType.block == null && fluidType.fluid == null) {
                Fluid fluid = new FluidPrimal(fluidType.name).setDensity(fluidType.density)
                    .setViscosity(fluidType.viscosity)
                    .setTemperature(fluidType.temperature);
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
        registerPacket(PacketSpeedChange.class, Side.SERVER);

        registerPacket(PacketSwingArm.class, Side.CLIENT);
        registerPacket(PacketFireStarter.class, Side.CLIENT);
        registerPacket(PacketSpeedUpdate.class, Side.CLIENT);
        registerPacket(PacketWorldTime.class, Side.CLIENT);
        registerPacket(PacketPlayerData.class, Side.CLIENT);
    }

    public void setupEvents() {
        registerEvent(new TickHandler());

        registerEvent(new PitKilnHandler());
        registerEvent(new KnappingHandler());
        registerEvent(new BucketHandler());
        registerEvent(new ToolHandler());
        registerEvent(new LogPileHandler());
        registerEvent(new CharcoalPileHandler());
        registerEvent(new AshPileHandler());
        registerEvent(new CampfireHandler());
        registerEvent(new InventoryHandler());
        registerEvent(new StoneHandler());
        registerEvent(new GroundRockHandler());
        registerEvent(new GroundShellHandler());
        registerEvent(new MobDropHandler());
        registerEvent(new CastHandler());
        registerEvent(new HeatHandler());
        registerEvent(new FoodHandler());
        registerEvent(new PlayerHandler());

        if (Mods.fm.isLoaded()) {
            registerEvent(new ForbiddenMagicHandler());
        }
    }

    public void setupServer() {
        StoneType.registerStone(Constants.stoneTypes, Registry.stone);
        StoneType.registerStone(Constants.stoneTypes, Registry.gravel);
    }

    public void setupCommands() {
        registerCommand(new CommandPrimal());

        registerCommand(new CommandTPS());
        registerCommand(new CommandTickTime());
        registerCommand(new CommandModGive());
        registerCommand(new CommandTemperature());
        registerCommand(new CommandSchedule());
        registerCommand(new CommandClearBlocks());
        registerCommand(new CommandStrata());
        registerCommand(new CommandVein());
    }

    public void setupMetals() {
        for (MetalType type : Constants.metalTypes) {
            Fluid fluid = Utils.getMetalFluid(type.name);
            if (fluid != null) {
                type.setFluid(fluid);
            }
            int temp = Utils.getMetalMelting(type.name);
            if (temp > 0) {
                type.setMeltingTemperature(temp);
            }
        }
        MetalType.setFluids(Constants.metalTypes);
    }

    public void setupModCompat() {
        ((ItemBucketCeramicModded) ceramic_bucket_modded).registerModdedLiquids();
        PrimalEarlyGenerator.strataGen.initBlockList();
        PrimalEarlyGenerator.soilGen.initBlockList();
        BlockStoneOreThaumcraft.setupShards();
    }

    public void setupHeatables() {
        registerHeat(Items.iron_ingot, Registry.ingot, Constants.iron);
        registerHeat(Items.gold_ingot, Registry.ingot, Constants.gold);
        registerHeat(Items.gold_nugget, Registry.nugget, Constants.gold);
    }

    public void setupDebug() {
        for (MetalType type : Constants.metalTypes) {
            type.meltingTemperature = 10;
        }
    }

    public void setupNEI() {
        if (Mods.nei.isLoaded()) {
            new NEICompat().loadConfig();
        }
    }

    public void setupMT() {
        if (Mods.mt.isLoaded()) {
            MineTweakerAPI.registerClass(MTCompat.class);
        }
    }

    public void setupWAILA() {
        if (Mods.wl.isLoaded() && !Mods.wd.isLoaded()) {
            new WLCompat().loadConfig();
        }
    }

    public void setupWorldGen() {
        registerWorld(PrimalEarlyGenerator.instance, 10000);
        registerWorld(PrimalLateGenerator.instance, 20000);
        registerWorld(PrimalDecorator.instance, 30000);
    }

    public void register(Item item, String name) {
        GameRegistry.registerItem(item.setCreativeTab(Registry.creativeTab), name);
        registerModItem(item, name);
    }

    public void register(Block block, String name) {
        boolean hasItemBlock = true;
        if (block instanceof IPrimalBlock block2) {
            if (block2.canRegister()) {
                if (block2.getItemBlockClass() == null) hasItemBlock = false;
                GameRegistry
                    .registerBlock(block.setCreativeTab(block2.getCreativeTab()), block2.getItemBlockClass(), name);
            } else {
                hasItemBlock = false;
            }
        } else {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), name);
        }
        if (hasItemBlock) {
            registerModItem(block, name);
        }
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

    public void registerModItem(Block block, String name) {
        registerModItem(Item.getItemFromBlock(block), name);
    }

    public void registerModItem(Item item, String name) {
        if (item instanceof IMetaItem item2) {
            String[] elements = item2.getElements();
            String[] elementsBlacklist = item2.getElementsBlacklist();
            String elementName = item2.getElementName();
            String suffix = "";
            if (item2.hasSuffix()) {
                suffix = "_" + elementName;
            }
            for (int i = 0; i < elements.length; i++) {
                if (!Utils.contains(elementsBlacklist, elements[i])) {
                    ItemStack stack = new ItemStack(item2.getItemObject(), 1, i);
                    Utils.registerModItem(elements[i] + suffix, stack);
                    if (item2.registerOre()) {
                        Utils.registerModOreDict(Utils.getOreDictionaryName(elementName, elements[i]), stack);
                    }
                }
            }
        } else {
            Utils.registerModItem(name, new ItemStack(item, 1, 0));
        }
    }

    public void register(Class<? extends TileEntity> cls, String baseName) {
        GameRegistry.registerTileEntity(cls, Primal.MODID + "." + baseName);
    }

    public void registerEvent(Object event) {
        if (event instanceof IEventHandler eventh) {
            if (eventh.isFMLEvent()) {
                FMLCommonHandler.instance()
                    .bus()
                    .register(event);
            }
        }
        MinecraftForge.EVENT_BUS.register(event);
    }

    private static int nextPacketID = 0;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void registerPacket(Class cl, Side side) {
        Primal.network.registerMessage(cl, cl, nextPacketID++, side);
    }

    public void registerWorld(IWorldGenerator world, int weight) {
        GameRegistry.registerWorldGenerator(world, weight);
    }

    public void registerCommand(ICommand command) {
        if (command instanceof ISubCommand command2) {
            CommandPrimal.registerSubCommand(command2);
        } else {
            CommandHandler ch = (CommandHandler) Primal.proxy.getServer()
                .getCommandManager();
            ch.registerCommand(command);
        }
    }

    public void registerHeat(Item item, Item maskItem) {
        TemperatureUtils.registerImpl(item, Collections.singletonList(0), maskItem);
    }

    public void registerHeat(Item item, Item maskItem, MetalType metal) {
        TemperatureUtils.registerImpl(item, Collections.singletonList(0), maskItem, Collections.singletonList(metal));
    }
}
