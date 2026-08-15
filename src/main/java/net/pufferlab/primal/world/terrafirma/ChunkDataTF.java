package net.pufferlab.primal.world.terrafirma;

import static net.pufferlab.primal.utils.Utils.getFloatArray;
import static net.pufferlab.primal.utils.Utils.getIntArray;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.IOUtils;
import net.pufferlab.primal.world.storage.ChunkDataManager;
import net.pufferlab.primal.world.storage.ChunkDataStorage;
import net.pufferlab.primal.world.storage.ChunkSavedData;

import io.netty.buffer.ByteBuf;

public class ChunkDataTF extends ChunkSavedData {

    public static final String name = Primal.MODID + "ChunkDataTF";

    public ChunkNoiseData noiseData = new ChunkNoiseData(0, 0);

    public void syncData(ChunkNoiseData noiseData) {
        this.noiseData = noiseData;
    }

    public ChunkDataTF(String p_i2141_1_) {
        super(name);
    }

    public BiomesTF getBiome(int x, int z) {
        return noiseData.getBiome(x, z);
    }

    public void setBiome(int x, int z, BiomesTF biome) {
        noiseData.setBiome(x, z, biome);
    }

    public int getHeight(int x, int z) {
        return noiseData.getHeight(x, z);
    }

    public void setHeight(int x, int z, int value) {
        noiseData.setHeight(x, z, value);
    }

    public int getSteepness(int x, int z) {
        return noiseData.getSteepness(x, z);
    }

    public void setSteepness(int x, int z, int value) {
        noiseData.setSteepness(x, z, value);
    }

    public float getRainfall(int x, int z) {
        return noiseData.getRainfall(x, z);
    }

    public void setRainfall(int x, int z, float value) {
        noiseData.setRainfall(x, z, value);
    }

    public float getTemperature(int x, int z) {
        return noiseData.getTemperature(x, z);
    }

    public void setTemperature(int x, int z, float value) {
        noiseData.setTemperature(x, z, value);
    }

    public float getVegetation(int x, int z) {
        return noiseData.getVegetation(x, z);
    }

    public void setVegetation(int x, int z, float value) {
        noiseData.setVegetation(x, z, value);
    }

    public float getForestness(int x, int z) {
        return noiseData.getForestness(x, z);
    }

    public void setForestness(int x, int z, float value) {
        noiseData.setForestness(x, z, value);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        noiseData.heightmap = nbt.getIntArray("terrain_height");

        noiseData.biomes = nbt.getIntArray("biomes");

        noiseData.steepness = nbt.getIntArray("steepness");

        int[] rainfallInt = nbt.getIntArray("rainfall");
        noiseData.rainfall = getFloatArray(rainfallInt);

        int[] temperatureInt = nbt.getIntArray("temperature");
        noiseData.temperature = getFloatArray(temperatureInt);

        int[] vegetationInt = nbt.getIntArray("vegetation");
        noiseData.vegetation = getFloatArray(vegetationInt);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        int[] rainfallInt = IOUtils.readIntArray(buf);
        noiseData.rainfall = getFloatArray(rainfallInt);

        int[] temperatureInt = IOUtils.readIntArray(buf);
        noiseData.temperature = getFloatArray(temperatureInt);

        int[] vegetationInt = IOUtils.readIntArray(buf);
        noiseData.vegetation = getFloatArray(vegetationInt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setIntArray("terrain_height", noiseData.heightmap);

        nbt.setIntArray("biomes", noiseData.biomes);

        nbt.setIntArray("steepness", noiseData.steepness);

        int[] rainfallInt = getIntArray(noiseData.rainfall);
        nbt.setIntArray("rainfall", rainfallInt);

        int[] temperatureInt = getIntArray(noiseData.temperature);
        nbt.setIntArray("temperature", temperatureInt);

        int[] vegetationInt = getIntArray(noiseData.vegetation);
        nbt.setIntArray("vegetation", vegetationInt);
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        int[] rainfallInt = getIntArray(noiseData.rainfall);
        IOUtils.writeIntArray(buf, rainfallInt);

        int[] temperatureInt = getIntArray(noiseData.temperature);
        IOUtils.writeIntArray(buf, temperatureInt);

        int[] vegetationInt = getIntArray(noiseData.vegetation);
        IOUtils.writeIntArray(buf, vegetationInt);
    }

    public static ChunkDataTF getClient(int x, int z) {
        ChunkDataManager manager = ChunkDataManager.getClientDataManager();

        ChunkDataStorage storage = manager.get(x, z);

        return get(storage);
    }

    public static ChunkDataTF get(World world, int x, int z) {
        ChunkDataManager manager = ChunkDataManager.getDataManager(world);

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
