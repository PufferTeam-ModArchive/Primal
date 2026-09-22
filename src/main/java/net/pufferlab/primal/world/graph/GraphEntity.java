package net.pufferlab.primal.world.graph;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

import io.netty.buffer.ByteBuf;

public class GraphEntity implements IGraphEntity {

    private boolean invalid;
    public int x;
    public int y;
    public int z;
    public int id;
    public Block block;

    public void initialize(Block block, int id, int x, int y, int z) {
        this.block = block;
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int id() {
        return this.id;
    }

    @Override
    public int x() {
        return this.x;
    }

    @Override
    public int y() {
        return this.y;
    }

    @Override
    public int z() {
        return this.z;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("id", id);
        nbt.setInteger("x", x);
        nbt.setInteger("y", y);
        nbt.setInteger("z", z);
    }

    public void writeToBuffer(ByteBuf buf) {
        buf.writeInt(id);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.id = nbt.getInteger("id");
        this.x = nbt.getInteger("x");
        this.y = nbt.getInteger("y");
        this.z = nbt.getInteger("z");
    }

    public void readFromBuffer(ByteBuf buf) {
        this.id = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    public void invalidate() {
        this.invalid = true;
    }

    public boolean invalid() {
        return this.invalid;
    }

    public Block block() {
        return this.block;
    }
}
