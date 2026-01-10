package net.pufferlab.primal.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface ITile {

    public World getWorld();

    public int getX();

    public int getY();

    public int getZ();

    public void mark();

    default TileEntity getTile() {
        return getWorld().getTileEntity(getX(), getY(), getZ());
    }
}
