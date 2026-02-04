package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPlan extends Slot {

    public int recipeID;

    public SlotPlan(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, int recipeID) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.recipeID = recipeID;
    }

    @Override
    public boolean canTakeStack(EntityPlayer p_82869_1_) {
        return false;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

}
