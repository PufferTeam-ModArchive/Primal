package net.pufferlab.primal.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.PositionMap;

public class ChunkPlacerData extends WorldSavedData {

    public static final String name = Primal.MODID + "ChunkPlacerData";
    public static final String nameBlocks = "QueuedBlocks";

    public ChunkPlacerData(String p_i2141_1_) {
        super(name);
    }

    public List<BlockHolder> list = new ArrayList<>();
    public PositionMap<BlockHolder> map = new PositionMap<>();

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        readFromNBT(tag, nameBlocks, list, map);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        writeToNBT(tag, nameBlocks, list, map);
    }

    public void writeToNBT(NBTTagCompound nbt, String name, List<BlockHolder> queue, PositionMap<BlockHolder> map) {
        NBTTagList list = new NBTTagList();

        for (BlockHolder task : queue) {
            if (task.invalid()) continue;
            NBTTagCompound tag = new NBTTagCompound();
            task.writeToNBT(tag);
            list.appendTag(tag);
        }

        nbt.setTag(name, list);
    }

    public void readFromNBT(NBTTagCompound nbt, String name, List<BlockHolder> queue, PositionMap<BlockHolder> map) {
        NBTTagList list = nbt.getTagList(name, Constants.tagCompound);

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            BlockHolder task = new BlockHolder(tag);
            queue.add(task);
            if (map != null) {
                map.put(task.chunkX, task.chunkZ, task);
            }
        }
    }

    public static void addBlock(World world, int x, int y, int z, Block block, int meta) {
        ChunkPlacerData placer = get(world);
        BlockHolder blockHolder = new BlockHolder(x, y, z, block, meta);
        placer.list.add(blockHolder);
        placer.map.put(blockHolder.chunkX, blockHolder.chunkZ, blockHolder);
        placer.markDirty();
    }

    public static void tickPlacement(World world, int chunkX, int chunkZ) {
        ChunkPlacerData placer = get(world);
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
