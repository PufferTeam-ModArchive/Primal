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

    public static PriorityQueue<ScheduledTask> getTasks(World world) {
        SchedulerData scheduler = get(world);
        return scheduler.queue;
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

    public static void addScheduledTask(int inTime, World world) {
        long in = GlobalTickingData.getTickTime(world) + inTime;
        SchedulerData scheduler = get(world);

        scheduler.queue.add(new ScheduledTask(ScheduledTask.simpleTask, in));
        scheduler.markDirty();
    }

    public static void addScheduledTask(byte task, int inTime, World world, int x, int y, int z, int type, int id) {
        long in = GlobalTickingData.getTickTime(world) + inTime;
        SchedulerData scheduler = get(world);

        scheduler.queue.add(new ScheduledTask(task, in, x, y, z, type, id));
        scheduler.markDirty();
    }

    public static void addScheduledBlockTask(int inTime, World world, int x, int y, int z, int type, int id) {
        addScheduledTask(ScheduledTask.blockTask, inTime, world, x, y, z, type, id);
    }

    public static void addScheduledTileTask(int inTime, World world, int x, int y, int z, int type, int id) {
        addScheduledTask(ScheduledTask.tileTask, inTime, world, x, y, z, type, id);
    }

    public static void removeScheduledTask(World world, int x, int y, int z, int type) {
        SchedulerData scheduler = get(world);

        scheduler.queue.removeIf(task -> task.equals(x, y, z, type));
        scheduler.markDirty();
    }

    public static void removeScheduledTask(World world, int x, int y, int z) {
        SchedulerData scheduler = get(world);

        scheduler.queue.removeIf(task -> task.equals(x, y, z));
        scheduler.markDirty();
    }

    public static void tickTasks(long currentTick, World world) {
        SchedulerData scheduler = get(world);

        while (!scheduler.queue.isEmpty() && scheduler.queue.peek().timeScheduled <= currentTick) {
            ScheduledTask task = scheduler.queue.poll();

            boolean success = task.run(world);
            if (success) {
                scheduler.markDirty();
            }
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
