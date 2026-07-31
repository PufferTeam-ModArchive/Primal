package net.pufferlab.primal.world.terrafirma;

import static net.pufferlab.primal.utils.Utils.getFloatArray;
import static net.pufferlab.primal.utils.Utils.getIntArray;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.IOUtils;
import net.pufferlab.primal.utils.PositionUtils;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.ChunkDataManager;
import net.pufferlab.primal.world.ChunkDataStorage;
import net.pufferlab.primal.world.ChunkSavedData;

import io.netty.buffer.ByteBuf;

public class ChunkDataTF extends ChunkSavedData {

    public static final String name = Primal.MODID + "ChunkDataTF";

    public int[] biomes = new int[256];
    public int[] heightmap = new int[256];
    public float[] rockness = new float[256];
    public float[] rainfall = new float[256];
    public float[] vegetation = new float[256];

    public ChunkDataTF(String p_i2141_1_) {
        super(name);
    }

    // Not a good idea right now
    public void syncBiomeArray(Chunk chunk) {
        chunk.setBiomeArray(Utils.getByteArray(biomes));
    }

    public BiomesTF getBiome(int x, int z) {
        return BiomesTF.getBiome(biomes[PositionUtils.pack4BitsCoord(x, z)]);
    }

    public void setBiome(int x, int z, BiomesTF biome) {
        biomes[PositionUtils.pack4BitsCoord(x, z)] = BiomesTF.getID(biome);
    }

    public int getHeight(int x, int z) {
        return heightmap[PositionUtils.pack4BitsCoord(x, z)];
    }

    public void setHeight(int x, int z, int value) {
        heightmap[PositionUtils.pack4BitsCoord(x, z)] = value;
    }

    public float getRockiness(int x, int z) {
        return rockness[PositionUtils.pack4BitsCoord(x, z)];
    }

    public void setRockiness(int x, int z, float value) {
        this.rockness[PositionUtils.pack4BitsCoord(x, z)] = value;
    }

    public float getRainfall(int x, int z) {
        return rainfall[PositionUtils.pack4BitsCoord(x, z)];
    }

    public void setRainfall(int x, int z, float value) {
        this.rainfall[PositionUtils.pack4BitsCoord(x, z)] = value;
    }

    public float getVegetation(int x, int z) {
        return vegetation[PositionUtils.pack4BitsCoord(x, z)];
    }

    public void setVegetation(int x, int z, float value) {
        this.vegetation[PositionUtils.pack4BitsCoord(x, z)] = value;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.heightmap = nbt.getIntArray("terrain_height");

        this.biomes = nbt.getIntArray("biomes");

        int[] rocknessInt = nbt.getIntArray("rockiness");
        this.rockness = getFloatArray(rocknessInt);

        int[] rainfallInt = nbt.getIntArray("rainfall");
        this.rainfall = getFloatArray(rainfallInt);

        int[] vegetationInt = nbt.getIntArray("vegetation");
        this.vegetation = getFloatArray(vegetationInt);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        int[] rocknessInt = IOUtils.readIntArray(buf);
        this.rockness = getFloatArray(rocknessInt);

        int[] rainfallInt = IOUtils.readIntArray(buf);
        this.rainfall = getFloatArray(rainfallInt);

        int[] vegetationInt = IOUtils.readIntArray(buf);
        this.vegetation = getFloatArray(vegetationInt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setIntArray("terrain_height", heightmap);

        nbt.setIntArray("biomes", this.biomes);

        int[] rocknessInt = getIntArray(rockness);
        nbt.setIntArray("rockiness", rocknessInt);

        int[] rainfallInt = getIntArray(rainfall);
        nbt.setIntArray("rainfall", rainfallInt);

        int[] vegetationInt = getIntArray(vegetation);
        nbt.setIntArray("vegetation", vegetationInt);
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        int[] rocknessInt = getIntArray(rockness);
        IOUtils.writeIntArray(buf, rocknessInt);

        int[] rainfallInt = getIntArray(rainfall);
        IOUtils.writeIntArray(buf, rainfallInt);

        int[] vegetationInt = getIntArray(vegetation);
        IOUtils.writeIntArray(buf, vegetationInt);
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
