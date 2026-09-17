package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

public class ContainerAnvilWork extends ContainerPrimal {

    public TileEntityAnvil tileAnvil;

    public ContainerAnvilWork(InventoryPlayer playerInv, TileEntityAnvil te) {
        this.tileAnvil = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileAnvil.isUseableByPlayer(player);
    }
}
