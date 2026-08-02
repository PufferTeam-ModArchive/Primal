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
import net.pufferlab.primal.world.terrafirma.gen.region.data.ChunkNoiseData;

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

    public float getRockiness(int x, int z) {
        return noiseData.getRockiness(x, z);
    }

    public void setRockiness(int x, int z, float value) {
        noiseData.setRockiness(x, z, value);
    }

    public float getRainfall(int x, int z) {
        return noiseData.getRainfall(x, z);
    }

    public void setRainfall(int x, int z, float value) {
        noiseData.setRainfall(x, z, value);
    }

    public float getVegetation(int x, int z) {
        return noiseData.getVegetation(x, z);
    }

    public void setVegetation(int x, int z, float value) {
        noiseData.setVegetation(x, z, value);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        noiseData.heightmap = nbt.getIntArray("terrain_height");

        noiseData.biomes = nbt.getIntArray("biomes");

        int[] rocknessInt = nbt.getIntArray("rockiness");
        noiseData.rockness = getFloatArray(rocknessInt);

        int[] rainfallInt = nbt.getIntArray("rainfall");
        noiseData.rainfall = getFloatArray(rainfallInt);

        int[] vegetationInt = nbt.getIntArray("vegetation");
        noiseData.vegetation = getFloatArray(vegetationInt);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        int[] rocknessInt = IOUtils.readIntArray(buf);
        noiseData.rockness = getFloatArray(rocknessInt);

        int[] rainfallInt = IOUtils.readIntArray(buf);
        noiseData.rainfall = getFloatArray(rainfallInt);

        int[] vegetationInt = IOUtils.readIntArray(buf);
        noiseData.vegetation = getFloatArray(vegetationInt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setIntArray("terrain_height", noiseData.heightmap);

        nbt.setIntArray("biomes", noiseData.biomes);

        int[] rocknessInt = getIntArray(noiseData.rockness);
        nbt.setIntArray("rockiness", rocknessInt);

        int[] rainfallInt = getIntArray(noiseData.rainfall);
        nbt.setIntArray("rainfall", rainfallInt);

        int[] vegetationInt = getIntArray(noiseData.vegetation);
        nbt.setIntArray("vegetation", vegetationInt);
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        int[] rocknessInt = getIntArray(noiseData.rockness);
        IOUtils.writeIntArray(buf, rocknessInt);

        int[] rainfallInt = getIntArray(noiseData.rainfall);
        IOUtils.writeIntArray(buf, rainfallInt);

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
