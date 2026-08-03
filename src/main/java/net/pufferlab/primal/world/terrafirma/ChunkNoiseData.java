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
    public int[] biomes = new int[256];

    public float[] rockness = new float[256];
    public float[] rainfall = new float[256];
    public float[] vegetation = new float[256];

    public BiomesTF getBiome(int x, int z) {
        return BiomesTF.getBiome(biomes[pack2DCoord(x, z)]);
    }

    public void setBiome(int x, int z, BiomesTF biome) {
        biomes[pack2DCoord(x, z)] = BiomesTF.getID(biome);
    }

    public int getHeight(int x, int z) {
        return heightmap[pack2DCoord(x, z)];
    }

    public void setHeight(int x, int z, int value) {
        heightmap[pack2DCoord(x, z)] = value;
    }

    public float getRockiness(int x, int z) {
        return rockness[pack2DCoord(x, z)];
    }

    public void setRockiness(int x, int z, float value) {
        this.rockness[pack2DCoord(x, z)] = value;
    }

    public float getRainfall(int x, int z) {
        return rainfall[pack2DCoord(x, z)];
    }

    public void setRainfall(int x, int z, float value) {
        this.rainfall[pack2DCoord(x, z)] = value;
    }

    public float getVegetation(int x, int z) {
        return vegetation[pack2DCoord(x, z)];
    }

    public void setVegetation(int x, int z, float value) {
        this.vegetation[pack2DCoord(x, z)] = value;
    }
}
