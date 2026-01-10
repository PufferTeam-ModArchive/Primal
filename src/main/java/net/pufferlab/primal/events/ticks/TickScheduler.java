package net.pufferlab.primal.events.ticks;

import java.util.PriorityQueue;

import net.minecraft.world.World;

public class TickScheduler {

    public static final PriorityQueue<ScheduledTask> queue = new PriorityQueue<>();

    public static void scheduleNewTask(int inTime, World world) {
        long in = WorldTickingData.getTickTime(world) + inTime;
        queue.add(new ScheduledTask(in));
    }

    public static void tickTasks(long currentTick, World world) {
        while (!queue.isEmpty() && queue.peek().timeToSchedule <= currentTick) {
            ScheduledTask task = queue.poll();

            task.run(world);
        }
    }
}
