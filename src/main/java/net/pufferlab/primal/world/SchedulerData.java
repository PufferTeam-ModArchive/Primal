package net.pufferlab.primal.world;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

public class SchedulerData extends WorldSavedData {

    private static String name = Primal.MODID + "SchedulerData";

    public PriorityQueue<ScheduledTask> queue = new PriorityQueue<>();
    public PriorityQueue<ScheduledTask> queueWait = new PriorityQueue<>();
    public Map<Long, List<ScheduledTask>> taskMap = new HashMap<>();

    public SchedulerData(String p_i2141_1_) {
        super(name);
    }

    public static PriorityQueue<ScheduledTask> getTasks(World world) {
        SchedulerData scheduler = get(world);
        return scheduler.queue;
    }

    public static PriorityQueue<ScheduledTask> getWaitingTasks(World world) {
        SchedulerData scheduler = get(world);
        return scheduler.queueWait;
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

        NBTTagList listWait = new NBTTagList();

        for (ScheduledTask task : queueWait) {
            NBTTagCompound tag = new NBTTagCompound();
            task.writeToNBT(tag);
            list.appendTag(tag);
        }

        nbt.setTag("WaitingTasks", listWait);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        queue.clear();

        NBTTagList list = nbt.getTagList("ScheduledTasks", 10);

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            ScheduledTask task = new ScheduledTask(tag);
            queue.add(task);
            taskMap.computeIfAbsent(task.packedCoords, p -> new ArrayList<>())
                .add(task);
        }

        NBTTagList listWait = nbt.getTagList("WaitingTasks", 10);

        for (int i = 0; i < listWait.tagCount(); i++) {
            NBTTagCompound tag = listWait.getCompoundTagAt(i);
            ScheduledTask task = new ScheduledTask(tag);
            queueWait.add(task);
            taskMap.computeIfAbsent(task.packedCoords, p -> new ArrayList<>())
                .add(task);
        }
    }

    public static void addScheduledTask(int inTime, World world) {
        long currentTime = GlobalTickingData.getTickTime(world);
        SchedulerData scheduler = get(world);

        scheduler.queue.add(new ScheduledTask(ScheduledTask.simpleTask, currentTime, inTime));
        scheduler.markDirty();
    }

    public static void addScheduledTask(byte task, int inTime, Block block, World world, int x, int y, int z, int type,
        int id) {
        long currentTime = GlobalTickingData.getTickTime(world);
        SchedulerData scheduler = get(world);

        scheduler.queue.add(new ScheduledTask(task, block, currentTime, inTime, x, y, z, type, id));
        scheduler.markDirty();
    }

    public static void addScheduledBlockTask(int inTime, Block block, World world, int x, int y, int z, int type,
        int id) {
        addScheduledTask(ScheduledTask.blockTask, inTime, block, world, x, y, z, type, id);
    }

    public static void addScheduledTileTask(int inTime, Block block, World world, int x, int y, int z, int type,
        int id) {
        addScheduledTask(ScheduledTask.tileTask, inTime, block, world, x, y, z, type, id);
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

    public static void addWaitingTask(ScheduledTask task, World world) {
        SchedulerData scheduler = get(world);

        scheduler.queueWait.add(task);
        scheduler.markDirty();
    }

    public static void moveScheduledTask(Block block, World world, int x, int y, int z, int newX, int newY, int newZ) {
        SchedulerData scheduler = get(world);

        long packedCoords = Utils.packCoord(x, y, z);
        List<ScheduledTask> tasks = scheduler.taskMap.remove(packedCoords);

        if (tasks != null) {
            for (ScheduledTask task : tasks) {
                task.updateCoords(newX, newY, newZ);
                scheduler.taskMap.computeIfAbsent(task.packedCoords, p -> new ArrayList<>())
                    .add(task);
            }
        }
    }

    public static void tickTasks(long currentTick, World world) {
        SchedulerData scheduler = get(world);

        while (!scheduler.queue.isEmpty() && scheduler.queue.peek().timeScheduled <= currentTick) {
            ScheduledTask task = scheduler.queue.poll();

            task.run(world);
            scheduler.markDirty();
        }
    }

    public static void tickWaitingTasks(World world) {
        SchedulerData scheduler = get(world);

        if (!scheduler.queueWait.isEmpty()) {
            Iterator<ScheduledTask> it = scheduler.queueWait.iterator();
            while (it.hasNext()) {
                ScheduledTask task = it.next();
                if (task.isLoaded(world)) {
                    it.remove();
                    task.run(world);
                    scheduler.markDirty();
                }
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
