package net.pufferlab.primal;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.pufferlab.primal.blocks.*;
import net.pufferlab.primal.events.*;
import net.pufferlab.primal.items.*;
import net.pufferlab.primal.tileentities.*;

import cpw.mods.fml.common.registry.GameRegistry;

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

    public static Block pit_kiln;
    public static Block thatch;
    public static Block thatch_roof;
    public static Block log_pile;
    public static Block charcoal_pile;
    public static Block ash_pile;
    public static Block chopping_log;
    public static Block campfire;
    public static Block large_vessel;
    public static Block barrel;
    public static Block faucet;
    public static Item icons;
    public static Item straw;
    public static Item flint;
    public static Item wood;
    public static Item clay;
    public static Item ceramic_bucket;
    public static Item flint_axe;
    public static Item flint_pickaxe;
    public static Item flint_shovel;
    public static Item flint_knife;
    public static Item firestarter;
    public static Item.ToolMaterial toolFlint;

    public void preInit() {
        toolFlint = EnumHelper.addToolMaterial("flint", 0, 100, 2.0F, 0.0F, 15);

        pit_kiln = new BlockPitKiln();
        log_pile = new BlockLogPile();
        charcoal_pile = new BlockCharcoalPile();
        ash_pile = new BlockAshPile();
        chopping_log = new BlockChoppingLog();
        campfire = new BlockCampfire();
        large_vessel = new BlockLargeVessel();
        barrel = new BlockBarrel();
        faucet = new BlockFaucet();

        thatch = new BlockThatch();
        thatch_roof = new BlockThatchRoof();

        icons = new ItemMeta(Constants.icons, "icon");
        straw = new ItemMeta(Constants.strawItems, "straw");
        wood = new ItemMeta(Constants.woodItems, "wood");
        flint = new ItemMeta(Constants.flintItems, "flint");
        clay = new ItemMeta(Constants.clayItems, "clay");

        ceramic_bucket = new ItemBucketCeramic("ceramic_bucket");

        flint_axe = new ItemAxePrimitive(toolFlint, "flint_axe");
        flint_pickaxe = new ItemPickaxePrimitive(toolFlint, "flint_pickaxe");
        flint_shovel = new ItemShovelPrimitive(toolFlint, "flint_shovel");
        flint_knife = new ItemKnifePrimitive(toolFlint, "flint_knife");

        firestarter = new ItemFireStarter();

        register(thatch, "thatch");
        register(thatch_roof, "thatch_roof");
        register(pit_kiln, "pit_kiln");
        register(TileEntityPitKiln.class, "pit_kiln");
        register(log_pile, "log_pile");
        register(TileEntityLogPile.class, "log_pile");
        register(charcoal_pile, "charcoal_pile");
        register(TileEntityCharcoalPile.class, "charcoal_pile");
        register(ash_pile, "ash_pile");
        register(TileEntityAshPile.class, "ash_pile");
        register(chopping_log, "chopping_log");
        register(TileEntityChoppingLog.class, "chopping_log");
        register(campfire, "campfire");
        register(TileEntityCampfire.class, "campfire");
        register(large_vessel, "large_vessel");
        register(TileEntityLargeVessel.class, "large_vessel");
        register(barrel, "barrel");
        register(TileEntityBarrel.class, "barrel");
        register(faucet, "faucet");
        register(TileEntityFaucet.class, "faucet");

        register(icons, "icon");
        register(straw, "straw");
        register(wood, "wood");
        register(flint, "flint");
        register(clay, "clay");
        register(ceramic_bucket, "ceramic_bucket");
        register(flint_axe, "flint_axe");
        register(flint_pickaxe, "flint_pickaxe");
        register(flint_shovel, "flint_shovel");
        register(flint_knife, "flint_knife");
        register(firestarter, "firestarter");
    }

    public void init() {
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
    }

    public void register(Item item, String name) {
        GameRegistry.registerItem(item.setCreativeTab(Registry.creativeTab), name);
    }

    public void register(Block block, String name) {
        if (block instanceof BlockPitKiln || block instanceof BlockLogPile
            || block instanceof BlockCharcoalPile
            || block instanceof BlockAshPile
            || block instanceof BlockCampfire) {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), null, name);
        } else if (block instanceof BlockLargeVessel || block instanceof BlockBarrel) {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), ItemBlockPrimal.class, name);
        } else {
            GameRegistry.registerBlock(block.setCreativeTab(Registry.creativeTab), name);
        }
    }

    public void register(Class<? extends TileEntity> cls, String baseName) {
        GameRegistry.registerTileEntity(cls, Primal.MODID + "_" + baseName);
    }

    public void registerEvent(Object event) {
        MinecraftForge.EVENT_BUS.register(event);
    }
}
