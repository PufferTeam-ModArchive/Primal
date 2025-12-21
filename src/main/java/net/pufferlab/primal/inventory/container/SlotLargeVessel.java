package net.pufferlab.primal.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.TileEntityLargeVessel;

public class SlotLargeVessel extends Slot {

    public TileEntityLargeVessel tileLargeVessel;
    public boolean isOpen;

    public SlotLargeVessel(TileEntityLargeVessel te, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(te, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.tileLargeVessel = te;
        this.isOpen = te.isOpen;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (!this.isOpen) {
            return false;
        }
        if (Utils.containsOreDict(stack, "itemContainer")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canTakeStack(EntityPlayer p_82869_1_) {
        if (!this.isOpen) {
            return false;
        }
        return true;
    }

}
