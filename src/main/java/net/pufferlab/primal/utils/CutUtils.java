package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.pufferlab.primal.items.IMetaItem;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

public class CutUtils {

    private static final List<Block> blockList = new ArrayList<>();
    private static final TIntList metaList = new TIntArrayList();
    private static final List<String> blockNames = new ArrayList<>();

    public static void registerBlock(Block block) {
        Item item = Item.getItemFromBlock(block);

        if (item instanceof IMetaItem item2) {
            String[] elements = item2.getElements();
            String[] elementsBlacklist = item2.getElementsBlacklist();
            String suffix = item2.getSuffix();
            for (int i = 0; i < elements.length; i++) {
                if (!Utils.contains(elementsBlacklist, elements[i])) {
                    registerBlock(block, i, elements[i] + suffix);
                }
            }
        }
    }

    public static Block getBlock(int id) {
        if (id < 0) id = 0;
        return blockList.get(id);
    }

    public static int getBlockMeta(int id) {
        if (id < 0) id = 0;
        return metaList.get(id);
    }

    public static String[] getModNames() {
        return blockNames.toArray(new String[0]);
    }

    public static void registerBlock(Block block, int meta, String name) {
        registerBlock(block, meta);
        blockNames.add(name);
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
