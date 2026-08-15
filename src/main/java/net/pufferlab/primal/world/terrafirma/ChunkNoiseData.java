package net.pufferlab.primal.world.terrafirma;

import static net.pufferlab.primal.utils.HashUtils.pack2DCoord;

public class ChunkNoiseData {

    public int chunkX;
    public int chunkZ;

    public ChunkNoiseData(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public int[] heightmap = new int[256];
    public int[] heightmapExtra = new int[68];
    public int[] biomes = new int[256];
    public int[] steepness = new int[256];

    public float[] temperature = new float[256];
    public float[] rainfall = new float[256];
    public float[] vegetation = new float[256];
    public float[] forestness = new float[256];

    public float[] detail = new float[256];

    public BiomesTF getBiome(int x, int z) {
        return BiomesTF.getBiome(biomes[pack2DCoord(x, z)]);
    }

    public void setBiome(int x, int z, BiomesTF biome) {
        biomes[pack2DCoord(x, z)] = BiomesTF.getID(biome);
    }

    public int getHeight(int x, int z) {
        int index = getBorderIndex(x, z);
        if (index < 0) {
            return heightmap[pack2DCoord(x, z)];
        } else {
            return heightmapExtra[index];
        }
    }

    public void setHeight(int x, int z, int value) {
        int index = getBorderIndex(x, z);
        if (index < 0) {
            heightmap[pack2DCoord(x, z)] = value;
        } else {
            heightmapExtra[index] = value;
        }
    }

    public int getSteepness(int x, int z) {
        return steepness[pack2DCoord(x, z)];
    }

    public void setSteepness(int x, int z, int value) {
        this.steepness[pack2DCoord(x, z)] = value;
    }

    public float getRainfall(int x, int z) {
        return rainfall[pack2DCoord(x, z)];
    }

    public void setRainfall(int x, int z, float value) {
        this.rainfall[pack2DCoord(x, z)] = value;
    }

    public float getTemperature(int x, int z) {
        return temperature[pack2DCoord(x, z)];
    }

    public void setTemperature(int x, int z, float value) {
        this.temperature[pack2DCoord(x, z)] = value;
    }

    public float getVegetation(int x, int z) {
        return vegetation[pack2DCoord(x, z)];
    }

    public void setVegetation(int x, int z, float value) {
        this.vegetation[pack2DCoord(x, z)] = value;
    }

    public float getForestness(int x, int z) {
        return forestness[pack2DCoord(x, z)];
    }

    public void setForestness(int x, int z, float value) {
        this.forestness[pack2DCoord(x, z)] = value;
    }

    // Not important

    public float getDetail(int x, int z) {
        return detail[pack2DCoord(x, z)];
    }

    public void setDetail(int x, int z, float value) {
        this.detail[pack2DCoord(x, z)] = value;
    }

    public int getBorderIndex(int x, int z) {
        if (z == -1) {
            return x + 1;
        }

        if (z == 16) {
            return 18 + x + 1;
        }

        if (x == -1) {
            return 36 + z;
        }

        if (x == 16) {
            return 52 + z;
        }

        return -1;
    }
}
