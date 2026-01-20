package net.pufferlab.primal;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.utils.*;

public class Constants {

    public static final int wildcard = OreDictionary.WILDCARD_VALUE;
    public static final double epsilon = 2e-5;
    public static final String[] none = new String[] { "none" };
    public static final float modelConst = 0.0625F;
    public static final int tagCompound = 10;
    public static final int tagIntArray = 11;

    public static final int helmet = 0;
    public static final int chestplate = 1;
    public static final int leggings = 2;
    public static final int boots = 3;

    // Types
    public static final String[] rockTypes = new String[] { "stone" };
    public static final String[] rockTextures = new String[] { "minecraft:stone" };
    public static final String[] woodTypes = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "dark_oak" };
    public static final String[] colorTypes = new String[] { "white", "orange", "magenta", "light_blue", "yellow",
        "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black" };
    public static final String[] shellTypes = new String[] { "scallop", "conch" };

    // Stones

    // Igneous Extrusive
    public static final StoneCategory igneousExtrusive = new StoneCategory("igneous_extrusive");
    public static final StoneType andesite = new StoneType(igneousExtrusive, "andesite", 40, 120);
    public static final StoneType basalt = new StoneType(igneousExtrusive, "basalt", 0, 70);
    public static final StoneType dacite = new StoneType(igneousExtrusive, "dacite", 70, 140);
    public static final StoneType rhyolite = new StoneType(igneousExtrusive, "rhyolite", 50, 100);

    // Igneous Intrusive
    public static final StoneCategory igneousIntrusive = new StoneCategory("igneous_intrusive");
    public static final StoneType diorite = new StoneType(igneousIntrusive, "diorite", 20, 60);
    public static final StoneType gabbro = new StoneType(igneousIntrusive, "gabbro", 0, 30);
    public static final StoneType granite = new StoneType(igneousIntrusive, "granite", 30, 140);

    // Sedimentary
    public static final StoneCategory sedimentary = new StoneCategory("sedimentary");
    public static final StoneType claystone = new StoneType(sedimentary, "claystone", 30, 80);
    public static final StoneType limestone = new StoneType(sedimentary, "limestone", 40, 90);
    public static final StoneType sandstone = new StoneType(sedimentary, "sandstone", 60, 120);
    public static final StoneType shale = new StoneType(sedimentary, "shale", 50, 100);

    // Metamorphic
    public static final StoneCategory metamorphic = new StoneCategory("metamorphic");
    public static final StoneType slate = new StoneType(metamorphic, "slate", 10, 50);

    public static final StoneType[] stoneTypes = new StoneType[] { andesite, basalt, dacite, rhyolite, diorite, gabbro,
        granite, claystone, limestone, sandstone, shale, slate };

    // Metals
    public static final FluidType moltenIron = new FluidType("molten_iron", Material.lava);
    public static final MetalType iron = new MetalType("iron", false, 1148, 1538, moltenIron, 2);
    public static final FluidType moltenGold = new FluidType("molten_gold", Material.lava);
    public static final MetalType gold = new MetalType("gold", false, 750, 1064, moltenGold, 0);
    public static final FluidType moltenCopper = new FluidType("molten_copper", Material.lava);
    public static final MetalType copper = new MetalType("copper", false, 850, 1085, moltenCopper, 0);
    public static final FluidType moltenTin = new FluidType("molten_tin", Material.lava);
    public static final MetalType tin = new MetalType("tin", false, 220, 232, moltenTin, 0);
    public static final FluidType moltenBronze = new FluidType("molten_bronze", Material.lava);
    public static final MetalType bronze = new MetalType("bronze", true, 700, 950, moltenBronze, 1);
    public static final MetalType[] metalTypes = new MetalType[] { iron, gold, copper, tin, bronze, };
    public static final MetalType[] blockMetalTypes = new MetalType[] { copper, tin, bronze };
    public static final MetalType[] toolMetalTypes = new MetalType[] { bronze };
    public static final String[] ingotBlacklist = new String[] { "iron", "gold" };
    public static final String[] nuggetBlacklist = new String[] { "gold" };

    // Misc
    public static final String[] chimneyTypes = new String[] { "brick" };
    public static final String[] chimneyTextures = new String[] { "minecraft:brick" };
    public static final String[] strawItems = new String[] { "straw", "straw_cordage", "straw_kindling" };
    public static final String[] hideItems = new String[] { "hide", "soaked_hide", "scraped_hide" };
    public static final String[] woodItems = new String[] { "firewood", "ash" };
    public static final String[] glowstoneItems = new String[] { "glowstone_crystal" };
    public static final String[] powderItems = new String[] { "lime" };
    public static final String[] flintItems = new String[] { "flint_shard", "flint_axe_head", "flint_pickaxe_head",
        "flint_shovel_head", "flint_knife_blade", "flint_hoe_head" };
    public static final String[] clayItems = new String[] { "clay_brick", "clay_flower_pot", "clay_large_vessel",
        "clay_crucible", "clay_ingot_mold", "clay_pickaxe_mold", "clay_axe_mold", "clay_shovel_mold", "clay_sword_mold",
        "clay_hoe_mold", "clay_bucket" };
    public static final String[] moldItems = new String[] { "ingot_mold", "pickaxe_mold", "axe_mold", "shovel_mold",
        "sword_mold", "hoe_mold" };
    public static final String[] colorItems = new String[] { "wool", "glass", "glass_pane", "carpet", "hardened_clay" };
    public static final String[] colorItemsEFR = new String[] { "banner", "concrete", "concrete_powder",
        "glazed_terracotta", "bed" };
    public static final String[] icons = new String[] { "knapping" };

    // Food
    public static final FoodType[] flourItems = new FoodType[] {
        new FoodType("wheat_flour", 1, 0.5F, false, null, 17, 20, 1.0F) };
    public static final FoodType[] doughItems = new FoodType[] {
        new FoodType("wheat_dough", 1, 0.5F, false, null, 17, 20, 1.0F) };

    // Fluids
    public static final FluidType[] fluidsTypes = new FluidType[] {
        new FluidType("empty", Material.air).setBlock(Blocks.air)
            .setFluid(null),
        new FluidType("water", Material.water).setBlock(Blocks.flowing_water)
            .setFluid(FluidRegistry.WATER),
        new FluidType("lava", Material.lava).setBlock(Blocks.flowing_lava)
            .setFluid(FluidRegistry.LAVA),
        new FluidType("limewater"), new FluidType("tannin"), new FluidType("white"), new FluidType("orange"),
        new FluidType("magenta"), new FluidType("light_blue"), new FluidType("yellow"), new FluidType("lime"),
        new FluidType("pink"), new FluidType("gray"), new FluidType("light_gray"), new FluidType("cyan"),
        new FluidType("purple"), new FluidType("blue"), new FluidType("brown"), new FluidType("green"),
        new FluidType("red"), new FluidType("black"), moltenIron, moltenGold, moltenCopper, moltenTin, moltenBronze };
    public static final String[] fluids = FluidType.getNames(Constants.fluidsTypes);
    public static final boolean[] fluidsBreak = FluidType.getBreaks(Constants.fluidsTypes);
    public static final String[] existingFluids = FluidType.getExistingNames(Constants.fluidsTypes);

    // OreDict
    public static final String[] ashPileOreDicts = new String[] { "ash" };
    public static final String[] charcoalPileOreDicts = new String[] { "charcoal", "coal" };
    public static final String[] groundRockOreDicts = new String[] { "rock" };
    public static final String[] groundShellOreDicts = new String[] { "shell" };
    public static final String[] logPileOreDicts = new String[] { "firewood" };
}
