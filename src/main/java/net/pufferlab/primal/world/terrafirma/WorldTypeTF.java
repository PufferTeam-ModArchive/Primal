package net.pufferlab.primal.world.terrafirma;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.pufferlab.primal.Primal;

public class WorldTypeTF extends WorldType {

    public WorldTypeTF() {
        super("terrafirma");
        setNotificationData();
    }

    @Override
    public String getTranslateName() {
        return "generator." + Primal.MODID + "." + this.getWorldTypeName() + ".name";
    }

    @Override
    public String func_151359_c() {
        return "generator." + Primal.MODID + "." + this.getWorldTypeName() + ".info";
    }

    @Override
    public WorldChunkManager getChunkManager(World world) {
        return new ChunkManagerTF(world);
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        return new ChunkProviderTF(world);
    }

    @Override
    public float getCloudHeight() {
        return 200.0F;
    }

}
