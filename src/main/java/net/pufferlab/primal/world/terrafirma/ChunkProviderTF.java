package net.pufferlab.primal.world.terrafirma;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.pufferlab.primal.world.terrafirma.gen.region.RegionProvider;

public class ChunkProviderTF implements IChunkProvider {

    private World worldObj;

    public RegionProvider regionProvider;

    public ChunkProviderTF(World world) {
        this.worldObj = world;
        this.regionProvider = new RegionProvider();
    }

    @Override
    public boolean chunkExists(int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ) {
        this.regionProvider.tickTasks(worldObj);

        this.regionProvider.generateRegion(worldObj, chunkX, chunkZ);

        ChunkBlockData data = this.regionProvider.getChunkBlockData(chunkX, chunkZ);

        Block[] ablock = new Block[65536];
        byte[] abyte = new byte[65536];

        Chunk chunk = new Chunk(this.worldObj, ablock, abyte, chunkX, chunkZ);

        if (data != null) {
            data.placeToChunk(chunk);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public Chunk loadChunk(int chunkX, int chunkZ) {
        return this.provideChunk(chunkX, chunkZ);
    }

    @Override
    public void populate(IChunkProvider provider, int chunkX, int chunkZ) {
        Chunk chunk = worldObj.getChunkFromChunkCoords(chunkX, chunkZ);

        this.regionProvider.tickPendingBlocks(worldObj, chunkX, chunkZ);

        chunk.setChunkModified();
    }

    @Override
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
        return true;
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "PrimalChunkGenerator";
    }

    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_,
        int p_73155_3_, int p_73155_4_) {
        return null;
    }

    @Override
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_,
        int p_147416_5_) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int p_82695_1_, int p_82695_2_) {

    }

    @Override
    public void saveExtraData() {

    }
}
