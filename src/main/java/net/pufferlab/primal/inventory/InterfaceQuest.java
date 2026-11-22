package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.pufferlab.primal.client.gui.GuiQuest;

public class InterfaceQuest extends InterfacePrimal {

    public InterfaceQuest() {}

    @Override
    public Object getNewContainer(EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerQuest(player.inventory);
    }

    @Override
    public Object getNewGui(EntityPlayer player, World world, int x, int y, int z) {
        return new GuiQuest(player.inventory);
    }

    @Override
    public String getIdentifier() {
        return "quest";
    }
}
