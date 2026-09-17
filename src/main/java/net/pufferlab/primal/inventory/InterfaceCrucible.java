package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.client.gui.GuiCrucible;
import net.pufferlab.primal.tileentities.TileEntityCrucible;

public class InterfaceCrucible extends InterfacePrimal {

    @Override
    public Object getNewContainer(EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCrucible tef) {
            return new ContainerCrucible(player.inventory, tef);
        }
        return null;
    }

    @Override
    public Object getNewGui(EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCrucible tef) {
            return new GuiCrucible(player.inventory, tef);
        }
        return null;
    }

    @Override
    public String getIdentifier() {
        return "crucible";
    }
}
