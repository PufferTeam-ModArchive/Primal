package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerQuest extends ContainerPrimal {

    public ContainerQuest(InventoryPlayer playerInventory) {}

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
