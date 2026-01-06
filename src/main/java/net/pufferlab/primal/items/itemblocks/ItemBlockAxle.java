package net.pufferlab.primal.items.itemblocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Primal;

public class ItemBlockAxle extends ItemBlockMotion {

    public ItemBlockAxle(Block p_i45328_1_) {
        super(p_i45328_1_);
        this.setHasSubtypes(true);
    }

    @Override
    public boolean cancelPlacement(ItemStack stack) {
        if (stack.getItemDamage() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() == 1) {
            return "tile." + Primal.MODID + ".gear";
        }
        return super.getUnlocalizedName(stack);
    }
}
