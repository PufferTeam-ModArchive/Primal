package net.pufferlab.primal.tileentities;

import net.minecraft.world.World;

public interface ITile {

    public World getWorld();

    public int getX();

    public int getY();

    public int getZ();
}
