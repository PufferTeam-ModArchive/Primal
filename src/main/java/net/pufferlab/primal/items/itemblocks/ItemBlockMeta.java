package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.BlockMeta;
import net.pufferlab.primal.items.IMetaItem;

public class ItemBlockMeta extends ItemBlock implements IMetaItem {

    public String[] elements;
    public String[] elementsBlacklist;
    public String name;
    public BlockMeta blockC;
    public boolean hasSuffix;

    public ItemBlockMeta(Block block) {
        super(block);

        blockC = (BlockMeta) field_150939_a;

        elements = blockC.getElements();
        elementsBlacklist = blockC.getElementsBlacklist();
        name = blockC.getElementName();
        hasSuffix = blockC.hasSuffix();
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() >= elements.length
            || Utils.contains(elementsBlacklist, elements[stack.getItemDamage()])) {
            return "tile." + Primal.MODID + ".error";
        }
        if (this.hasSuffix) {
            return "tile." + Primal.MODID + "." + elements[stack.getItemDamage()] + "_" + name;
        } else {
            return "tile." + Primal.MODID + "." + elements[stack.getItemDamage()];
        }
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String[] getElements() {
        return elements;
    }

    @Override
    public String[] getElementsBlacklist() {
        return elementsBlacklist;
    }

    @Override
    public String getElementName() {
        return name;
    }

    @Override
    public boolean hasSuffix() {
        return hasSuffix;
    }

    @Override
    public Item getItemObject() {
        return this;
    }
}
