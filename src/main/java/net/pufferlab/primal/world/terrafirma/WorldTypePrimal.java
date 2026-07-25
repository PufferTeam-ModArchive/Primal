package net.pufferlab.primal.world.terrafirma;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldTypePrimal extends WorldType {

    public WorldTypePrimal() {
        super("primal");
    }

    @Override
    public WorldChunkManager getChunkManager(World world) {
        return new ChunkManagerPrimal();
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        return new ChunkProviderPrimal(world);
    }

    @Override
    public float getCloudHeight() {
        return 200.0F;
    }

}
