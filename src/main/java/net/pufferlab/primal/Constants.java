package net.pufferlab.primal;

import net.pufferlab.primal.utils.*;

public class Constants {

    // spotless:off
    public static final int wildcard = Short.MAX_VALUE;
    public static final double epsilon = 2e-5;
    public static final String[] none = { "none" };
    public static final float pixel = 0.0625F;
    public static final int tagCompound = 10;
    public static final int tagIntArray = 11;
    public static final String white = "§f";
    public static final String gray  = "§7";
    public static final String reset = "§r";

    public static int minHeight = 0;
    public static int maxHeight = 255;

    public static final short lightNone = 0x000;
    public static final short lightFire = 0xEC0;
    public static final short lightTorch = 0xDA8;
    public static final short lightHeat1 = 0xDA8;
    public static final short lightHeat2 = 0xEBA;
    public static final short lightHeat3 = 0xFDC;

    public static final int effectHunger = 17;

    public static final String helmet = "helmet";
    public static final String chestplate = "chestplate";
    public static final String leggings = "leggings";
    public static final String boots = "boots";

    public static final String head = "head";
    public static final String body = "body";

    public static final String hat = "hat";
    public static final String shirt = "shirt";
    public static final String pants = "pants";
    public static final String shoes = "shoes";

    // Types
    public static final int rockModel = 0;
    public static final int itemModel = 1;
    public static final String[] woodTypes = { "oak", "spruce", "birch", "jungle", "acacia", "dark_oak" };
    public static final String[] colorTypes = { "white", "orange", "magenta", "light_blue", "yellow",
        "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black" };
    public static final String[] shellTypes = { "scallop", "conch" };

    // Stones

    // Igneous Extrusive
    public static final StoneCategory igneousExtrusive  = new StoneCategory("igneous_extrusive");
    public static final StoneType andesite              = new StoneType(igneousExtrusive, "andesite", 40, 110, 15);
    public static final StoneType basalt                = new StoneType(igneousExtrusive, "basalt", 20, 70, 15);
    public static final StoneType dacite                = new StoneType(igneousExtrusive, "dacite", 70, 140, 15);
    public static final StoneType rhyolite              = new StoneType(igneousExtrusive, "rhyolite", 50, 100, 15);

    // Igneous Intrusive
    public static final StoneCategory igneousIntrusive = new StoneCategory("igneous_intrusive");
    public static final StoneType diorite               = new StoneType(igneousIntrusive, "diorite", 20, 60, 15);
    public static final StoneType gabbro                = new StoneType(igneousIntrusive, "gabbro", 0, 30, 10);
    public static final StoneType granite               = new StoneType(igneousIntrusive, "granite", 30, 140, 15);

    // Sedimentary
    public static final StoneCategory sedimentary   = new StoneCategory("sedimentary");
    public static final StoneType claystone         = new StoneType(sedimentary, "claystone", 25, 100, 15);
    public static final StoneType limestone         = new StoneType(sedimentary, "limestone", 40, 70, 2);
    public static final StoneType sandstone         = new StoneType(sedimentary, "sandstone", 60, 120, 2);
    public static final StoneType shale             = new StoneType(sedimentary, "shale", 50, 100, 8);
    public static final StoneType chalk             = new StoneType(sedimentary, "chalk", 50, 90, 8);

    // Metamorphic
    public static final StoneCategory metamorphic   = new StoneCategory("metamorphic");
    public static final StoneType slate             = new StoneType(metamorphic, "slate", 0, 50, 10);
    public static final StoneType schist            = new StoneType(metamorphic, "schist", 10, 40, 10);


    public static final StoneType[] stoneTypes = new StoneType[] { andesite, basalt, dacite, rhyolite, diorite, gabbro,
        granite, claystone, limestone, sandstone, shale, chalk, slate, schist };

