package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.client.gui.GuiLargeVessel;
import net.pufferlab.primal.tileentities.TileEntityLargeVessel;

public class InterfaceLargeVessel extends InterfacePrimal {

    @Override
    public Object getNewContainer(EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityLargeVessel tef) {
            return new ContainerLargeVessel(player.inventory, tef);
        }
        return null;
    }

    @Override
    public Object getNewGui(EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityLargeVessel tef) {
            return new GuiLargeVessel(player.inventory, tef);
        }
        return null;
    }

    @Override
    public String getIdentifier() {
        return "large_vessel";
    }
}
