package net.pufferlab.primal.blocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.pufferlab.primal.world.ScheduleManager;
import net.pufferlab.primal.world.SchedulerData;
import net.pufferlab.primal.world.Tasks;

public interface IScheduledBlock {

    default ScheduleManager getManager() {
        return null;
    }

    default void onSchedule(World world, int x, int y, int z, Tasks type, int id) {
        onScheduleTask(world, x, y, z, type);
    };

    default void onScheduleTask(World world, int x, int y, int z, Tasks task) {}

    default void addSchedule(World world, int x, int y, int z, int inTime, Tasks type) {
        ScheduleManager manager = getManager();
        if (manager != null) {
            if (manager.hasSentUpdate(world, x, y, z, type)) return;
        }
        SchedulerData.addScheduledBlockTask(inTime, getBlock(), world, x, y, z, type);
    }

    default void removeSchedule(World world, int x, int y, int z) {
        SchedulerData.removeScheduledTask(world, x, y, z);
    }

    public Block getBlock();
}
