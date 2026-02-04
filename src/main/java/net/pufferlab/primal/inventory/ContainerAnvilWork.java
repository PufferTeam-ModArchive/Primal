package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

public class ContainerAnvilWork extends Container {

    public TileEntityAnvil tileAnvil;

    public ContainerAnvilWork(TileEntityAnvil te) {
        this.tileAnvil = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileAnvil.isUseableByPlayer(player);
    }
}
