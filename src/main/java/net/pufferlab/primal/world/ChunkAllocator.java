package net.pufferlab.primal.world;

import java.util.Arrays;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.ForgeChunkManager;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.PositionUtils;
import net.pufferlab.primal.utils.WorldUtils;

public class ChunkAllocator extends WorldSavedData {

    private static final String name = Primal.MODID + "ProjectedChunk";

    int lastX;
    int lastY;
    int lastZ;
    public int[] chunkCoords;

    public static int startXZ = 10_000_000;

    public ChunkAllocator(String name) {
        super(name);
        lastX = 10_000_000;
        lastY = 240;
        lastZ = 10_000_000;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("lastKey")) {
            long coords = nbt.getLong("lastKey");
            lastX = PositionUtils.unpackX(coords);
            lastY = PositionUtils.unpackY(coords);
            lastZ = PositionUtils.unpackZ(coords);
        }
        chunkCoords = nbt.getIntArray("chunkCoords");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        long coords = PositionUtils.packCoord(lastX, lastY, lastZ);
        nbt.setLong("lastKey", coords);
        nbt.setIntArray("chunkCoords", chunkCoords);
    }

    public static ChunkAllocator get(World world) {
        ChunkAllocator data = (ChunkAllocator) world.loadItemData(ChunkAllocator.class, name);

        if (data == null) {
            data = new ChunkAllocator(name);
            world.setItemData(name, data);
        }

        return data;
    }

    public static VirtualBlock allocateNewVirtualBlock(World world) {
        ChunkAllocator allocator = get(world);
        allocator.markDirty();
        return allocator.allocateLocalNewVirtualBlock(world);
    }

    public VirtualBlock allocateLocalNewVirtualBlock(World world) {
        if (world.isRemote) return null;
        if (lastY < 240) {
            lastY += 16;
        } else {
            lastY = 0;

            if (lastX < 30_000_000) {
                lastX += 16;
            } else {
                lastX = 10_000_000;
                lastZ += 16;
            }
        }
        if (!contains(lastX, lastZ)) {
            if (world instanceof WorldServer worldServer) {
                worldServer.theChunkProviderServer.loadChunk(lastX >> 4, lastZ >> 4);
                cleanChunk(worldServer, lastX >> 4, lastZ >> 4);
            }
            chunkCoords = appendXZ(chunkCoords, lastX, lastZ);
        }
        int sectionIndex = lastY >> 4;
        int baseX = lastX;
        int baseY = sectionIndex << 4;
        int baseZ = lastZ;
        return new VirtualBlock(baseX + 8, baseY + 8, baseZ + 8);
    }

    public void cleanChunk(World world, int chunkX, int chunkZ) {
        Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);

        ExtendedBlockStorage[] sections = chunk.getBlockStorageArray();

        for (int section = 0; section < sections.length; section++) {
            ExtendedBlockStorage storage = sections[section];
            if (storage == null) continue;

            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    for (int x = 0; x < 16; x++) {
                        WorldUtils.setChunkBlock(storage, x, y, z, Blocks.air, 0);
                    }
                }
            }
        }

        chunk.isModified = true;
    }

    public static int[] appendXZ(int[] array, int x, int z) {
        int[] result = Arrays.copyOf(array, array.length + 2);
        result[array.length] = x;
        result[array.length + 1] = z;
        return result;
    }

    public boolean contains(int x, int z) {
        if (chunkCoords == null) {
            chunkCoords = new int[0];
        }
        for (int i = 0; i < chunkCoords.length; i += 2) {
            if (x == chunkCoords[i] && z == chunkCoords[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public void loadChunk(World world, int chunkX, int chunkZ) {
        ForgeChunkManager.Ticket ticket = ForgeChunkManager
            .requestTicket(Primal.instance, world, ForgeChunkManager.Type.NORMAL);

        if (ticket != null) {
            ChunkCoordIntPair chunk = new ChunkCoordIntPair(chunkX, chunkZ);
            ForgeChunkManager.forceChunk(ticket, chunk);
        }
    }
}
