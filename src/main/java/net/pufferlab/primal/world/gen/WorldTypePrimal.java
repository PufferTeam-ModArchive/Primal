package net.pufferlab.primal.world.gen;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldTypePrimal extends WorldType {

    public WorldTypePrimal() {
        super("primal");
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        return new ChunkProviderPrimal(world);
    }
}
