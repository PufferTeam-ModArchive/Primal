package net.pufferlab.primal.world.terrafirma.gen.noise;

import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;

public interface INoiseLayer {

    public void generate(ChunkNoiseData data, int chunkX, int chunkZ);
}
