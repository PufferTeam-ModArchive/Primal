package net.pufferlab.primitivelife;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primitivelife.blocks.BlockPitKiln;
import net.pufferlab.primitivelife.blocks.BlockThatch;
import net.pufferlab.primitivelife.blocks.BlockThatchRoof;
import net.pufferlab.primitivelife.items.*;
import net.pufferlab.primitivelife.tileentities.TileEntityPitKiln;

import cpw.mods.fml.common.registry.GameRegistry;

public class Registry {

    public static Block pit_kiln;
    public static Block thatch;
    public static Block thatch_roof;
    public static Item item;
    public static Item ceramic_bucket;
    public static Item flint_axe;
    public static Item flint_pickaxe;
    public static Item flint_shovel;
    public static Item flint_knife;
    public static Item.ToolMaterial toolFlint;

    public void preInit() {
        toolFlint = EnumHelper.addToolMaterial("flint", 0, 100, 2.0F, 0.0F, 15);

        pit_kiln = new BlockPitKiln();
        thatch = new BlockThatch();
        thatch_roof = new BlockThatchRoof();
        item = new ItemMaterial(Constants.miscItems, "item");
        ceramic_bucket = new ItemPrimitiveBucket("ceramic_bucket");

        flint_axe = new ItemPrimitiveAxe(toolFlint, "flint_axe");
        flint_pickaxe = new ItemPrimitivePickaxe(toolFlint, "flint_pickaxe");
        flint_shovel = new ItemPrimitiveShovel(toolFlint, "flint_shovel");
        flint_knife = new ItemPrimitiveKnife(toolFlint, "flint_knife");

        register(thatch, "thatch");
        register(thatch_roof, "thatch_roof");
        register(pit_kiln, "pit_kiln");

        register(item, "item");
        register(ceramic_bucket, "ceramic_bucket");
        register(flint_axe, "flint_axe");
        register(flint_pickaxe, "flint_pickaxe");
        register(flint_shovel, "flint_shovel");
        register(flint_knife, "flint_knife");
    }

    public void preInitTE() {
        register(TileEntityPitKiln.class, "pit_kiln");
    }

    public void initOreDicts() {
        OreDictionary.registerOre(
            "straw",
            new ItemStack(Registry.item, 1, Utils.getItemFromArray(Constants.miscItems, "straw")));
    }

    public void register(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

    public void register(Block block, String name) {
        if (block instanceof BlockPitKiln) {
            GameRegistry.registerBlock(block, null, name);
        } else {
            GameRegistry.registerBlock(block, name);
        }
    }

    public void register(Class<? extends TileEntity> cls, String baseName) {
        GameRegistry.registerTileEntity(cls, "primitivelife_" + baseName);
    }
}
