package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

public class ContainerAnvilWork extends ContainerPrimal {

    public TileEntityAnvil tileAnvil;

    public ContainerAnvilWork() {}

    public ContainerAnvilWork(TileEntityAnvil te) {
        this.tileAnvil = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileAnvil.isUseableByPlayer(player);
    }
}
