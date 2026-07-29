package net.pufferlab.primal.world.terrafirma;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.world.ChunkDataManager;
import net.pufferlab.primal.world.ChunkDataStorage;
import net.pufferlab.primal.world.ChunkSavedData;

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
    public void writeToNBT(NBTTagCompound nbt) {
        int[] rocknessInt = getIntArray(rockness);
        nbt.setIntArray("rockness", rocknessInt);

        int[] rainfallInt = getIntArray(rainfall);
        nbt.setIntArray("rainfall", rainfallInt);
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

    public static ChunkDataTF get(Chunk chunk) {
        ChunkDataManager manager = ChunkDataManager.getChunkDataManager(chunk.worldObj);

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
