package net.pufferlab.primal.world.terrafirma.gen.region;

import net.minecraft.world.World;
import net.pufferlab.primal.world.terrafirma.ChunkBlockData;
import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;
import net.pufferlab.primal.world.terrafirma.gen.filler.*;
import net.pufferlab.primal.world.terrafirma.gen.noise.NoiseLayer;
import net.pufferlab.primal.world.terrafirma.gen.noise.NoiseTerrain;

public class RegionGenerator {

    public RegionProvider regionProvider;

    public NoiseLayer noiseLayer;
    public NoiseTerrain noiseTerrain;

    public TerrainFiller terrainFiller;
    public StrataLayers strataLayers;
    public SoilFiller soilFiller;
    public VegetationFiller vegetationFiller;
    public VeinFiller veinFiller;

    public RegionGenerator(World world, RegionProvider regionProvider) {
        noiseLayer = new NoiseLayer(world);
        noiseTerrain = new NoiseTerrain(world);
        terrainFiller = new TerrainFiller(world);
        strataLayers = new StrataLayers(world);
        soilFiller = new SoilFiller(world);
        veinFiller = new VeinFiller(world);
        vegetationFiller = new VegetationFiller(world);

        this.regionProvider = regionProvider;
    }

    public void genNoise(ChunkNoiseData data, int chunkX, int chunkZ) {
        this.noiseLayer.genNoiseLayers(data, chunkX, chunkZ);
        this.noiseTerrain.genTerrain(data, chunkX, chunkZ);
    }

    public void genBlocks(ChunkBlockData data, ChunkNoiseData dataNoise, int chunkX, int chunkZ) {
        this.terrainFiller.genTerrain(data, dataNoise, chunkX, chunkZ);

        this.soilFiller.genSoil(data, dataNoise, chunkX, chunkZ);
        this.strataLayers.genStrata(data, dataNoise, chunkX, chunkZ);

        this.veinFiller.genVein(data, dataNoise, chunkX, chunkZ);
        this.vegetationFiller.genVegetation(data, dataNoise, chunkX, chunkZ);
    }
}
