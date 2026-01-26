package net.pufferlab.primal;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.utils.*;

public class Constants {

    // spotless:off
    public static final int wildcard = OreDictionary.WILDCARD_VALUE;
    public static final double epsilon = 2e-5;
    public static final String[] none = new String[] { "none" };
    public static final float modelConst = 0.0625F;
    public static final int tagCompound = 10;
    public static final int tagIntArray = 11;
    public static final int maxHeight = 256;
    public static final String white = EnumChatFormatting.WHITE.toString();
    public static final String gray = EnumChatFormatting.GRAY.toString();
    public static final String reset = EnumChatFormatting.RESET.toString();

    public static final short lightNone = 0x000;
    public static final short lightFire = 0xEC0;
    public static final short lightTorch = 0xDA8;
    public static final short lightHeat1 = 0xDA8;
    public static final short lightHeat2 = 0xEBA;
    public static final short lightHeat3 = 0xFDC;

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
// Igneous Extrusive
    public static final StoneCategory igneousExtrusive = new StoneCategory("igneous_extrusive");
    public static final StoneType andesite  = new StoneType(igneousExtrusive, "andesite", 40, 110, 15);
    public static final StoneType basalt    = new StoneType(igneousExtrusive, "basalt", 20, 70, 15);
    public static final StoneType dacite    = new StoneType(igneousExtrusive, "dacite", 70, 140, 15);
    public static final StoneType rhyolite  = new StoneType(igneousExtrusive, "rhyolite", 50, 100, 15);

    // Igneous Intrusive
    public static final StoneCategory igneousIntrusive = new StoneCategory("igneous_intrusive");
    public static final StoneType diorite   = new StoneType(igneousIntrusive, "diorite", 20, 60, 15);
    public static final StoneType gabbro    = new StoneType(igneousIntrusive, "gabbro", 0, 30, 10);
    public static final StoneType granite   = new StoneType(igneousIntrusive, "granite", 30, 140, 15);

    // Sedimentary
    public static final StoneCategory sedimentary = new StoneCategory("sedimentary");
    public static final StoneType claystone  = new StoneType(sedimentary, "claystone", 25, 100, 15);
    public static final StoneType limestone  = new StoneType(sedimentary, "limestone", 40, 70, 2);
    public static final StoneType sandstone  = new StoneType(sedimentary, "sandstone", 60, 120, 2);
    public static final StoneType shale      = new StoneType(sedimentary, "shale", 50, 100, 8);
    public static final StoneType chalk      = new StoneType(sedimentary, "chalk", 50, 90, 8);

    // Metamorphic
    public static final StoneCategory metamorphic = new StoneCategory("metamorphic");
    public static final StoneType slate  = new StoneType(metamorphic, "slate", 0, 50, 10);
    public static final StoneType schist = new StoneType(metamorphic, "schist", 10, 40, 10);


    public static final StoneType[] stoneTypes = new StoneType[] { andesite, basalt, dacite, rhyolite, diorite, gabbro,
        granite, claystone, limestone, sandstone, shale, chalk, slate, schist };

    public static final StoneType[][] stoneTypesLayer = StoneType.generateLayerCache(Constants.stoneTypes);

    // Metals
    public static final FluidType moltenIron = new FluidType("molten_iron", Material.lava);
    public static final MetalType iron = new MetalType("iron", false, 1148, 1538, moltenIron, 2);
    public static final FluidType moltenCastIron = new FluidType("molten_cast_iron", Material.lava);
    public static final MetalType castIron = new MetalType("cast_iron", false, 1148, 1538, moltenCastIron, 2);
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
    public static final MetalType[] toolMetalTypes = new MetalType[] { iron, copper, bronze };
    public static final String[] ingotBlacklist = new String[] { "iron", "gold" };
    public static final String[] nuggetBlacklist = new String[] { "gold" };

    // Ore Types
    public static final OreType native_copper = new OreType(copper, "native_copper");
    public static final OreType malachite = new OreType(copper, "malachite");
    public static final OreType cassiterite = new OreType(tin, "cassiterite");
    public static final OreType native_gold = new OreType(gold, "native_gold");
    public static final OreType limonite = new OreType(iron, "limonite");
    public static final OreType magnetite = new OreType(iron, "magnetite");

    public static final OreType[] oreTypes = new OreType[] { native_copper, malachite, cassiterite, native_gold,
        limonite, magnetite };

