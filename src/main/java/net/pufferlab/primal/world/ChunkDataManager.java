package net.pufferlab.primal.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.utils.PositionUtils;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import io.netty.buffer.ByteBuf;

public class ChunkDataManager {

    private static final ChunkDataManager instance = new ChunkDataManager();
    private static final ChunkDataManager instanceClient = new ChunkDataManager();

    public static final TIntObjectMap<ChunkDataManager> chunkDataManagerMap = new TIntObjectHashMap<>();

    public final TLongObjectMap<ChunkDataStorage> chunkDataStorage = new TLongObjectHashMap<>();

    public ChunkDataManager() {}

    public static ChunkDataManager getClientDataManager() {
        return ChunkDataManager.instanceClient;
    }

    public static ChunkDataManager getDataManager(World world) {
        return getDataManager(world.provider.dimensionId);
    }

    public static ChunkDataManager getDataManager(int dimensionID) {
        ChunkDataManager manager = chunkDataManagerMap.get(dimensionID);
        if (manager == null) {
            manager = new ChunkDataManager();
            chunkDataManagerMap.put(dimensionID, manager);
        }
        return manager;
    }

    public ChunkDataStorage get(Chunk chunk) {
        return get(chunk.xPosition, chunk.zPosition);
    }

    public ChunkDataStorage remove(Chunk chunk) {
        return remove(chunk.xPosition, chunk.zPosition);
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

    public ChunkDataStorage remove(int x, int z) {
        long coord = PositionUtils.packChunkCoord(x, z);
        return chunkDataStorage.remove(coord);
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

    public void readFromBuffer(ByteBuf buf, int x, int z) {
        if (!buf.readBoolean()) {
            return;
        }

        ChunkDataStorage storage = new ChunkDataStorage(x, z);
        storage.readFromBuffer(buf);

        long coord = PositionUtils.packChunkCoord(x, z);
        chunkDataStorage.put(coord, storage);
    }

    public void writeToBuffer(ByteBuf buf, int x, int z) {
        long coord = PositionUtils.packChunkCoord(x, z);
        ChunkDataStorage storage = chunkDataStorage.get(coord);

        if (storage != null) {
            buf.writeBoolean(true);
            storage.writeToBuffer(buf);
        } else {
            buf.writeBoolean(false);
        }
    }
}
