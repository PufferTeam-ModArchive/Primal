package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.pufferlab.primal.tileentities.TileEntityGenerator;

public class ContainerGenerator extends ContainerPrimal {

    public TileEntityGenerator tileGen;

    public ContainerGenerator(InventoryPlayer playerInventory, TileEntityGenerator te) {
        this.tileGen = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
