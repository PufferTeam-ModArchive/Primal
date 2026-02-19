package net.pufferlab.primal.inventory;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.tileentities.TileEntityCrucible;

public class SlotCrucible extends Slot {

    public TileEntityCrucible tileCrucible;

    public SlotCrucible(TileEntityCrucible te, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(te, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.tileCrucible = te;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (Utils.containsOreDict(stack, "itemContainer")) {
            return false;
        }
        return true;
    }

}
