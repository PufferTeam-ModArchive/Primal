package net.pufferlab.primal.events.ticks;

import java.util.PriorityQueue;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.pufferlab.primal.Primal;

public class SchedulerData extends WorldSavedData {

    private static String name = Primal.MODID + "SchedulerData";

    public PriorityQueue<ScheduledTask> queue = new PriorityQueue<>();

    public SchedulerData(String p_i2141_1_) {
        super(name);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {

        NBTTagList list = new NBTTagList();

        for (ScheduledTask task : queue) {
            NBTTagCompound tag = new NBTTagCompound();
            task.writeToNBT(tag);
            list.appendTag(tag);
        }

        nbt.setTag("ScheduledTasks", list);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        queue.clear();

        NBTTagList list = nbt.getTagList("ScheduledTasks", 10);

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            queue.add(new ScheduledTask(tag));
        }
    }

    public static void scheduleNewTask(int inTime, World world) {
        long in = GlobalTickingData.getTickTime(world) + inTime;
        SchedulerData scheduler = get(world);

        scheduler.queue.add(new ScheduledTask(in));
    }

    public static void tickTasks(long currentTick, World world) {
        SchedulerData scheduler = get(world);

        while (!scheduler.queue.isEmpty() && scheduler.queue.peek().timeScheduled <= currentTick) {
            ScheduledTask task = scheduler.queue.poll();

            task.run(world);
        }
    }

    public static SchedulerData get(World world) {
        SchedulerData data = (SchedulerData) world.loadItemData(SchedulerData.class, name);

        if (data == null) {
            data = new SchedulerData(name);
            world.setItemData(name, data);
        }

        return data;
    }
}
