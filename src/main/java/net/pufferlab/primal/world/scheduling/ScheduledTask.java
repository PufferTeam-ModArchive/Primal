package net.pufferlab.primal.world.scheduling;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.IScheduledBlock;
import net.pufferlab.primal.tileentities.IScheduledTile;
import net.pufferlab.primal.world.GlobalTickingData;

import io.netty.buffer.ByteBuf;

public class ScheduledTask implements Comparable<ScheduledTask> {

    long timeCurrent, timeScheduled;
    int inTime;
    int x, y, z, id;
    Block block;
    Task task;
    Task.Type taskType;
    boolean invalid;

    public ScheduledTask(NBTTagCompound tag) {
        readFromNBT(tag);
    }

    public ScheduledTask(ByteBuf buf) {
        readFromBuffer(buf);
    }

    public ScheduledTask(Task.Type type, long currentTime, int inTime) {
        this.taskType = type;
        this.timeCurrent = currentTime;
        this.inTime = inTime;
        this.timeScheduled = currentTime + inTime;
    }

    public ScheduledTask(Task.Type type, Block block, long currentTime, int inTime, int x, int y, int z, Task task,
        int id) {
        this.taskType = type;
        this.block = block;
        this.timeCurrent = currentTime;
        this.inTime = inTime;
        this.timeScheduled = currentTime + inTime;
        this.x = x;
        this.y = y;
        this.z = z;
        this.task = task;
        this.id = id;
    }

    @Override
    public int compareTo(ScheduledTask o) {
        return Long.compare(this.timeScheduled, o.timeScheduled);
    }

    public void updateCoords(int newX, int newY, int newZ) {
        this.x = newX;
        this.y = newY;
        this.z = newZ;
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setByte("type", Task.Type.getID(this.taskType));
        tag.setInteger("task", Task.getID(this.task));

        tag.setLong("time", timeCurrent);
        tag.setInteger("in", inTime);

        tag.setInteger("blockID", Block.getIdFromBlock(block));
        tag.setInteger("x", x);
        tag.setInteger("y", y);
        tag.setInteger("z", z);
        tag.setInteger("id", id);
    }

    public void writeToBuffer(ByteBuf buf) {
        buf.writeByte(Task.Type.getID(this.taskType));
        buf.writeInt(Task.getID(this.task));

        buf.writeLong(timeCurrent);
        buf.writeInt(inTime);

        buf.writeInt(Block.getIdFromBlock(block));
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(id);
    }

    public void readFromNBT(NBTTagCompound tag) {
        taskType = Task.Type.getTask(tag.getByte("type"));
        task = Task.getTask(tag.getInteger("task"));

        timeCurrent = tag.getLong("time");
        inTime = tag.getInteger("in");
        timeScheduled = timeCurrent + inTime;

        block = Block.getBlockById(tag.getInteger("blockID"));
        x = tag.getInteger("x");
        y = tag.getInteger("y");
        z = tag.getInteger("z");
        id = tag.getInteger("id");
    }

    public void readFromBuffer(ByteBuf buf) {
        taskType = Task.Type.getTask(buf.readByte());
        task = Task.getTask(buf.readInt());

        timeCurrent = buf.readLong();
        inTime = buf.readInt();
        timeScheduled = timeCurrent + inTime;

        block = Block.getBlockById(buf.readInt());
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        id = buf.readInt();
    }

    public Class<?> getTileClass() {
        return null;
    }

    public boolean equals(Block block, int x, int y, int z) {
        if (this.x == x && this.y == y && this.z == z && this.block == block) {
            return true;
        }
        return false;
    }

    public boolean equals(int x, int y, int z) {
        if (this.x == x && this.y == y && this.z == z) {
            return true;
        }
        return false;
    }

    public boolean equals(Block block, int x, int y, int z, Task task) {
        if (this.x == x && this.y == y && this.z == z && this.task == task && this.block == block) {
            return true;
        }
        return false;
    }

    public boolean equals(int x, int y, int z, Task task) {
        if (this.x == x && this.y == y && this.z == z && this.task == task) {
            return true;
        }
        return false;
    }

    public boolean equals(Task task) {
        if (this.task == task) {
            return true;
        }
        return false;
    }

    public boolean run(World world) {
        if (this.invalid()) return false;
        switch (this.taskType) {
            case simpleTask: {
                Primal.LOG.info("ScheduledTask Ran");
                return true;
            }
            case blockTask: {
                if (!isLoaded(world)) {
                    SchedulerData.addWaitingTask(this, world);
                    return false;
                }
                Block block = world.getBlock(this.x, this.y, this.z);
                if (this.block == block) {
                    if (block instanceof IScheduledBlock block2) {
                        block2.onSchedule(world, this.x, this.y, this.z, this.task, this.id, this.timeScheduled);
                        return true;
                    }
                }
            }
            case tileTask: {
                if (!isLoaded(world)) {
                    SchedulerData.addWaitingTask(this, world);
                    return false;
                }
                Block block = world.getBlock(this.x, this.y, this.z);
                if (this.block == block) {
                    TileEntity te = world.getTileEntity(this.x, this.y, this.z);
                    if (te instanceof IScheduledTile te2) {
                        te2.onSchedule(world, this.x, this.y, this.z, this.task, this.id, this.timeScheduled);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean invalid() {
        return this.invalid;
    }

    public void invalidate() {
        this.invalid = true;
    }

    public boolean isLoaded(World world) {
        if (!world.blockExists(this.x, this.y, this.z)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ScheduledTask{" + "inTime="
            + (timeScheduled - GlobalTickingData.getTickTime())
            + ", task="
            + task.toString()
            + ", x="
            + x
            + ", y="
            + y
            + ", z="
            + z
            + ", id="
            + id
            + '}';
    }
}
