package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.client.gui.GuiGenerator;
import net.pufferlab.primal.tileentities.TileEntityGenerator;

public class InterfaceGenerator extends InterfacePrimal {

    @Override
    public Object getNewContainer(EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityGenerator tef) {
            return new ContainerGenerator(player.inventory, tef);
        }
        return null;
    }

    @Override
    public Object getNewGui(EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityGenerator tef) {
            return new GuiGenerator(player.inventory, tef);
        }
        return null;
    }

    @Override
    public String getIdentifier() {
        return "generator";
    }
}
