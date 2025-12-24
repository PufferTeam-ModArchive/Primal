package net.pufferlab.primal.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;

public class ItemBlockCrucible extends ItemBlock {

    public ItemBlockCrucible(Block p_i45328_1_) {
        super(p_i45328_1_);

        this.setMaxStackSize(1);
    }

    int timeUpdate;

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int p_77663_4_, boolean p_77663_5_) {
        timeUpdate++;
        if (timeUpdate > 60) {
            timeUpdate = 0;
            int temperature = Utils.getTemperatureFromNBT(stack.getTagCompound()) - 20;
            if (temperature < 0) {
                temperature = 0;
            }
            Utils.setTemperatureToNBT(stack.getTagCompound(), temperature);
        }
    }

}
