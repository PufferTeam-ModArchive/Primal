package net.pufferlab.primal.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemExample extends Item {

    public ItemExample() { // <---- This is a constructor, its a way to create an instance with some base characters
        this.setTextureName("modid:texture"); // <-- This links to a function in "Item" that puts the texture
    }

    // This "onUpdate" function gets run every tick, it is defined in "Item", in this case I can override it for this
    // item
    // and run some code
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int p_77663_4_, boolean p_77663_5_) {}

}
