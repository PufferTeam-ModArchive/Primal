package net.pufferlab.primal.world.terrafirma.gen.filler;

import net.pufferlab.primal.world.terrafirma.ChunkBlockData;
import net.pufferlab.primal.world.terrafirma.ChunkNoiseData;

public interface IBlockLayer {

    public void generate(ChunkBlockData data, ChunkNoiseData dataNoise, int chunkX, int chunkZ);
}
