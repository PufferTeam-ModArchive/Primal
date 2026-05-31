package net.pufferlab.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.pufferlab.primal.world.SchedulerData;
import net.pufferlab.primal.world.Tasks;

public interface IScheduledBlock {

    default void onSchedule(World world, int x, int y, int z, Tasks type, int id, long taskTime) {
        onScheduleTask(world, x, y, z, type, taskTime);
    };

    default void onScheduleTask(World world, int x, int y, int z, Tasks task, long taskTime) {}

    default void addSchedule(World world, int x, int y, int z, int inTime, Tasks type) {
        SchedulerData.addScheduledBlockTask(inTime, getBlock(), world, x, y, z, type);
    }

    default void addSchedule(World world, int x, int y, int z, long currentTime, int inTime, Tasks type) {
        SchedulerData.addScheduledBlockTask(currentTime, inTime, getBlock(), world, x, y, z, type);
    }

    default boolean hasSchedule(World world, int x, int y, int z, Tasks type) {
        return SchedulerData.hasScheduledTask(world, x, y, z, type);
    }

    default void removeSchedule(World world, int x, int y, int z) {
        SchedulerData.removeScheduledTask(world, x, y, z);
    }

    public Block getBlock();
}
