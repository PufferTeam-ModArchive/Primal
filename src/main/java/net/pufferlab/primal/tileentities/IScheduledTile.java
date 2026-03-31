package net.pufferlab.primal.tileentities;

import net.minecraft.world.World;
import net.pufferlab.primal.world.ScheduleManager;
import net.pufferlab.primal.world.SchedulerData;
import net.pufferlab.primal.world.Tasks;

public interface IScheduledTile extends ITile {

    default ScheduleManager getManager() {
        return null;
    }

    default void onSchedule(World world, int x, int y, int z, int type, int id) {
        ScheduleManager manager = getManager();
        if (manager != null) {
            manager.onUpdate(type, world);
        }
        onScheduleTask(Tasks.getTask(type));
        mark();
    }

    default void onScheduleTask(Tasks task) {

    }

    default void addSchedule(World world, int x, int y, int z, int inTime, int type) {
        ScheduleManager manager = getManager();
        if (manager != null) {
            if (manager.hasSentUpdate(type)) return;

            manager.addUpdate(type, world, inTime);
        }
        SchedulerData.addScheduledTileTask(inTime, getBlock(), world, x, y, z, type);
        mark();
    }

    default void addSchedule(int inTime, Tasks type) {
        addSchedule(inTime, type.ordinal());
    }

    default void addSchedule(int inTime, int type) {
        addSchedule(getWorld(), getX(), getY(), getZ(), inTime, type);
    }

    default void removeSchedule(World world, int x, int y, int z) {
        SchedulerData.removeScheduledTask(world, x, y, z);
        mark();
    }

    default void removeSchedule(World world, int x, int y, int z, int type) {
        ScheduleManager manager = getManager();
        if (manager != null) {
            if (!manager.hasSentUpdate(type)) return;

            manager.removeUpdate(type, world);
        }
        SchedulerData.removeScheduledTask(world, x, y, z, type);
        mark();
    }

    default void removeSchedule() {
        removeSchedule(getWorld(), getX(), getY(), getZ());
    }

    default void removeSchedule(Tasks type) {
        removeSchedule(type.ordinal());
    }

    default void removeSchedule(int type) {
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