    // Metals
    public static final FluidType moltenIron        = new FluidType("molten_iron", true);
    public static final MetalType iron              = new MetalType("iron", false, 921, 1148, 1538, moltenIron, 2);
    public static final FluidType moltenCastIron    = new FluidType("molten_cast_iron", true);
    public static final MetalType castIron          = new MetalType("cast_iron", false, 921, 1148, 1538, moltenCastIron, 2);
    public static final FluidType moltenGold        = new FluidType("molten_gold", true);
    public static final MetalType gold              = new MetalType("gold", false, 636, 848, 1064, moltenGold, 0);
    public static final FluidType moltenCopper      = new FluidType("molten_copper", true);
    public static final MetalType copper            = new MetalType("copper", false, 648, 850, 1085, moltenCopper, 0);
    public static final FluidType moltenTin         = new FluidType("molten_tin", true);
    public static final MetalType tin               = new MetalType("tin", false, 138, 184, 232, moltenTin, 0);
    public static final FluidType moltenBronze      = new FluidType("molten_bronze", true);
    public static final MetalType bronze            = new MetalType("bronze", true, 570, 760, 950, moltenBronze, 1);
    public static final MetalType[] metalTypes      = { iron, gold, copper, tin, bronze, };
    public static final MetalType[] blockMetalTypes = { copper, tin, bronze };
    public static final MetalType[] toolMetalTypes  = { iron, copper, bronze };
    public static final MetalType[] anvilMetalTypes = { iron, copper, bronze };
    public static final MetalType[] metalTypesAll   = { iron, castIron, gold, copper, tin, bronze, };
    public static final String[] ingotBlacklist     = { "iron", "gold" };
    public static final String[] nuggetBlacklist    = { "gold" };

    // Ore Types
    public static final OreType native_copper   = new OreType(copper, "native_copper");
    public static final OreType malachite       = new OreType(copper, "malachite");
    public static final OreType cassiterite     = new OreType(tin, "cassiterite");
    public static final OreType native_gold     = new OreType(gold, "native_gold");
    public static final OreType limonite        = new OreType(castIron, "limonite");
    public static final OreType magnetite       = new OreType(castIron, "magnetite");
    public static final OreType hematite        = new OreType(castIron, "hematite");

    public static final OreType[] oreTypes = { native_copper, malachite, cassiterite, native_gold,
        limonite, magnetite, hematite };

    //Special

    public static final OreType anthracite_coal     = new OreType("anthracite_coal", 0);
    public static final OreType bituminous_coal     = new OreType("bituminous_coal", 0).hasNoGem();
    public static final OreType lignite_coal        = new OreType("lignite_coal", 0);
    public static final OreType[] coalOreTypes      = {anthracite_coal, bituminous_coal, lignite_coal};

    public static final OreType diamond = new OreType("diamond", 2).hasNoGem();
    public static final OreType emerald = new OreType("emerald", 2).hasNoGem();
    public static final OreType amethyst = new OreType("amethyst", 2);
    public static final OreType lapis_lazuli = new OreType("lapis_lazuli", 2).hasNoGem();
    public static final OreType opal = new OreType("opal", 2);
    public static final OreType ruby = new OreType("ruby", 2);
    public static final OreType sapphire = new OreType("sapphire", 2);
    public static final OreType topaz = new OreType("topaz", 2);
    public static final OreType amber = new OreType("amber", 2);
    public static final OreType[] gemOreTypes      = {diamond, emerald, amethyst, lapis_lazuli, opal, ruby, sapphire, topaz, amber};

    //Mod Compat
    public static final int aerColor = 0xFFFF7E;
    public static final int ignisColor = 0xFF3C01;
    public static final int aquaColor = 0x0090FF;
    public static final int terraColor = 0x00A000;
    public static final int ordoColor = 0xEECCFF;
    public static final int perditioColor = 0x555577;

    public static final OreType aer         = new OreType("aer", 0, aerColor);
    public static final OreType ignis       = new OreType("ignis", 0, ignisColor);
    public static final OreType aqua        = new OreType("aqua", 0, aquaColor);
    public static final OreType terra       = new OreType("terra", 0, terraColor);
    public static final OreType ordo        = new OreType("ordo", 0, ordoColor);
    public static final OreType perditio    = new OreType("perditio", 0,perditioColor);

    public static final OreType[] tcOreTypes = {aer, ignis, aqua, terra, ordo, perditio};

    //Vein Type

