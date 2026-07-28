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

    public enum CutMaterial {
        stone,
        wood
    }

    private static final List<Block> blockList = new ArrayList<>();
    private static final TIntList metaList = new TIntArrayList();
    private static final List<CutMaterial> matList = new ArrayList<>();
    private static final List<String> blockNames = new ArrayList<>();
    private static final List<ItemStack> itemStackList = new ArrayList<>();

    private static Block[] blockArray;
    private static int[] metaArray;
    private static String[] blockNamesArray;
    private static ItemStack[] itemStackArray;

    public static void registerStoneBlock(Block block) {
        registerBlock(CutMaterial.stone, block);
    }

    public static void registerBlock(CutMaterial mat, Block block) {
        Item item = Item.getItemFromBlock(block);

        if (item instanceof IMetaItem item2) {
            String[] elements = item2.getElements();
            String[] elementsBlacklist = item2.getElementsBlacklist();
            String suffix = item2.getSuffix();
            for (int i = 0; i < elements.length; i++) {
                if (!Utils.contains(elementsBlacklist, elements[i])) {
                    registerBlock(mat, block, i, elements[i] + suffix);
                }
            }
        } else {
            registerBlock(mat, block, 0);
        }
    }

    public static void registerBlock(CutMaterial mat, Block block, int meta, String name) {
        registerBlock(mat, block, meta);
        blockNames.add(name);
    }

    public static void registerBlock(CutMaterial mat, Block block, int meta) {
        blockList.add(block);
        metaList.add(meta);
        matList.add(mat);
    }

    public static Block getBlock(int id) {
        if (id < 0 || id >= blockArray.length) id = 0;
        return blockArray[id];
    }

    public static int getBlockMeta(int id) {
        if (id < 0 || id >= metaArray.length) id = 0;
        return metaArray[id];
    }

    public static void getSubBlocks(Block thiz, List<ItemStack> list) {
        for (int i = 0; i < metaArray.length; i++) {
            list.add(new ItemStack(thiz, 0, i));
        }
    }

    public static String[] getBlockNames() {
        if (blockNamesArray == null) {
            blockNamesArray = blockNames.toArray(new String[0]);
        }
        return blockNamesArray;
    }

    public static ItemStack[] getItemList() {
        if (itemStackArray == null) {
            itemStackArray = itemStackList.toArray(new ItemStack[0]);
        }
        return itemStackArray;
    }

    public static void registerItems() {
        blockArray = blockList.toArray(new Block[blockList.size()]);
        metaArray = metaList.toArray(new int[metaList.size()]);

        for (int i = 0; i < blockArray.length; i++) {
            Block block = blockArray[i];
            int meta = metaArray[i];
            itemStackList.add(new ItemStack(block, 1, meta));
        }
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
        if (id < 0 || id >= itemStackArray.length) id = 0;
        ItemStack stack = itemStackArray[id];
        return stack.getUnlocalizedName();
    }
}
