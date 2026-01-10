package net.pufferlab.primal.events.ticks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

public class ScheduledTask implements Comparable<ScheduledTask> {

    long timeScheduled;
    byte taskType;
    int x, y, z, type, id;

    public ScheduledTask(NBTTagCompound tag) {
        readFromNBT(tag);
    }

    public ScheduledTask(long timeToSchedule) {
        this.timeScheduled = timeToSchedule;
        taskType = 0;
    }

    public ScheduledTask(long timeScheduled, int x, int y, int z, int type, int id) {
        this.timeScheduled = timeScheduled;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.id = id;
        taskType = 1;
    }

    @Override
    public int compareTo(@NotNull ScheduledTask o) {
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

    public void run(World world) {
        if (this.taskType == 0) {
            System.out.println("Ran task");
        }
    }
}
