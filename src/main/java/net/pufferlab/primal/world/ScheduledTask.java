package net.pufferlab.primal.world;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.IScheduledBlock;
import net.pufferlab.primal.tileentities.IScheduledTile;

public class ScheduledTask implements Comparable<ScheduledTask> {

    public static final byte simpleTask = 0;
    public static final byte blockTask = 1;
    public static final byte tileTask = 2;

    long timeScheduled;
    byte taskType;
    int x, y, z, type, id;

    public ScheduledTask(NBTTagCompound tag) {
        readFromNBT(tag);
    }

    public ScheduledTask(byte taskType, long timeToSchedule) {
        this.taskType = taskType;
        this.timeScheduled = timeToSchedule;
    }

    public ScheduledTask(byte taskType, long timeScheduled, int x, int y, int z, int type, int id) {
        this.taskType = taskType;
        this.timeScheduled = timeScheduled;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.id = id;
    }

    @Override
    public int compareTo(ScheduledTask o) {
        return Long.compare(this.timeScheduled, o.timeScheduled);
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setByte("task", this.taskType);
        tag.setLong("time", timeScheduled);
        tag.setInteger("x", x);
        tag.setInteger("y", y);
        tag.setInteger("z", z);
        tag.setInteger("type", type);
        tag.setInteger("id", id);
    }

    public void readFromNBT(NBTTagCompound tag) {
        taskType = tag.getByte("task");
        timeScheduled = tag.getLong("time");
        x = tag.getInteger("x");
        y = tag.getInteger("y");
        z = tag.getInteger("z");
        type = tag.getInteger("type");
        id = tag.getInteger("id");
    }

    public boolean equals(int x, int y, int z) {
        if (this.x == x && this.y == y && this.z == z) {
            return true;
        }
        return false;
    }

    public boolean equals(int x, int y, int z, int type) {
        if (this.x == x && this.y == y && this.z == z && this.type == type) {
            return true;
        }
        return false;
    }

    public boolean equals(int type) {
        if (this.type == type) {
            return true;
        }
        return false;
    }

    public boolean run(World world) {
        switch (this.taskType) {
            case simpleTask: {
                Primal.LOG.info("ScheduledTask Ran");
                return true;
            }
            case blockTask: {
                Block block = world.getBlock(this.x, this.y, this.z);
                if (block instanceof IScheduledBlock block2) {
                    block2.onSchedule(world, this.x, this.y, this.z, this.type, this.id);
                    return true;
                }
            }
            case tileTask: {
                Block block = world.getBlock(this.x, this.y, this.z);
                TileEntity te = world.getTileEntity(this.x, this.y, this.z);
                if (te instanceof IScheduledTile te2) {
                    te2.onSchedule(world, this.x, this.y, this.z, this.type, this.id);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ScheduledTask{" + "inTime="
            + (timeScheduled - GlobalTickingData.getTickTime())
            + ", taskType="
            + taskType
            + ", x="
            + x
            + ", y="
            + y
            + ", z="
            + z
            + ", type="
            + type
            + ", id="
            + id
            + '}';
    }
}
