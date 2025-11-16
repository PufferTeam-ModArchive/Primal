package net.pufferlab.primitivelife.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class SlotLocked extends Slot {

    InventoryPlayer player;

    public SlotLocked(InventoryPlayer p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.player = p_i1824_1_;
    }

    @Override
    public boolean canTakeStack(EntityPlayer p_82869_1_) {
        if (player.currentItem == getSlotIndex()) {
            return false;
        }
        return true;
    }
}
