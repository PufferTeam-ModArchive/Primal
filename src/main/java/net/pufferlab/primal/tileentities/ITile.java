package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface ITile {

    public World getWorld();

    public int getX();

    public int getY();

    public int getZ();

    public void mark();

    public int getWorldID();

    default TileEntity getTile() {
        return getWorld().getTileEntity(getX(), getY(), getZ());
    }

    default int getMeta() {
        return getWorld().getBlockMetadata(getX(), getY(), getZ());
    }

    public void updateTEState();

    public void updateTELight();

    default Block getBlock() {
        return getTile().getBlockType();
    }
}
