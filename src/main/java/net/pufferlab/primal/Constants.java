package net.pufferlab.primal;

import net.minecraft.block.material.Material;
import net.pufferlab.primal.blocks.MaterialPrimal;

public class Constants {

    public static final String[] none = new String[] { "none" };
    public static final float modelConst = 0.0625F;
    public static final String[] rockTypes = new String[] { "stone" };
    public static final String[] rockTextures = new String[] { "minecraft:stone" };
    public static final String[] uniqueItemTypes = new String[] { "item", "straw", "hide", "wood", "powder", "flint",
        "clay" };
    public static final String[] strawItems = new String[] { "straw", "straw_cordage", "straw_kindling" };
    public static final String[] hideItems = new String[] { "hide", "soaked_hide", "scraped_hide" };
    public static final String[] woodItems = new String[] { "firewood", "ash" };
    public static final String[] powderItems = new String[] { "lime" };
    public static final String[] flintItems = new String[] { "flint_shard", "flint_axe_head", "flint_pickaxe_head",
        "flint_shovel_head", "flint_knife_blade" };
    public static final String[] clayItems = new String[] { "clay_brick", "clay_flower_pot", "clay_large_vessel",
        "clay_bucket" };
    public static final String[] colorTypes = new String[] { "white", "orange", "magenta", "light_blue", "yellow",
        "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black" };
    public static final String[] colorItems = new String[] { "wool", "glass", "glass_pane", "carpet", "hardened_clay" };
    public static final String[] colorItemsEFR = new String[] { "banner", "concrete", "concrete_powder",
        "glazed_terracotta", "bed" };
    public static final String[] icons = new String[] { "knapping" };
    public static final String[] fluids = new String[] { "empty", "water", "lava", "limewater", "tannin", "white",
        "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue",
        "brown", "green", "red", "black" };
    public static final Material[] fluidsMaterial = new Material[] { null, null, null, MaterialPrimal.limewater,
        MaterialPrimal.tannin, MaterialPrimal.dye, MaterialPrimal.dye, MaterialPrimal.dye, MaterialPrimal.dye,
        MaterialPrimal.dye, MaterialPrimal.dye, MaterialPrimal.dye, MaterialPrimal.dye, MaterialPrimal.dye,
        MaterialPrimal.dye, MaterialPrimal.dye, MaterialPrimal.dye, MaterialPrimal.dye, MaterialPrimal.dye,
        MaterialPrimal.dye, MaterialPrimal.dye };
    public static final boolean[] fluidsBreak = new boolean[] { false, false, true, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false };
    public static final String[] vanillaFluids = new String[] { "empty", "water", "lava" };
    public static final String[] ashPileOreDicts = new String[] { "ash" };
    public static final String[] charcoalPileOreDicts = new String[] { "charcoal", "coal" };
    public static final String[] groundRockOreDicts = new String[] { "rock" };
    public static final String[] logPileOreDicts = new String[] { "firewood" };
}
