package net.pufferlab.primal.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.utils.PosMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;

public class ChunkDataManager {

    private static final ChunkDataManager instance = new ChunkDataManager();
    private static final ChunkDataManager instanceClient = new ChunkDataManager();

    public static final TIntObjectMap<ChunkDataManager> chunkDataManagerMap = new TIntObjectHashMap<>();

    public final PosMap.Single<ChunkDataStorage> chunkDataStorage = new PosMap.Single<>();

    public ChunkDataManager() {}

    public static ChunkDataManager getClientDataManager() {
        return ChunkDataManager.instanceClient;
    }

    public static ChunkDataManager getDataManager(World world) {
        if (world.isRemote) return getClientDataManager();
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
        ChunkDataStorage storage = chunkDataStorage.get(x, z);
        if (storage == null) {
            storage = new ChunkDataStorage(x, z);
            chunkDataStorage.put(x, z, storage);
        }
        return storage;
    }

    public ChunkDataStorage remove(int x, int z) {
        return chunkDataStorage.remove(x, z);
    }

    public void readFromNBT(NBTTagCompound nbt, Chunk chunk) {
        readFromNBT(nbt, chunk.xPosition, chunk.zPosition);
    }

    public void readFromNBT(NBTTagCompound nbt, int x, int z) {
        ChunkDataStorage storage = new ChunkDataStorage(x, z);
        storage.readFromNBT(nbt);
        chunkDataStorage.put(x, z, storage);
    }

    public void writeToNBT(NBTTagCompound nbt, Chunk chunk) {
        writeToNBT(nbt, chunk.xPosition, chunk.zPosition);
    }

    public void writeToNBT(NBTTagCompound nbt, int x, int z) {
        ChunkDataStorage storage = chunkDataStorage.get(x, z);
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

        chunkDataStorage.put(x, z, storage);
    }

    public void writeToBuffer(ByteBuf buf, int x, int z) {
        ChunkDataStorage storage = chunkDataStorage.get(x, z);

        if (storage != null) {
            buf.writeBoolean(true);
            storage.writeToBuffer(buf);
        } else {
            buf.writeBoolean(false);
        }
    }
}
