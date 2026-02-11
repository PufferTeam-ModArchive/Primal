package net.pufferlab.primal.items.itemblocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.items.IMetaItem;

public class ItemBlockAxle extends ItemBlockPrimal implements IMetaItem {

    public static final String[] types = new String[] { "axle", "gear", "bracket" };

    public ItemBlockAxle(Block p_i45328_1_) {
        super(p_i45328_1_);
        this.setHasSubtypes(true);
    }

    @Override
    public boolean cancelPlacement(ItemStack stack) {
        if (stack.getItemDamage() == 1 || stack.getItemDamage() == 2) {
            return true;
        }
        return false;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() == 1) {
            return "tile." + Primal.MODID + ".gear";
        }
        if (stack.getItemDamage() == 2) {
            return "tile." + Primal.MODID + ".bracket";
        }
        return super.getUnlocalizedName(stack);
    }

    @Override
    public String[] getElements() {
        return types;
    }

    @Override
    public String getElementName() {
        return "axle";
    }

    @Override
    public boolean hasSuffix() {
        return false;
    }

    @Override
    public Item getItemObject() {
        return this;
    }
}
