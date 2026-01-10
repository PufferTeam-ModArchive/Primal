package net.pufferlab.primal.events.ticks;

import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

public class ScheduledTask implements Comparable<ScheduledTask> {

    long timeToSchedule;

    public ScheduledTask(long timeToSchedule) {
        this.timeToSchedule = timeToSchedule;
    }

    @Override
    public int compareTo(@NotNull ScheduledTask o) {
        return Long.compare(this.timeToSchedule, o.timeToSchedule);
    }

    public void run(World world) {
        System.out.println("Ran");
    }
}