    //Special

    public static final OreType anthracite_coal = new OreType("anthracite", 0);
    public static final OreType bituminous_coal = new OreType("bituminous", 0);
    public static final OreType lignite_coal = new OreType("lignite", 0);
    public static final OreType[] coalOreTypes = new OreType[] {anthracite_coal, bituminous_coal, lignite_coal};
    public static final String[] coalOreBlacklist = new String[] { bituminous_coal.name };

    //Mod Compat
    public static final OreType aer = new OreType("aer", 0, 0xFFFF7E);
    public static final OreType ignis = new OreType("ignis", 0, 0xFF3C01);
    public static final OreType aqua = new OreType("aqua", 0, 0x0090FF);
    public static final OreType terra = new OreType("terra", 0, 0x00A000);
    public static final OreType ordo = new OreType("ordo", 0, 0xEECCFF);
    public static final OreType perditio = new OreType("perditio", 0,0x555577);

    //Vein Type

    public static final VeinType surface_copper = new VeinType(native_copper, "surface_copper", 40, 100, 3, 5, 0.5F,0.3F, 0.3F, andesite, granite, dacite, basalt, rhyolite, diorite);
    public static final VeinType deep_copper = new VeinType(native_copper, "deep_copper", 10, 40, 5, 6, 0.0F, 0.3F, 0.15F, gabbro, diorite, schist);
    public static final VeinType deep_malachite = new VeinType(malachite, "malachite", 10, 40, 5, 6, 0.0F, 0.3F, 0.15F, slate, diorite, schist);
    public static final VeinType medium_cassiterite = new VeinType(cassiterite, "medium_cassiterite", 20, 50, 3, 5, 0.0F,0.5F, 0.2F, diorite, granite, dacite, claystone, andesite);
    public static final VeinType[] veinTypes = new VeinType[] { surface_copper, deep_copper, deep_malachite, medium_cassiterite };
    public static final VeinType[][] veinTypesLayer = VeinType.generateVeinCache(Constants.veinTypes);

    public static final VeinType common_aer = new VeinType(aer, "common_aer", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType common_ignis = new VeinType(ignis, "common_ignis", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType common_aqua = new VeinType(aqua, "common_aqua", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType common_terra  = new VeinType(terra, "common_terra", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType common_ordo  = new VeinType(ordo, "common_ordo", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType common_perditio  = new VeinType(perditio, "common_perditio", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType[] tcVeinTypes = new VeinType[] { common_aer, common_ignis, common_aqua, common_terra, common_ordo, common_perditio };
    public static final VeinType[][] tcVeinTypesLayer = VeinType.generateVeinCache(Constants.tcVeinTypes);

    // Soil Types
    public static final SoilType loamy = new SoilType("loamy");
    public static final SoilType silty_loam = new SoilType("silty_loam");
    public static final SoilType silty = new SoilType("silty");
    public static final SoilType sandy = new SoilType("sandy");
    public static final SoilType sandy_loam = new SoilType("sandy_loam");
    public static final SoilType peaty = new SoilType("peaty");

    public static final SoilType[] soilTypes = new SoilType[] { loamy, silty_loam, silty, sandy, sandy_loam, peaty };

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
        "clay_knife_mold", "clay_hoe_mold", "clay_bucket" };
    public static final String[] moldItems = new String[] { "ingot_mold", "pickaxe_mold", "axe_mold", "shovel_mold",
        "sword_mold", "knife_mold", "hoe_mold" };
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
        new FluidType("red"), new FluidType("black"), moltenIron, moltenCastIron, moltenGold, moltenCopper, moltenTin, moltenBronze };
    public static final String[] fluids = FluidType.getNames(Constants.fluidsTypes);
    public static final boolean[] fluidsBreak = FluidType.getBreaks(Constants.fluidsTypes);
    public static final String[] existingFluids = FluidType.getExistingNames(Constants.fluidsTypes);

    // OreDict
    public static final String[] ashPileOreDicts = new String[] { "ash" };
    public static final String[] charcoalPileOreDicts = new String[] { "coalAny" };
    public static final String[] groundRockOreDicts = new String[] { "rock" };
    public static final String[] groundShellOreDicts = new String[] { "shell" };
    public static final String[] logPileOreDicts = new String[] { "firewood" };

    // spotless:on
}
