package net.pufferlab.primal.tileentities;

import net.minecraft.world.World;
import net.pufferlab.primal.world.scheduling.ScheduleManager;
import net.pufferlab.primal.world.scheduling.SchedulerData;
import net.pufferlab.primal.world.scheduling.Task;

public interface IScheduledTile extends ITile {

    default ScheduleManager getManager() {
        return null;
    }

    default void onSchedule(World world, int x, int y, int z, Task type, int id, long taskTime) {
        ScheduleManager manager = getManager();
        if (manager != null) {
            manager.onUpdate(type, world);
        }
        onScheduleTask(type, taskTime);
        mark();
    }

    default void onScheduleTask(Task task, long taskTime) {

    }

    default void addSchedule(World world, int x, int y, int z, int inTime, Task type) {
        ScheduleManager manager = getManager();
        if (manager != null) {
            if (manager.hasSentUpdate(world, x, y, z, type)) return;

            manager.addUpdate(type, world, inTime);
        }
        SchedulerData.addScheduledTileTask(inTime, getBlock(), world, x, y, z, type);
        mark();
    }

    default void addSchedule(World world, int x, int y, int z, long timeCurrent, int inTime, Task type) {
        ScheduleManager manager = getManager();
        if (manager != null) {
            if (manager.hasSentUpdate(world, x, y, z, type)) return;

            manager.addUpdate(type, world, timeCurrent, inTime);
        }
        SchedulerData.addScheduledTileTask(timeCurrent, inTime, getBlock(), world, x, y, z, type);
        mark();
    }

    default void addSchedule(int inTime, Task type) {
        addSchedule(getWorld(), getX(), getY(), getZ(), inTime, type);
    }

    default void addSchedule(long timeSent, int inTime, Task type) {
        addSchedule(getWorld(), getX(), getY(), getZ(), timeSent, inTime, type);
    }

    default void removeSchedule(World world, int x, int y, int z) {
        SchedulerData.removeScheduledTask(world, x, y, z);
        mark();
    }

    default void removeSchedule(World world, int x, int y, int z, Task type) {
        ScheduleManager manager = getManager();
        if (manager != null) {
            if (!manager.hasSentUpdate(world, x, y, z, type)) return;

            manager.removeUpdate(type, world);
        }
        SchedulerData.removeScheduledTask(world, x, y, z, type);
        mark();
    }

    default void removeSchedule() {
        removeSchedule(getWorld(), getX(), getY(), getZ());
    }

    default void removeSchedule(Task type) {
        removeSchedule(getWorld(), getX(), getY(), getZ(), type);
    }

    default void removeAllSchedule() {
        removeSchedule();
    }

    default void moveAllSchedule(World world, int oldX, int oldY, int oldZ, int newX, int newY, int newZ) {
        SchedulerData.moveScheduledTask(getBlock(), world, oldX, oldY, oldZ, newX, newY, newZ);
    }

    default void moveAllSchedule(World world, int oldX, int oldY, int oldZ) {
        moveAllSchedule(world, oldX, oldY, oldZ, getX(), getY(), getZ());
    }
}
