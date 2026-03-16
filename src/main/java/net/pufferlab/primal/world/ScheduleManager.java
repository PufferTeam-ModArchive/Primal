package net.pufferlab.primal.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class ScheduleManager {

    public UpdateTask[] tasks;
    public TIntObjectMap<UpdateTask> tasksMap = new TIntObjectHashMap<>();

    public ScheduleManager(int... tasksID) {
        this.tasks = new UpdateTask[tasksID.length];
        for (int i = 0; i < tasksID.length; i++) {
            tasks[i] = new UpdateTask(tasksID[i]);
        }
        for (UpdateTask task : tasks) {
            this.tasksMap.put(task.id, task);
        }
    }

    public ScheduleManager(UpdateTask... tasks) {
        this.tasks = tasks;
        for (UpdateTask task : tasks) {
            this.tasksMap.put(task.id, task);
        }
    }

    public void readFromNBT(NBTTagCompound tag) {
        for (UpdateTask task : tasks) {
            UpdateTask.readFromNBT(tag, task);
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        for (UpdateTask task : tasks) {
            UpdateTask.writeToNBT(tag, task);
        }
    }

    public boolean hasSentUpdate(int type) {
        return tasksMap.get(type)
            .hasSentUpdate();
    }

    public void addUpdate(int type, World world, int inTime) {
        tasksMap.get(type)
            .addUpdate(world, inTime);
    }

    public void removeUpdate(int type, World world) {
        tasksMap.get(type)
            .removeUpdate(world);
    }

    public void onUpdate(int type, World world) {
        tasksMap.get(type)
            .onUpdate(world);
    }

    public long getNextUpdate(int type) {
        return tasksMap.get(type)
            .getNextUpdate();
    }

    public int getTime(int type) {
        return tasksMap.get(type)
            .getTime();
    }
}
