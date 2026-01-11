package net.pufferlab.primal.tileentities;

import net.minecraft.world.World;
import net.pufferlab.primal.events.ticks.SchedulerData;

public interface IScheduledTile extends ITile {

    default void onSchedule(World world, int x, int y, int z, int type, int id) {};

    default void addSchedule(World world, int x, int y, int z, int inTime, int type) {
        SchedulerData.addScheduledTileTask(inTime, world, x, y, z, type, 0);
    }

    default void addSchedule(int inTime, int type) {
        addSchedule(getWorld(), getX(), getY(), getZ(), inTime, type);
    }

    default void removeSchedule(World world, int x, int y, int z) {
        SchedulerData.removeScheduledTask(world, x, y, z);
    }

    default void removeSchedule(World world, int x, int y, int z, int type) {
        SchedulerData.removeScheduledTask(world, x, y, z, type);
    }

    default void removeSchedule() {
        removeSchedule(getWorld(), getX(), getY(), getZ());
    }

    default void removeSchedule(int type) {
        removeSchedule(getWorld(), getX(), getY(), getZ(), type);
    }

    default void removeAllSchedule() {
        removeSchedule();
    }
}
