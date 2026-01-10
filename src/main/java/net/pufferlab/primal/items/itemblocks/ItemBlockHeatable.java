package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pufferlab.primal.items.IHeatableItem;

public class ItemBlockHeatable extends ItemBlock implements IHeatableItem {

    public ItemBlockHeatable(Block p_i45328_1_) {
        super(p_i45328_1_);

        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int p_77663_4_, boolean p_77663_5_) {
        super.onUpdate(stack, worldIn, entityIn, p_77663_4_, p_77663_5_);

        onUpdateHeat(stack, worldIn);
    }
}
