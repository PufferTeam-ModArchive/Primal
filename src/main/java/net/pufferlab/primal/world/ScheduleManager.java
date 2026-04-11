package net.pufferlab.primal.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class ScheduleManager {

    public TaskInfo[] tasks;
    public TIntObjectMap<TaskInfo> tasksMap = new TIntObjectHashMap<>();

    public ScheduleManager(Tasks... tasksID) {
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

    public boolean hasSchedule(Tasks task) {
        return hasSchedule(Tasks.getID(task));
    }

    public boolean hasSchedule(int type) {
        for (TaskInfo task : tasks) {
            if (task.id == type) return true;
        }
        return false;
    }

    public boolean hasSentUpdate(World world, int x, int y, int z, int type) {
        TaskInfo task = tasksMap.get(type);
        if (task == null) {
            return SchedulerData.hasScheduledTask(world, x, y, z, type);
        }
        return task.hasSentUpdate();
    }

    public void addUpdate(int type, World world, int inTime) {
        TaskInfo task = tasksMap.get(type);
        if (task != null) {
            task.addUpdate(world, inTime);
        }
    }

    public void removeUpdate(int type, World world) {
        TaskInfo task = tasksMap.get(type);
        if (task != null) {
            task.removeUpdate(world);
        }
    }

    public void onUpdate(int type, World world) {
        TaskInfo task = tasksMap.get(type);
        if (task != null) {
            task.onUpdate(world);
        }
    }

    public long getTimeScheduled(int type) {
        return tasksMap.get(type)
            .getTimeScheduled();
    }

    public long getTimeScheduled(Tasks task) {
        return getTimeScheduled(Tasks.getID(task));
    }

    public long getTimeSent(int type) {
        return tasksMap.get(type)
            .getTimeSent();
    }

    public long getTimeSent(Tasks task) {
        return getTimeSent(Tasks.getID(task));
    }

    public int getTime(int type) {
        return tasksMap.get(type)
            .getTime();
    }

    public int getTime(Tasks task) {
        return getTime(Tasks.getID(task));
    }
}
