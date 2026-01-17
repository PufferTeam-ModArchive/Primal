package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.BlockMeta;

public class ItemBlockMeta extends ItemBlock {

    private String[] elements;
    private String[] elementsBlacklist;
    private String name;
    private BlockMeta blockC;
    private boolean hasSuffix;

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
}
