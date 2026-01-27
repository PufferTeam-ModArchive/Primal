package net.pufferlab.primal.world;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.CoordUtils;

public class SchedulerData extends WorldSavedData {

    private static String name = Primal.MODID + "SchedulerData";

    public PriorityQueue<ScheduledTask> queue = new PriorityQueue<>();
    public Map<Long, List<ScheduledTask>> taskMap = new HashMap<>();

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
            ScheduledTask task = new ScheduledTask(tag);
            queue.add(task);
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

    public static void moveScheduledTask(Block block, World world, int x, int y, int z, int newX, int newY, int newZ) {
        SchedulerData scheduler = get(world);

        long packedCoords = CoordUtils.pack(x, y, z);
        List<ScheduledTask> tasks = scheduler.taskMap.get(packedCoords);

        if (tasks != null) {
            Iterator<ScheduledTask> it = tasks.iterator();
            while (it.hasNext()) {
                ScheduledTask task = it.next();
                if (task.equals(block, x, y, z)) {
                    it.remove();

                    task.updatePos(newX, newY, newZ);

                    scheduler.taskMap.computeIfAbsent(task.packedCoords, p -> new ArrayList<>())
                        .add(task);
                }
            }

            if (tasks.isEmpty()) {
                scheduler.taskMap.remove(packedCoords);
            }
        }
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
