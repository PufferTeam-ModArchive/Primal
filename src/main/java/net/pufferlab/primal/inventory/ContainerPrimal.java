package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerPrimal extends Container {

    private int guiID;

    public void setGuiId(int guiID) {
        this.guiID = guiID;
    }

    public int getGuiId() {
        return this.guiID;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return false;
    }
}
