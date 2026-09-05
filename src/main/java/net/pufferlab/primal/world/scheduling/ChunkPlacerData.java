package net.pufferlab.primal.world.scheduling;

import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
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

    public static void addBlock(World world, int x, int y, int z, Block block, int meta, NBTTagCompound nbt) {
        addBlock(world, x, y, z, block, meta, nbt, false);
    }

    public static void addBlockFast(World world, int x, int y, int z, Block block, int meta) {
        addBlock(world, x, y, z, block, meta, null, true);
    }

    public static void tickPlacement(World world, Entity entity) {
        int posX = Mth.floor(entity.posX);
        int posZ = Mth.floor(entity.posZ);

        int chunkX = posX >> 4;
        int chunkZ = posZ >> 4;

        tickPlacement(world, chunkX, chunkZ);
    }

    public static void tickPlacement(World world, int chunkX, int chunkZ) {
        ChunkPlacerData placer = get(world);

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                IChunkProvider provider = world.getChunkProvider();

                if (provider.chunkExists(chunkX + x, chunkZ + z)) {
                    ChunkBlockHolder data = placer.chunkDataMap.remove(chunkX + x, chunkZ + z);
                    if (data != null) {
                        Chunk chunk = world.getChunkFromChunkCoords(chunkX + x, chunkZ + z);
                        data.placeToChunk(chunk);
                        data.placeTileEntity(chunk);
                        data.invalidate();
                    }
                }

            }
        }
    }

    private static final ConcurrentHashMap<World, ChunkPlacerData> cache = new ConcurrentHashMap<>();

    public static ChunkPlacerData get(final World world) {
        return cache.computeIfAbsent(world, w -> {
            ChunkPlacerData data = (ChunkPlacerData) w.loadItemData(ChunkPlacerData.class, name);

            if (data == null) {
                data = new ChunkPlacerData(name);
                w.setItemData(name, data);
            }

            return data;
        });
    }

}
