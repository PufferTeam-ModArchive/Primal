package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.pufferlab.primal.tileentities.TileEntityGenerator;

public class ContainerGenerator extends Container {

    public TileEntityGenerator tileGen;

    public ContainerGenerator(TileEntityGenerator te) {
        this.tileGen = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