    public static final VeinType surface_copper     = new VeinType(native_copper, "surface_copper", 40, 100, 3, 5, 0.5F,0.3F, 0.4F, andesite, granite, dacite, basalt, rhyolite, diorite, shale, chalk);
    public static final VeinType deep_copper        = new VeinType(native_copper, "deep_copper", 10, 40, 5, 6, 0.0F, 0.3F, 0.15F, gabbro, diorite, schist);
    public static final VeinType deep_malachite     = new VeinType(malachite, "malachite", 10, 40, 5, 6, 0.0F, 0.3F, 0.15F, slate, diorite, schist);
    public static final VeinType medium_cassiterite = new VeinType(cassiterite, "medium_cassiterite", 20, 50, 3, 5, 0.0F,0.5F, 0.2F, diorite, granite, dacite, claystone, andesite);
    public static final VeinType medium_magnetite = new VeinType(magnetite, "medium_magnetite", 10, 60, 3, 6, 0.0F,0.5F, 0.3F, slate, schist, dacite, granite, gabbro, diorite, granite);
    public static final VeinType medium_limonite = new VeinType(limonite, "medium_limonite", 0, 40, 3, 5, 0.0F,0.5F, 0.15F, slate, schist, dacite, granite, gabbro);
    public static final VeinType[] veinTypes        = { surface_copper, deep_copper, deep_malachite, medium_cassiterite, medium_magnetite, medium_limonite };

