package net.pufferlab.primal.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;

public class ItemMetaHeatable extends ItemMeta {

    public ItemMetaHeatable(String[] materials, String type) {
        super(materials, type);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int p_77663_4_, boolean p_77663_5_) {
        NBTTagCompound tag = stack.getTagCompound();
        Utils.setTimePassedToNBT(tag, Utils.getTimePassedFromNBT(tag) + 1);
        if (Utils.getTimePassedFromNBT(tag) > 3) {
            Utils.setTimePassedToNBT(tag, 0);
            int temperature = Utils.getTemperatureFromNBT(tag) - 1;
            if (temperature < 0) {
                temperature = 0;
            }
            Utils.setTemperatureToNBT(tag, temperature);
        }
    }
}
