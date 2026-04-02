package net.pufferlab.primal.world;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.PositionMap;

public class SchedulerData extends WorldSavedData {

    private static final String name = Primal.MODID + "SchedulerData";
    private static final String nameQueue = "ScheduledTasks";
    private static final String nameQueueWait = "ScheduledWaitingTasks";

    public PriorityQueue<ScheduledTask> queue = new PriorityQueue<>();
    public PriorityQueue<ScheduledTask> queueWait = new PriorityQueue<>();
    public PositionMap<ScheduledTask> taskMap = new PositionMap<>();

    public SchedulerData(String p_i2141_1_) {
        super(name);
    }

    public static List<ScheduledTask> getTasks(World world) {
        SchedulerData scheduler = get(world);
        List<ScheduledTask> list = new ArrayList<>();
        for (ScheduledTask task : scheduler.queue) {
            if (task.invalid()) continue;
            list.add(task);
        }
        scheduler.queue.removeIf(ScheduledTask::invalid);
        return list;
    }

    public static PriorityQueue<ScheduledTask> getWaitingTasks(World world) {
        SchedulerData scheduler = get(world);
        return scheduler.queueWait;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        writeToNBT(nbt, nameQueue, queue, taskMap);
        writeToNBT(nbt, nameQueueWait, queueWait, null);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        queue.clear();

        readFromNBT(nbt, nameQueue, queue, taskMap);
        readFromNBT(nbt, nameQueueWait, queueWait, null);
    }

    public void writeToNBT(NBTTagCompound nbt, String name, PriorityQueue<ScheduledTask> queue,
        PositionMap<ScheduledTask> map) {
        NBTTagList list = new NBTTagList();

        for (ScheduledTask task : queue) {
            if (task.invalid()) continue;
            NBTTagCompound tag = new NBTTagCompound();
            task.writeToNBT(tag);
            list.appendTag(tag);
        }

        nbt.setTag(name, list);
    }

    public void readFromNBT(NBTTagCompound nbt, String name, PriorityQueue<ScheduledTask> queue,
        PositionMap<ScheduledTask> map) {
        NBTTagList list = nbt.getTagList(name, Constants.tagCompound);

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            ScheduledTask task = new ScheduledTask(tag);
            queue.add(task);
            if (map != null) {
                map.put(task.x, task.y, task.z, task);
            }
        }
    }

    public void addScheduledTask(ScheduledTask task) {
        this.queue.add(task);
        this.taskMap.put(task.x, task.y, task.z, task);
        this.markDirty();
    }

    public void removeScheduledTask(int x, int y, int z) {
        List<ScheduledTask> tasks = this.taskMap.remove(x, y, z);
        if (tasks == null) return;
        if (tasks.isEmpty()) return;
        for (ScheduledTask task : tasks) {
            if (task.equals(x, y, z)) {
                task.invalidate();
            }
        }
        this.markDirty();
    }

    public void removeScheduledTask(int x, int y, int z, int type) {
        List<ScheduledTask> tasks = this.taskMap.get(x, y, z);
        if (tasks == null) return;
        if (tasks.isEmpty()) return;
        for (ScheduledTask task : tasks) {
            if (task.equals(x, y, z, type)) {
                task.invalidate();
            }
        }
        tasks.removeIf(task -> task.equals(x, y, z, type));
        this.markDirty();
    }

    public boolean hasScheduledTask(int x, int y, int z, int type) {
        List<ScheduledTask> tasks = this.taskMap.get(x, y, z);
        if (tasks == null) return false;
        if (tasks.isEmpty()) return false;
        for (ScheduledTask task : tasks) {
            if (task.equals(type) && !task.invalid()) return true;
        }
        return false;
    }

    public static boolean hasScheduledTask(World world, int x, int y, int z, int type) {
        SchedulerData scheduler = get(world);

        return scheduler.hasScheduledTask(x, y, z, type);
    }

    public static void addScheduledTask(int inTime, World world) {
        long currentTime = GlobalTickingData.getTickTime(world);
        SchedulerData scheduler = get(world);

        scheduler.addScheduledTask(
            new ScheduledTask(ScheduledTask.TaskType.getID(ScheduledTask.TaskType.simpleTask), currentTime, inTime));
    }

    public static void addScheduledTask(byte task, int inTime, Block block, World world, int x, int y, int z,
        int type) {
        long currentTime = GlobalTickingData.getTickTime(world);
        SchedulerData scheduler = get(world);

        scheduler.addScheduledTask(new ScheduledTask(task, block, currentTime, inTime, x, y, z, type, 0));
    }

    public static void addScheduledTask(ScheduledTask.TaskType task, int inTime, Block block, World world, int x, int y,
        int z, int type) {
        addScheduledTask(ScheduledTask.TaskType.getID(task), inTime, block, world, x, y, z, type);
    }

    public static void addScheduledBlockTask(int inTime, Block block, World world, int x, int y, int z, int type) {
        addScheduledTask(ScheduledTask.TaskType.blockTask, inTime, block, world, x, y, z, type);
    }

    public static void addScheduledTileTask(int inTime, Block block, World world, int x, int y, int z, int type) {
        addScheduledTask(ScheduledTask.TaskType.tileTask, inTime, block, world, x, y, z, type);
    }

    public static void removeScheduledTask(World world, int x, int y, int z, int type) {
        SchedulerData scheduler = get(world);

        scheduler.removeScheduledTask(x, y, z, type);
    }

    public static void removeScheduledTask(World world, int x, int y, int z) {
        SchedulerData scheduler = get(world);

        scheduler.removeScheduledTask(x, y, z);
    }

    public static void addWaitingTask(ScheduledTask task, World world) {
        SchedulerData scheduler = get(world);

        scheduler.queueWait.add(task);
        scheduler.markDirty();
    }

    public static void moveScheduledTask(Block block, World world, int x, int y, int z, int newX, int newY, int newZ) {
        SchedulerData scheduler = get(world);

        List<ScheduledTask> tasks = scheduler.taskMap.remove(x, y, z);

        if (tasks != null) {
            for (ScheduledTask task : tasks) {
                task.updateCoords(newX, newY, newZ);
                scheduler.taskMap.put(task.x, task.y, task.z, task);
            }
        }
    }

    public static void tickTasks(long currentTick, World world) {
        SchedulerData scheduler = get(world);

        while (!scheduler.queue.isEmpty() && scheduler.queue.peek().timeScheduled <= currentTick) {
            ScheduledTask task = scheduler.queue.poll();

            boolean executed = task.run(world);
            if (executed) {
                task.invalidate();
            }
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
