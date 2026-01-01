package net.pufferlab.primal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.pufferlab.primal.items.Food;

public class Constants {

    public static final String[] none = new String[] { "none" };
    public static final float modelConst = 0.0625F;
    public static final String[] rockTypes = new String[] { "stone" };
    public static final String[] rockTextures = new String[] { "minecraft:stone" };
    public static final String[] chimneyTypes = new String[] { "brick" };
    public static final String[] chimneyTextures = new String[] { "minecraft:brick" };
    public static final String[] uniqueItemTypes = new String[] { "item", "straw", "hide", "wood", "powder", "flint",
        "clay", "mold" };
    public static final String[] strawItems = new String[] { "straw", "straw_cordage", "straw_kindling" };
    public static final String[] hideItems = new String[] { "hide", "soaked_hide", "scraped_hide" };
    public static final String[] woodItems = new String[] { "firewood", "ash", "bark"};
    public static final String[] powderItems = new String[] { "lime" };
    public static final String[] flintItems = new String[] { "flint_shard", "flint_axe_head", "flint_pickaxe_head",
        "flint_shovel_head", "flint_knife_blade" };
    public static final String[] clayItems = new String[] { "clay_brick", "clay_flower_pot", "clay_large_vessel",
        "clay_crucible", "clay_pickaxe_mold", "clay_axe_mold", "clay_shovel_mold", "clay_sword_mold", "clay_bucket" };
    public static final String[] moldItems = new String[] { "pickaxe_mold", "axe_mold", "shovel_mold", "sword_mold" };
    public static final String[] colorTypes = new String[] { "white", "orange", "magenta", "light_blue", "yellow",
        "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black" };
    public static final String[] colorItems = new String[] { "wool", "glass", "glass_pane", "carpet", "hardened_clay" };
    public static final String[] colorItemsEFR = new String[] { "banner", "concrete", "concrete_powder",
        "glazed_terracotta", "bed" };
    public static final String[] icons = new String[] { "knapping" };
    public static final Food[] flourItems = new Food[] { new Food("wheat_flour", 1, 0.5F, false, null, 17, 20, 1.0F) };
    public static final Food[] doughItems = new Food[] { new Food("wheat_dough", 1, 0.5F, false, null, 17, 20, 1.0F) };
    public static final String[] fluids = new String[] { "empty", "water", "lava", "limewater", "tannin", "white",
        "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue",
        "brown", "green", "red", "black" };
    public static final Material[] fluidsMaterial = new Material[] { null, Material.water, Material.lava,
        Material.water, Material.water, Material.water, Material.water, Material.water, Material.water, Material.water,
        Material.water, Material.water, Material.water, Material.water, Material.water, Material.water, Material.water,
        Material.water, Material.water, Material.water, Material.water };
    public static final boolean[] fluidsBreak = new boolean[] { false, false, true, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false };
    public static final String[] vanillaFluids = new String[] { "empty", "water", "lava" };
    public static final Fluid[] vanillaFluidsObjects = new Fluid[] { null, FluidRegistry.WATER, FluidRegistry.LAVA };
    public static final Block[] vanillaFluidsBlocks = new Block[] { Blocks.air, Blocks.flowing_water,
        Blocks.flowing_lava };
    public static final String[] ashPileOreDicts = new String[] { "ash" };
    public static final String[] charcoalPileOreDicts = new String[] { "charcoal", "coal" };
    public static final String[] groundRockOreDicts = new String[] { "rock" };
    public static final String[] logPileOreDicts = new String[] { "firewood" };
}
