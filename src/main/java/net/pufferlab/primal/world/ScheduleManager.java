package net.pufferlab.primal.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class ScheduleManager {

    public UpdateTask[] tasks;
    public TIntObjectMap<UpdateTask> tasksMap = new TIntObjectHashMap<>();

    public ScheduleManager(Tasks... tasksID) {
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
            if (!task.serialize) continue;
            UpdateTask.readFromNBT(tag, task);
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        for (UpdateTask task : tasks) {
            if (!task.serialize) continue;
            UpdateTask.writeToNBT(tag, task);
        }
    }

    public boolean hasSentUpdate(World world, int x, int y, int z, int type) {
        UpdateTask task = tasksMap.get(type);
        if (task == null) {
            return SchedulerData.hasScheduledTask(world, x, y, z, type);
        }
        return task.hasSentUpdate();
    }

    public void addUpdate(int type, World world, int inTime) {
        UpdateTask task = tasksMap.get(type);
        if (task != null) {
            task.addUpdate(world, inTime);
        }
    }

    public void removeUpdate(int type, World world) {
        UpdateTask task = tasksMap.get(type);
        if (task != null) {
            task.removeUpdate(world);
        }
    }

    public void onUpdate(int type, World world) {
        UpdateTask task = tasksMap.get(type);
        if (task != null) {
            task.onUpdate(world);
        }
    }

    public long getNextUpdate(int type) {
        return tasksMap.get(type)
            .getNextUpdate();
    }

    public long getNextUpdate(Tasks task) {
        return getNextUpdate(task.ordinal());
    }

    public int getTime(int type) {
        return tasksMap.get(type)
            .getTime();
    }

    public int getTime(Tasks task) {
        return getTime(task.ordinal());
    }
}
