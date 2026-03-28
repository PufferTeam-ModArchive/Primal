package net.pufferlab.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.pufferlab.primal.world.SchedulerData;
import net.pufferlab.primal.world.Tasks;

public interface IScheduledBlock {

    default void onSchedule(World world, int x, int y, int z, int type, int id) {
        onScheduleTask(world, x, y, z, Tasks.getTask(type));
    };

    default void onScheduleTask(World world, int x, int y, int z, Tasks task) {}

    default void addSchedule(World world, int x, int y, int z, int inTime, Tasks task) {
        addSchedule(world, x, y, z, inTime, task.ordinal());
    }

    default void addSchedule(World world, int x, int y, int z, int inTime, int type) {
        SchedulerData.addScheduledBlockTask(inTime, getBlock(), world, x, y, z, type, 0);
    }

    default void removeSchedule(World world, int x, int y, int z) {
        SchedulerData.removeScheduledTask(world, x, y, z);
    }

    public Block getBlock();
}
