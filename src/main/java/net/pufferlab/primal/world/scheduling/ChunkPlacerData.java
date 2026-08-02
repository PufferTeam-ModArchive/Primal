package net.pufferlab.primal.world.scheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.NBTType;
import net.pufferlab.primal.utils.PosMap;
import net.pufferlab.primal.utils.WorldUtils;

public class ChunkPlacerData extends WorldSavedData {

    public static final String name = Primal.MODID + "ChunkPlacerData";
    public static final String nameBlocks = "QueuedBlocks";

    public ChunkPlacerData(String p_i2141_1_) {
        super(name);
    }

    public List<BlockHolder> list = new ArrayList<>();
    public PosMap.Multi<BlockHolder> map = new PosMap.Multi<>();
    public ConcurrentLinkedQueue<BlockHolder> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        readFromNBT(tag, nameBlocks, list, map, queue);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        writeToNBT(tag, nameBlocks, list, map, queue);
    }

    public void writeToNBT(NBTTagCompound nbt, String name, List<BlockHolder> queue, PosMap.Multi<BlockHolder> map,
        ConcurrentLinkedQueue<BlockHolder> concurrentQueue) {
        NBTTagList list = new NBTTagList();

        for (BlockHolder task : queue) {
            if (task.invalid()) continue;
            NBTTagCompound tag = new NBTTagCompound();
            task.writeToNBT(tag);
            list.appendTag(tag);
        }
        for (BlockHolder task : concurrentQueue) {
            if (task.invalid()) continue;
            NBTTagCompound tag = new NBTTagCompound();
            task.writeToNBT(tag);
            list.appendTag(tag);
        }

        nbt.setTag(name, list);
    }

    public void readFromNBT(NBTTagCompound nbt, String name, List<BlockHolder> queue, PosMap.Multi<BlockHolder> map,
        ConcurrentLinkedQueue<BlockHolder> concurrentQueue) {
        NBTTagList list = nbt.getTagList(name, NBTType.TagCompound);

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            BlockHolder task = new BlockHolder(tag);
            queue.add(task);
            if (map != null) {
                map.put(task.chunkX, task.chunkZ, task);
            }
        }
    }

    public static void addBlock(World world, int x, int y, int z, Block block, int meta, NBTTagCompound nbt,
        boolean fastPlace) {
        ChunkPlacerData placer = get(world);
        BlockHolder blockHolder;
        if (nbt != null) {
            blockHolder = new BlockHolder(x, y, z, block, meta, nbt);
        } else {
            blockHolder = new BlockHolder(x, y, z, block, meta);
            blockHolder.setFastPlace(fastPlace);
        }
        placer.addBlockHolder(blockHolder);
    }

    public static void placeBlock(World world, int x, int y, int z, Block block, int meta, NBTTagCompound nbt,
        boolean fastPlace) {
        if (BlockUtils.isOreBlock(block)) {
            Block blockBefore = world.getBlock(x, y, z);
            if (!BlockUtils.isNaturalStone(blockBefore)) return;
            meta = world.getBlockMetadata(x, y, z);
        }
        if (fastPlace) {
            WorldUtils.setBlock(world, x, y, z, block, meta);
        } else {
            world.setBlock(x, y, z, block, meta, 2);
            WorldUtils.setTileEntityNBT(world, x, y, z, block, meta, nbt);
        }
    }

    public static void addBlock(World world, int x, int y, int z, Block block, int meta, NBTTagCompound nbt) {
        addBlock(world, x, y, z, block, meta, nbt, false);
    }

    public static void addBlockFast(World world, int x, int y, int z, Block block, int meta) {
        addBlock(world, x, y, z, block, meta, null, true);
    }

    public void addBlockHolder(BlockHolder blockHolder) {
        this.queue.add(blockHolder);
    }

    public void tickQueue() {
        BlockHolder blockHolder;
        while ((blockHolder = this.queue.poll()) != null) {
            this.list.add(blockHolder);
            this.map.put(blockHolder.chunkX, blockHolder.chunkZ, blockHolder);
            this.markDirty();
        }
    }

    public synchronized static void tickPlacement(World world, int chunkX, int chunkZ) {
        ChunkPlacerData placer = get(world);
        placer.tickQueue();

        List<BlockHolder> blockHolder = placer.map.get(chunkX, chunkZ);
        if (blockHolder == null) return;
        for (BlockHolder block : blockHolder) {
            boolean executed = block.place(world);
            if (executed) {
                block.invalidate();
            }
        }
        blockHolder.removeIf(BlockHolder::invalid);
        placer.markDirty();
    }

    public static ChunkPlacerData get(World world) {
        ChunkPlacerData data = (ChunkPlacerData) world.loadItemData(ChunkPlacerData.class, name);

        if (data == null) {
            data = new ChunkPlacerData(name);
            world.setItemData(name, data);
        }

        return data;
    }

}
