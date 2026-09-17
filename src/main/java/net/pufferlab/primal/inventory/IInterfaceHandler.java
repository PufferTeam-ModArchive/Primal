package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IInterfaceHandler {

    Object getNewContainer(EntityPlayer player, World world, int x, int y, int z);

    Object getNewGui(EntityPlayer player, World world, int x, int y, int z);
}
