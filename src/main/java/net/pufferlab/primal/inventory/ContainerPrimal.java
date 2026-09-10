package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.pufferlab.primal.utils.IIdentifiable;

public abstract class ContainerPrimal extends Container implements IIdentifiable {

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return false;
    }

    @Override
    public String getIdentifierType() {
        return "container";
    }
}
