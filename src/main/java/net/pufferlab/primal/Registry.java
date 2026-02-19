package net.pufferlab.primal;

import java.util.Collections;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
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
import net.pufferlab.primal.events.ForbiddenMagicHandler;
import net.pufferlab.primal.inventory.CreativeTabsPrimal;
import net.pufferlab.primal.items.*;
import net.pufferlab.primal.network.packets.*;
import net.pufferlab.primal.recipes.AnvilAction;
import net.pufferlab.primal.tileentities.*;
import net.pufferlab.primal.utils.*;
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
    public static final Block stone_slab;
    public static final Block double_stone_slab;
    public static final Block vertical_stone_slab;
    public static final Block double_vertical_stone_slab;
    public static final Block stone_stairs;
    public static final Block gravel;
    public static final Block sand;
    public static final Block dirt;
    public static final Block farmland;
    public static final Block path;
    public static final Block grass;
    public static final Block native_copper_ore;
    public static final Block native_gold_ore;
    public static final Block malachite_ore;
    public static final Block cassiterite_ore;
    public static final Block limonite_ore;
    public static final Block magnetite_ore;
    public static final Block tc_air_ore;
    public static final Block tc_fire_ore;
    public static final Block tc_water_ore;
    public static final Block tc_earth_ore;
    public static final Block tc_order_ore;
    public static final Block tc_entropy_ore;
    public static final Block block;
    public static final Block anvil;
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
    public static final Item hammer_head;
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
    public static final Item bronze_hammer;
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
    public static final Item copper_hammer;
    public static final Item copper_helmet;
    public static final Item copper_chestplate;
    public static final Item copper_leggings;
    public static final Item copper_boots;
    public static final Item iron_knife;
    public static final Item iron_hammer;
    public static final Item firestarter;
    public static final Item bucket;
    public static final Item.ToolMaterial toolFlint;
    public static final Item.ToolMaterial toolCopper;
    public static final Item.ToolMaterial toolBronze;
    public static final Item.ToolMaterial toolIron;
    public static final ItemArmor.ArmorMaterial armorBronze;
    public static final ItemArmor.ArmorMaterial armorCopper;
    public static final ItemArmor.ArmorMaterial armorIron;

    static {
        toolFlint = EnumHelper.addToolMaterial("flint", 0, 100, 2.0F, 0.0F, 15);
        toolCopper = EnumHelper.addToolMaterial("copper", 0, 150, 4.0F, 0.5F, 15);
        toolBronze = EnumHelper.addToolMaterial("bronze", 1, 200, 5.0F, 1.0F, 10);
        toolIron = Item.ToolMaterial.IRON;

        armorCopper = EnumHelper.addArmorMaterial("copper", 10, new int[] { 1, 4, 3, 1 }, 15);
        armorBronze = EnumHelper.addArmorMaterial("bronze", 15, new int[] { 2, 5, 4, 1 }, 15);
        armorIron = ItemArmor.ArmorMaterial.IRON;

        Constants.copper.setToolMaterial(toolCopper)
            .setArmorMaterial(armorCopper);
        Constants.bronze.setToolMaterial(toolBronze)
            .setArmorMaterial(armorBronze);
        Constants.iron.setToolMaterial(toolIron)
            .setArmorMaterial(armorIron);

        stone = new BlockStoneRaw(Constants.stoneTypes, "raw");
        cobble = new BlockStoneRaw(Constants.stoneTypes, "cobble");
        small_bricks = new BlockStoneRaw(Constants.stoneTypes, "small_bricks");
        bricks = new BlockStoneRaw(Constants.stoneTypes, "bricks");
        smooth = new BlockStoneRaw(Constants.stoneTypes, "smooth");

        stone_slab = new BlockCutSlab(stone, false);
        double_stone_slab = new BlockCutSlab(stone, true);
        stone_stairs = new BlockCutStairs(stone);
        vertical_stone_slab = new BlockCutSlabVertical(stone, false);
        double_vertical_stone_slab = new BlockCutSlabVertical(stone, true);

        gravel = new BlockStoneGravel(Constants.stoneTypes, "gravel");
        sand = new BlockStoneSand(Constants.stoneTypes, "sand");
        dirt = new BlockSoilDirt(Constants.soilTypes, "dirt");
        grass = new BlockSoilGrass(Constants.soilTypes, "grass");
        farmland = new BlockSoilFarmland(Constants.soilTypes, "farmland");
        path = new BlockSoilPath(Constants.soilTypes, "path");

        native_copper_ore = new BlockStoneOre(Constants.stoneTypes, Constants.native_copper);
        native_gold_ore = new BlockStoneOre(Constants.stoneTypes, Constants.native_gold);
        malachite_ore = new BlockStoneOre(Constants.stoneTypes, Constants.malachite);
        cassiterite_ore = new BlockStoneOre(Constants.stoneTypes, Constants.cassiterite);
        limonite_ore = new BlockStoneOre(Constants.stoneTypes, Constants.limonite);
        magnetite_ore = new BlockStoneOre(Constants.stoneTypes, Constants.magnetite);

        if (Mods.tc.isLoaded()) {
            tc_air_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.aer);
            tc_fire_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.ignis);
            tc_water_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.aqua);
            tc_earth_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.terra);
            tc_order_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.ordo);
            tc_entropy_ore = new BlockStoneOreThaumcraft(Constants.stoneTypes, Constants.perditio);
        } else {
            tc_air_ore = null;
            tc_fire_ore = null;
            tc_water_ore = null;
            tc_earth_ore = null;
            tc_order_ore = null;
            tc_entropy_ore = null;
        }

        ground_rock = new BlockStoneGround(Constants.stoneTypes, "ground_rock");
        ground_ore = new BlockStoneGroundOre(Constants.oreTypes, "ground_ore");
        ground_shell = new BlockGroundcover(Material.rock, Constants.shellTypes, "ground_shell").setItemTexture();

        anvil = new BlockMetalAnvil(Constants.anvilMetalTypes);
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
        coal = new ItemGem(Constants.coalOreTypes, "coal", true).setBlacklist(Constants.coalOreBlacklist);

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
        hammer_head = new ItemMetal(Constants.toolMetalTypes, "hammer_head");

        copper_axe = new ItemAxeMetal(Constants.copper);
        copper_pickaxe = new ItemPickaxeMetal(Constants.copper);
        copper_shovel = new ItemShovelMetal(Constants.copper);
        copper_sword = new ItemSwordMetal(Constants.copper);
        copper_knife = new ItemKnifeMetal(Constants.copper);
        copper_hoe = new ItemHoeMetal(Constants.copper);
        copper_hammer = new ItemHammerMetal(Constants.copper);

        bronze_axe = new ItemAxeMetal(Constants.bronze);
        bronze_pickaxe = new ItemPickaxeMetal(Constants.bronze);
        bronze_shovel = new ItemShovelMetal(Constants.bronze);
        bronze_sword = new ItemSwordMetal(Constants.bronze);
        bronze_knife = new ItemKnifeMetal(Constants.bronze);
        bronze_hoe = new ItemHoeMetal(Constants.bronze);
        bronze_hammer = new ItemHammerMetal(Constants.bronze);

        iron_knife = new ItemKnifeMetal(Constants.iron);
        iron_hammer = new ItemHammerMetal(Constants.iron);

        copper_helmet = new ItemArmorCopper(Constants.copper, Constants.helmet);
        copper_chestplate = new ItemArmorCopper(Constants.copper, Constants.chestplate);
        copper_leggings = new ItemArmorCopper(Constants.copper, Constants.leggings);
        copper_boots = new ItemArmorCopper(Constants.copper, Constants.boots);

        bronze_helmet = new ItemArmorBronze(Constants.bronze, Constants.helmet);
        bronze_chestplate = new ItemArmorBronze(Constants.bronze, Constants.chestplate);
        bronze_leggings = new ItemArmorBronze(Constants.bronze, Constants.leggings);
        bronze_boots = new ItemArmorBronze(Constants.bronze, Constants.boots);

        firestarter = new ItemFireStarter();
        handstone = new ItemHandstone();
    }

    public void setup() {
        // spotless:off
        register(thatch, "thatch");
        register(thatch_roof, "thatch_roof");
        register(stone, "stone");
        register(cobble, "cobble");
        register(small_bricks, "small_bricks");
        register(bricks, "bricks");
        register(smooth, "smooth");

        registerCut(stone);
        registerCut(cobble);
        registerCut(small_bricks);
        registerCut(bricks);
        registerCut(smooth);

        register(stone_slab, "stone_slab", stone_slab, double_stone_slab, false);
        register(double_stone_slab, "double_stone_slab", stone_slab, double_stone_slab, true);
        register(vertical_stone_slab, "vertical_stone_slab", vertical_stone_slab, double_vertical_stone_slab, false);
        register(double_vertical_stone_slab, "double_vertical_stone_slab", vertical_stone_slab, double_vertical_stone_slab, true);
        register(stone_stairs, "stone_stairs");

        register(gravel, "gravel");
        register(sand, "sand");
        register(dirt, "dirt");
        register(grass, "grass");
        register(farmland, "farmland");
        register(path, "path");

        register(native_copper_ore, "native_copper");
        register(malachite_ore, "malachite");
        register(cassiterite_ore, "cassiterite");
        register(native_gold_ore, "native_gold");
        register(limonite_ore, "limonite");
        register(magnetite_ore, "magnetite");

        if (Mods.tc.isLoaded()) {
            register(tc_air_ore, "tc_infused_air");
            register(tc_fire_ore, "tc_infused_fire");
            register(tc_water_ore, "tc_infused_water");
            register(tc_earth_ore, "tc_infused_earth");
            register(tc_order_ore, "tc_infused_order");
            register(tc_entropy_ore, "tc_infused_entropy");
        }

        register(ground_rock, "ground_rock");
        register(ground_ore, "ground_ore");
        register(ground_shell, "ground_shell");

        register(block, "block");
        register(lit_torch, "lit_torch");
        register(unlit_torch, "unlit_torch");
        register(anvil, "anvil");
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
        register(hammer_head, "hammer_head");

        register(copper_axe, "copper_axe");
        register(copper_pickaxe, "copper_pickaxe");
        register(copper_shovel, "copper_shovel");
        register(copper_sword, "copper_sword");
        register(copper_knife, "copper_knife");
        register(copper_hoe, "copper_hoe");
        register(copper_hammer, "copper_hammer");

        register(bronze_axe, "bronze_axe");
        register(bronze_pickaxe, "bronze_pickaxe");
        register(bronze_shovel, "bronze_shovel");
        register(bronze_sword, "bronze_sword");
        register(bronze_knife, "bronze_knife");
        register(bronze_hoe, "bronze_hoe");
        register(bronze_hammer, "bronze_hammer");

        register(iron_knife, "iron_knife");
        register(iron_hammer, "iron_hammer");

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
        // spotless:on
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
        register(TileEntityAnvil.class, "anvil");
        register(TileEntityCut.class, "cut_block");
        register(TileEntityCutDouble.class, "double_cut_block");
    }

    public static final Block[] fluidsBlocks = new Block[Constants.fluidsTypes.length];
    public static final Fluid[] fluidsObjects = new Fluid[Constants.fluidsTypes.length];

    public void setupFluids() {
        Constants.empty.setBlock(Blocks.air)
            .setFluid(null);
        Constants.water.setBlock(Blocks.flowing_water)
            .setFluid(FluidRegistry.WATER);
        Constants.lava.setBlock(Blocks.flowing_lava)
            .setFluid(FluidRegistry.LAVA);
        for (int i = 0; i < Constants.fluidsTypes.length; i++) {
            FluidType fluidType = Constants.fluidsTypes[i];
            if (!fluidType.existingFluid) {
                Fluid fluid = new FluidPrimal(fluidType.name).setDensity(fluidType.density)
                    .setViscosity(fluidType.viscosity)
                    .setTemperature(fluidType.temperature);
                register(fluid);
                Material mat = Material.water;
                if (fluidType.hotLiquid) {
                    mat = Material.lava;
                }
                Block block = new BlockFluidPrimal(fluid, mat, fluidType.name);
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
        registerPacket(PacketSpeedButton.class, Side.SERVER);
        registerPacket(PacketAnvilPlan.class, Side.SERVER);
        registerPacket(PacketAnvilWork.class, Side.SERVER);
        registerPacket(PacketCutMaterial.class, Side.SERVER);

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

    public void setupConfig() {
        ConfigUtils.initConfigMap();

        Constants.minHeight = Config.minimumYHeight.getInt();
        Constants.maxHeight = Config.maximumYHeight.getInt();

        for (MetalType type : Constants.metalTypes) {
            Fluid fluid = ConfigUtils.getMetalFluid(type);
            if (fluid != null) {
                type.setFluid(fluid);
            }
            int temp = ConfigUtils.getMetalMelting(type);
            if (temp > 0) {
                type.setMeltingTemperature(temp);
            }
        }
        MetalType.setFluids(Constants.metalTypes);

        for (StoneType type : Constants.stoneTypes) {
            int min = ConfigUtils.getStrataHeightMin(type);
            int max = ConfigUtils.getStrataHeightMax(type);
            type.setHeight(min, max);
        }

        for (VeinType type : Constants.veinTypesAll) {
            int min = ConfigUtils.getVeinHeightMin(type);
            int max = ConfigUtils.getVeinHeightMax(type);
            type.setHeight(min, max);
            int minSize = ConfigUtils.getVeinSizeMin(type);
            int maxSize = ConfigUtils.getVeinSizeMax(type);
            type.setSize(minSize, maxSize);
        }

        StoneType.genLayerCache(Constants.stoneTypes);
        VeinType.genVeinCache(Constants.veinTypes);
        VeinType.genTcVeinCache(Constants.tcVeinTypes);

        for (AnvilAction action : AnvilAction.values()) {
            int step = ConfigUtils.getAnvilStep(action);
            action.setStep(step);
        }
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
        registerWorld(new PrimalEarlyGenerator(), 10000);
        registerWorld(new PrimalLateGenerator(), 20000);
        registerWorld(new PrimalDecorator(), 30000);
    }

    public void register(Item item, String name) {
        GameRegistry.registerItem(item.setCreativeTab(Registry.creativeTab), name);
        registerModItem(item, name);
    }

    public void register(Block block, String name) {
        register(block, name, new Object[] {});
    }

    public void register(Block block, String name, Object... objects) {
        boolean hasItemBlock = true;
        if (block instanceof IPrimalBlock block2) {
            if (block2.canRegister()) {
                if (block2.getItemBlockClass() == null) hasItemBlock = false;
                GameRegistry.registerBlock(
                    block.setCreativeTab(block2.getCreativeTab()),
                    block2.getItemBlockClass(),
                    name,
                    objects);
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
            if (item2.registerModItem()) {
                String[] elements = item2.getElements();
                String[] elementsBlacklist = item2.getElementsBlacklist();
                String elementName = item2.getElementName();
                String suffix = item2.getSuffix();
                for (int i = 0; i < elements.length; i++) {
                    if (!Utils.contains(elementsBlacklist, elements[i])) {
                        ItemStack stack = new ItemStack(item, 1, i);
                        ItemUtils.registerModItem(elements[i] + suffix, stack);
                        if (item2.registerOre()) {
                            ItemUtils
                                .registerModOreDict(ItemUtils.getOreDictionaryName(elementName, elements[i]), stack);
                        }
                    }
                }
            }
        } else {
            ItemUtils.registerModItem(name, new ItemStack(item, 1, 0));
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
            CommandHandler ch = Primal.proxy.getCommandHandler();
            ch.registerCommand(command);
        }
    }

    public void registerHeat(Item item, Item maskItem) {
        HeatUtils.registerImpl(item, Collections.singletonList(0), maskItem);
    }

    public void registerHeat(Item item, Item maskItem, MetalType metal) {
        HeatUtils.registerImpl(item, Collections.singletonList(0), maskItem, Collections.singletonList(metal));
    }

    public void registerCut(Block block) {
        CutUtils.registerBlock(block);
    }
}
