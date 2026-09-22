package net.pufferlab.primal.world.graph;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.utils.IPositioned;

import io.netty.buffer.ByteBuf;

public interface IGraphEntity extends IPositioned {

    int id();

    Block block();

    boolean invalid();

    void invalidate();

    void initialize(Block block, int id, int x, int y, int z);

    void writeToNBT(NBTTagCompound compound);

    void writeToBuffer(ByteBuf buf);

    void readFromNBT(NBTTagCompound compound);

    void readFromBuffer(ByteBuf buf);
}
