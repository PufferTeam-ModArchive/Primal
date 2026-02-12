package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.pufferlab.primal.Registry;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

public class CutUtils {

    private static final List<Block> blockList = new ArrayList<>();
    private static final TIntList metaList = new TIntArrayList();

    public static void registerBlock(Block block) {
        Item item = Item.getItemFromBlock(block);

        List<ItemStack> list = new ArrayList<>();
        item.getSubItems(item, Registry.creativeTab, list);
        for (ItemStack el : list) {
            if (el != null) {
                int meta = el.getItemDamage();
                registerBlock(block, meta);
            }
        }

    }

    public static Block getBlock(int id) {
        return blockList.get(id);
    }

    public static int getBlockMeta(int id) {
        return metaList.get(id);
    }

    public static void registerBlock(Block block, int meta) {
        blockList.add(block);
        metaList.add(meta);
    }

    public static int getSize() {
        return blockList.size();
    }

    public static IIcon getIcon(int side, int id) {
        Block block = CutUtils.getBlock(id);
        int meta = CutUtils.getBlockMeta(id);
        return block.getIcon(side, meta);
    }

    public static String getUnlocalizedName(int id) {
        Block block = CutUtils.getBlock(id);
        int meta = CutUtils.getBlockMeta(id);
        Item item = Item.getItemFromBlock(block);
        ItemStack stack = new ItemStack(block, 1, meta);
        return item.getUnlocalizedName(stack);
    }
}
