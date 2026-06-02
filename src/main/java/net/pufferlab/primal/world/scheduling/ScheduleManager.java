package net.pufferlab.primal.world.scheduling;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class ScheduleManager {

    public TaskInfo[] tasks;
    public TIntObjectMap<TaskInfo> tasksMap = new TIntObjectHashMap<>();

    public ScheduleManager() {}

    public ScheduleManager(Task... tasksID) {
        this.tasks = new TaskInfo[tasksID.length];
        for (int i = 0; i < tasksID.length; i++) {
            tasks[i] = new TaskInfo(tasksID[i]);
        }
        for (TaskInfo task : tasks) {
            this.tasksMap.put(task.id, task);
        }
    }

    public ScheduleManager(TaskInfo... tasks) {
        this.tasks = tasks;
        for (TaskInfo task : tasks) {
            this.tasksMap.put(task.id, task);
        }
    }

    public TaskInfo get(Task task) {
        return this.tasksMap.get(Task.getID(task));
    }

    public void readFromNBT(NBTTagCompound tag) {
        for (TaskInfo task : tasks) {
            if (!task.serialize) continue;
            TaskInfo.readFromNBT(tag, task);
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        for (TaskInfo task : tasks) {
            if (!task.serialize) continue;
            TaskInfo.writeToNBT(tag, task);
        }
    }

    public boolean hasSchedule(Task task) {
        return hasSchedule(Task.getID(task));
    }

    public boolean hasSchedule(int type) {
        for (TaskInfo task : tasks) {
            if (task.id == type) return true;
        }
        return false;
    }

    public boolean hasSentUpdate(World world, int x, int y, int z, Task type) {
        TaskInfo task = get(type);
        if (task == null) {
            return SchedulerData.hasScheduledTask(world, x, y, z, type);
        }
        return task.hasSentUpdate();
    }

    public void addUpdate(Task type, World world, int inTime) {
        TaskInfo task = get(type);
        if (task != null) {
            task.addUpdate(world, inTime);
        }
    }

    public void addUpdate(Task type, World world, long currentTime, int inTime) {
        TaskInfo task = get(type);
        if (task != null) {
            task.addUpdate(world, currentTime, inTime);
        }
    }

    public void removeUpdate(Task type, World world) {
        TaskInfo task = get(type);
        if (task != null) {
            task.removeUpdate(world);
        }
    }

    public void onUpdate(Task type, World world) {
        TaskInfo task = get(type);
        if (task != null) {
            task.onUpdate(world);
        }
    }

    public long getTimeScheduled(Task type) {
        return get(type).getTimeScheduled();
    }

    public long getTimeSent(Task type) {
        return get(type).getTimeSent();
    }

    public int getTime(Task type) {
        return get(type).getTime();
    }
}