    public static final VeinType common_aer         = new VeinType(aer, "common_aer", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType common_ignis       = new VeinType(ignis, "common_ignis", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType common_aqua        = new VeinType(aqua, "common_aqua", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType common_terra       = new VeinType(terra, "common_terra", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType common_ordo        = new VeinType(ordo, "common_ordo", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType common_perditio    = new VeinType(perditio, "common_perditio", 0, 100, 2, 4, 0.0F, 0.3F, 0.4F, stoneTypes);
    public static final VeinType[] tcVeinTypes      = { common_aer, common_ignis, common_aqua, common_terra, common_ordo, common_perditio };
    public static final VeinType[] veinTypesAll     = Utils.combineArrays(veinTypes, tcVeinTypes);

    // Soil Types
    public static final SoilType loamy      = new SoilType("loamy");
    public static final SoilType silty_loam = new SoilType("silty_loam");
    public static final SoilType silty      = new SoilType("silty");
    public static final SoilType sandy      = new SoilType("sandy");
    public static final SoilType sandy_loam = new SoilType("sandy_loam");
    public static final SoilType peaty      = new SoilType("peaty");

    public static final SoilType[] soilTypes = { loamy, silty_loam, silty, sandy, sandy_loam, peaty };

    // Food
    public static final int crossedModel = 0;
    public static final int crossedModel32 = 2;
    public static final int cropModel = 1;
    public static final int cropModel32 = 3;

    public static final FoodType blueberry = new FoodType("blueberry", 1, 0.6F);
    public static final FoodType strawberry = new FoodType("strawberry", 1, 0.6F);
    public static final FoodType blackberry = new FoodType("blackberry", 1, 0.6F);
    public static final FoodType onion = new FoodType("onion", 2, 0.6F);
    public static final FoodType tomato = new FoodType("tomato", 2, 0.6F);
    public static final FoodType cabbage = new FoodType("cabbage", 2, 0.6F);
    public static final FoodType carrot = new FoodType("carrot", 2, 0.6F);
    public static final FoodType potato = new FoodType("potato", 2, 0.6F);
    public static final FoodType garlic = new FoodType("garlic", 2, 0.6F);
    public static final FoodType corn = new FoodType("corn", 2, 0.6F);

    public static final FoodType wheat = new FoodType("wheat");
    public static final FoodType oat = new FoodType("oat");
    public static final FoodType barley = new FoodType("barley");
    public static final FoodType rye = new FoodType("rye");
    public static final FoodType flax = new FoodType("flax");
    public static final FoodType jute = new FoodType("jute");
    public static final FoodType cotton = new FoodType("cotton");
    public static final FoodType sugar_cane = new FoodType("sugarcane");

    public static final FoodType wheat_flour = new FoodType("wheat_flour", 1, 0.5F, false, null, effectHunger, 20, 1.0F);
    public static final FoodType oat_flour = new FoodType("oat_flour", 1, 0.5F, false, null, effectHunger, 20, 1.0F);
    public static final FoodType barley_flour = new FoodType("barley_flour", 1, 0.5F, false, null, effectHunger, 20, 1.0F);
    public static final FoodType rye_flour = new FoodType("rye_flour", 1, 0.5F, false, null, effectHunger, 20, 1.0F);

    public static final FoodType wheat_dough = new FoodType("wheat_dough", 1, 0.5F, false, null, effectHunger, 20, 1.0F);
    public static final FoodType oat_dough = new FoodType("oat_dough", 1, 0.5F, false, null, effectHunger, 20, 1.0F);
    public static final FoodType barley_dough = new FoodType("barley_dough", 1, 0.5F, false, null, effectHunger, 20, 1.0F);
    public static final FoodType rye_dough = new FoodType("rye_dough", 1, 0.5F, false, null, effectHunger, 20, 1.0F);

    public static final FoodType wheat_bread = new FoodType("wheat_bread", 5, 0.6F).hasNoFoodItem();
    public static final FoodType oat_bread = new FoodType("oat_bread", 5, 0.6F);
    public static final FoodType barley_bread = new FoodType("barley_bread", 5, 0.6F);
    public static final FoodType rye_bread = new FoodType("rye_bread", 5, 0.6F);
    public static final FoodType[] flourItems       = {wheat_flour, oat_flour, barley_flour, rye_flour};
    public static final FoodType[] doughItems       = {wheat_dough, oat_dough, barley_dough, rye_dough };
    public static final FoodType[] breadItems       = {wheat_bread, oat_bread, barley_bread, rye_bread};
    public static final CropType blueberry_crop = new CropType(blueberry, "blueberry", 2, 3, 6, 'N', 0.15f, 1.20f, crossedModel);
    public static final CropType strawberry_crop = new CropType(strawberry, "strawberry", 2, 3, 6, 'P', 0.35f, 1.20f, crossedModel);
    public static final CropType blackberry_crop = new CropType(blackberry, "blackberry", 2, 3, 6, 'P', 0.35f, 1.20f, crossedModel);

    public static final CropType onion_crop = new CropType(onion, "onion", 7, 1, 3, 'P', 0.35f, 1.85f, crossedModel);
    public static final CropType tomatoes_crop = new CropType(tomato, "tomatoes", 8, 1, 3, 'P', 0.35f, 1.85f, crossedModel32);
    public static final CropType cabbages_crop = new CropType(cabbage, "cabbages", 6, 1, 3, 'N', 0.40f, 1.50f, crossedModel);

    public static final CropType carrots_crop = new CropType(carrot, "carrots", 5, 1, 3, 'K', 0.40f, 1.20f, cropModel).hasNoCropItem();
    public static final CropType potatoes_crop = new CropType(potato, "potatoes", 7, 1, 3, 'K', 0.50f, 1.20f, cropModel).hasNoCropItem();
    public static final CropType garlic_crop = new CropType(garlic, "garlic", 5, 1, 3, 'P', 0.35f, 1.20f, crossedModel);

    public static final CropType corn_crop = new CropType(corn, "corn", 5, 1, 3, 'N', 0.40f, 1.25f, cropModel32);

    public static final CropType wheat_crop = new CropType(wheat, "wheat", 8, 1, 3, 'N', 0.40f, 2.00f, cropModel).hasNoCropFood().hasNoCropItem().hasNoSeedItem();
    public static final CropType oat_crop = new CropType(oat, "oat", 8, 1, 3, 'N', 0.40f, 2.00f, cropModel).hasNoCropFood();
    public static final CropType barley_crop = new CropType(barley, "barley", 8, 1, 3, 'N', 0.40f, 2.00f, cropModel).hasNoCropFood();
    public static final CropType rye_crop = new CropType(rye, "rye", 8, 1, 3, 'N', 0.40f, 2.00f, cropModel).hasNoCropFood();

    public static final CropType flax_crop = new CropType(flax, "flax", 6, 1, 3, 'K', 0.50f, 2.00f, cropModel32).hasNoCropFood();
    public static final CropType jute_crop = new CropType(jute, "jute", 6, 1, 3, 'P', 0.45f, 2.50f, cropModel32).hasNoCropFood();
    public static final CropType cotton_crop = new CropType(cotton, "cotton", 6, 1, 3, 'P', 0.20f, 2.20f, crossedModel32).hasNoCropFood();

    public static final CropType sugar_canes_crop = new CropType(sugar_cane, "sugarcanes", 8, 1, 3, 'K', 0.50f, 2.25f, crossedModel32).hasNoCropFood();

    public static final CropType[] crops = {wheat_crop, oat_crop, barley_crop, rye_crop, flax_crop, jute_crop, cotton_crop, sugar_canes_crop, onion_crop, tomatoes_crop, cabbages_crop, carrots_crop, potatoes_crop, garlic_crop, corn_crop};
    public static final CropType[] berries = {blueberry_crop, strawberry_crop, blackberry_crop};
    public static final FoodType[] foodTypesAll = Utils.combineArrays(CropType.getFoodTypes(berries), CropType.getFoodTypes(crops), flourItems, doughItems, breadItems);

    // Misc
    public static final String[] chimneyTypes       = { "brick" };
    public static final String[] chimneyTextures    = { "minecraft:brick" };
    public static final String[] strawItems         = { "straw", "straw_cordage", "straw_kindling" };
    public static final String[] hideItems          = { "hide", "soaked_hide", "scraped_hide" };
    public static final String[] woodItems          = { "firewood", "ash" };
    public static final String[] glowstoneItems     = { "glowstone_crystal" };
    public static final String[] powderItems        = { "lime" };
    public static final String[] bloomItems         = { "raw_iron_bloom", "refined_iron_bloom" };
    public static final String[] flintItems         = { "flint_shard", "flint_axe_head", "flint_pickaxe_head",  "flint_shovel_head", "flint_knife_blade", "flint_hoe_head" };
    public static final String[] clayItems          = { "clay_brick", "clay_flower_pot", "clay_large_vessel", "clay_crucible", "clay_ingot_mold", "clay_pickaxe_mold",
                                                        "clay_axe_mold", "clay_shovel_mold", "clay_sword_mold", "clay_knife_mold", "clay_hoe_mold", "clay_hammer_mold", "clay_bucket" };
    public static final String[] moldItems          = { "ingot_mold", "pickaxe_mold", "axe_mold", "shovel_mold",
                                                        "sword_mold", "knife_mold", "hoe_mold", "hammer_mold" };
    public static final String[] colorItems         = { "wool", "glass", "glass_pane", "carpet", "hardened_clay" };
    public static final String[] colorItemsEFR      = { "banner", "concrete", "concrete_powder",
                                                        "glazed_terracotta", "bed" };
    public static final String[] icons              = { "knapping" };
    public static final String[] brickItems         = { "fire_bricks" };

    // Fluids
    public static final FluidType empty             = new FluidType("empty", false, true);
    public static final FluidType water             = new FluidType("water", false, true);
    public static final FluidType lava              = new FluidType("lava", true, true);
    public static final FluidType limewaterFluid    = new FluidType("limewater");
    public static final FluidType tanninFluid       = new FluidType("tannin");

    public static final FluidType whitePigment      = new FluidType("white");
    public static final FluidType orangePigment     = new FluidType("orange");
    public static final FluidType magentaPigment    = new FluidType("magenta");
    public static final FluidType lightBluePigment  = new FluidType("light_blue");
    public static final FluidType yellowPigment     = new FluidType("yellow");
    public static final FluidType limePigment       = new FluidType("lime");
    public static final FluidType pinkPigment       = new FluidType("pink");
    public static final FluidType grayPigment       = new FluidType("gray");
    public static final FluidType lightGrayPigment  = new FluidType("light_gray");
    public static final FluidType cyanPigment       = new FluidType("cyan");
    public static final FluidType purplePigment     = new FluidType("purple");
    public static final FluidType bluePigment       = new FluidType("blue");
    public static final FluidType brownPigment      = new FluidType("brown");
    public static final FluidType greenPigment      = new FluidType("green");
    public static final FluidType redPigment        = new FluidType("red");
    public static final FluidType blackPigment      = new FluidType("black");

    public static final FluidType[] fluidsTypes = {
        empty, water, lava,
        limewaterFluid, tanninFluid,
        whitePigment, orangePigment, magentaPigment, lightBluePigment,
        yellowPigment, limePigment, pinkPigment, grayPigment,
        lightGrayPigment, cyanPigment, purplePigment, bluePigment,
        brownPigment, greenPigment, redPigment, blackPigment,
        moltenIron, moltenCastIron, moltenGold,
        moltenCopper, moltenTin, moltenBronze
    };
    public static String[] fluids = FluidType.getNames(Constants.fluidsTypes);
    public static boolean[] fluidsBreak = FluidType.getBreaks(Constants.fluidsTypes);
    public static String[] existingFluids = FluidType.getExistingNames(Constants.fluidsTypes);

    // OreDict
    public static final String[] ashPileOreDicts            = { "ash" };
    public static final String[] charcoalPileOreDicts       = { "coalAny" };
    public static final String[] groundOreOreDicts         = { "smallOre" };
    public static final String[] groundRockOreDicts         = { "rock" };
    public static final String[] groundShellOreDicts        = { "shell" };
    public static final String[] logPileOreDicts            = { "firewood" };

    // spotless:on
}
