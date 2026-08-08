package net.pufferlab.primal.world.scheduling;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.*;

public class ChunkPlacerData extends WorldSavedData {

    public static final String name = Primal.MODID + "ChunkPlacerData";
    public static final String nameBlocks = "QueuedBlocks";

    public ChunkPlacerData(String p_i2141_1_) {
        super(name);
    }

    public PosMap.ConcurrentSingle<ChunkBlockHolder> chunkDataMap = new PosMap.ConcurrentSingle<>();

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        readFromNBT(tag, nameBlocks, chunkDataMap);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        writeToNBT(tag, nameBlocks, chunkDataMap);
    }

    public void writeToNBT(NBTTagCompound nbt, String name, PosMap.ConcurrentSingle<ChunkBlockHolder> chunkDataMap) {
        NBTTagList list = new NBTTagList();

        for (ChunkBlockHolder data : chunkDataMap.values()) {
            NBTTagCompound tag = new NBTTagCompound();
            data.writeToNBT(tag);
            list.appendTag(tag);
        }

        nbt.setTag(name, list);
    }

    public void readFromNBT(NBTTagCompound nbt, String name, PosMap.ConcurrentSingle<ChunkBlockHolder> chunkDataMap) {
        NBTTagList list = nbt.getTagList(name, NBTType.TagCompound);

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            ChunkBlockHolder data = new ChunkBlockHolder(0, 0);
            data.readFromNBT(tag);
            chunkDataMap.put(data.chunkX, data.chunkZ, data);
        }
    }

    public static void addBlock(World world, int x, int y, int z, Block block, int meta, NBTTagCompound nbt,
        boolean fastPlace) {
        ChunkPlacerData placer = get(world);

        int chunkX = x >> 4;
        int chunkZ = z >> 4;

        ChunkBlockHolder data = placer.chunkDataMap
            .computeIfAbsent(chunkX, chunkZ, c -> new ChunkBlockHolder(chunkX, chunkZ));
        if (nbt == null) {
            data.setBlock(x & 15, y, z & 15, block, meta);
        } else {
            data.setBlock(x & 15, y, z & 15, block, meta, nbt);
        }
        if (!fastPlace) {
            data.updateSkylight = true;
        }
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

    public static void tickPlacement(World world, int chunkX, int chunkZ) {
        ChunkPlacerData placer = get(world);

        ChunkBlockHolder data = placer.chunkDataMap.remove(chunkX, chunkZ);
        Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
        if (data != null) {
            data.placeToChunk(chunk);
            data.placeTileEntity(chunk);
            data.blocks = null;
            data.metas = null;
            data.tags = null;
        }
    }

    private static final Map<World, ChunkPlacerData> CACHE = new WeakHashMap<>();

    public static ChunkPlacerData get(World world) {
        synchronized (CACHE) {
            ChunkPlacerData data = CACHE.get(world);

            if (data == null) {
                data = (ChunkPlacerData) world.loadItemData(ChunkPlacerData.class, name);

                if (data == null) {
                    data = new ChunkPlacerData(name);
                    world.setItemData(name, data);
                }

                CACHE.put(world, data);
            }

            return data;
        }
    }

}
