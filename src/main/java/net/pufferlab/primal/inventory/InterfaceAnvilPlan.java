package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.client.gui.GuiAnvilPlan;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

public class InterfaceAnvilPlan extends InterfacePrimal {

    @Override
    public Object getNewContainer(EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityAnvil tef) {
            return new ContainerAnvilPlan(player.inventory, tef);
        }
        return null;
    }

    @Override
    public Object getNewGui(EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityAnvil tef) {
            return new GuiAnvilPlan(player.inventory, tef);
        }
        return null;
    }

    @Override
    public String getIdentifier() {
        return "anvil_plan";
    }
}
