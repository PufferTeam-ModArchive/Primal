package net.pufferlab.primal.world.terrafirma;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.IOUtils;
import net.pufferlab.primal.world.ChunkDataManager;
import net.pufferlab.primal.world.ChunkDataStorage;
import net.pufferlab.primal.world.ChunkSavedData;

import io.netty.buffer.ByteBuf;

public class ChunkDataTF extends ChunkSavedData {

    public static final String name = Primal.MODID + "ChunkDataTF";

    public float[][] rockness = new float[16][16];
    public float[][] rainfall = new float[16][16];

    public ChunkDataTF(String p_i2141_1_) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        int[] rocknessInt = nbt.getIntArray("rockness");
        this.rockness = getFloatArray(rocknessInt);

        int[] rainfallInt = nbt.getIntArray("rainfall");
        this.rainfall = getFloatArray(rainfallInt);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        int[] rocknessInt = IOUtils.readIntArray(buf);
        this.rockness = getFloatArray(rocknessInt);

        int[] rainfallInt = IOUtils.readIntArray(buf);
        this.rainfall = getFloatArray(rainfallInt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        int[] rocknessInt = getIntArray(rockness);
        nbt.setIntArray("rockness", rocknessInt);

        int[] rainfallInt = getIntArray(rainfall);
        nbt.setIntArray("rainfall", rainfallInt);
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        int[] rocknessInt = getIntArray(rockness);
        IOUtils.writeIntArray(buf, rocknessInt);

        int[] rainfallInt = getIntArray(rainfall);
        IOUtils.writeIntArray(buf, rainfallInt);
    }

    public int[] getIntArray(float[][] array) {
        int[] intArray = new int[256];
        for (int x = 0; x < array.length; x++) {
            for (int z = 0; z < array[x].length; z++) {
                int index = (z << 4) | x;
                intArray[index] = Float.floatToIntBits(array[x][z]);
            }
        }
        return intArray;
    }

    public float[][] getFloatArray(int[] intArray) {
        float[][] floatArray = new float[16][16];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int index = (z << 4) | x;
                floatArray[x][z] = Float.intBitsToFloat(intArray[index]);
            }
        }

        return floatArray;
    }

    public static ChunkDataTF getClient(int x, int z) {
        ChunkDataManager manager = ChunkDataManager.getClientDataManager();

        ChunkDataStorage storage = manager.get(x, z);

        return get(storage);
    }

    public static ChunkDataTF get(Chunk chunk) {
        ChunkDataManager manager = ChunkDataManager.getDataManager(chunk.worldObj);

        ChunkDataStorage storage = manager.get(chunk);
        return get(storage);
    }

    public static ChunkDataTF get(ChunkDataStorage storage) {
        ChunkDataTF data = (ChunkDataTF) storage.loadData(name);

        if (data == null) {
            data = new ChunkDataTF(name);
            storage.setData(name, data);
        }

        return data;
    }

}
