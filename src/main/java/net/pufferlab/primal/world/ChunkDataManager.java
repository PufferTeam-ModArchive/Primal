package net.pufferlab.primal.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.utils.PositionUtils;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public class ChunkDataManager {

    public static final ChunkDataManager instance = new ChunkDataManager();

    public static final TIntObjectMap<ChunkDataManager> chunkDataManagerMap = new TIntObjectHashMap<>();

    public final TLongObjectMap<ChunkDataStorage> chunkDataStorage = new TLongObjectHashMap<>();

    public ChunkDataManager() {}

    public static ChunkDataManager getChunkDataManager(World world) {
        ChunkDataManager manager = chunkDataManagerMap.get(world.provider.dimensionId);
        if (manager == null) {
            manager = new ChunkDataManager();
            chunkDataManagerMap.put(world.provider.dimensionId, manager);
        }
        return manager;
    }

    public ChunkDataStorage get(Chunk chunk) {
        return get(chunk.xPosition, chunk.zPosition);
    }

    public ChunkDataStorage get(int x, int z) {
        long coord = PositionUtils.packChunkCoord(x, z);
        ChunkDataStorage storage = chunkDataStorage.get(coord);
        if (storage == null) {
            storage = new ChunkDataStorage(x, z);
            chunkDataStorage.put(coord, storage);
        }
        return storage;
    }

    public void readFromNBT(NBTTagCompound nbt, Chunk chunk) {
        readFromNBT(nbt, chunk.xPosition, chunk.zPosition);
    }

    public void readFromNBT(NBTTagCompound nbt, int x, int z) {
        ChunkDataStorage storage = new ChunkDataStorage(x, z);
        storage.readFromNBT(nbt);
        long coord = PositionUtils.packChunkCoord(x, z);
        chunkDataStorage.put(coord, storage);
    }

    public void writeToNBT(NBTTagCompound nbt, Chunk chunk) {
        writeToNBT(nbt, chunk.xPosition, chunk.zPosition);
    }

    public void writeToNBT(NBTTagCompound nbt, int x, int z) {
        long coord = PositionUtils.packChunkCoord(x, z);
        ChunkDataStorage storage = chunkDataStorage.get(coord);
        if (storage != null) {
            storage.writeToNBT(nbt);
        }
    }
}
