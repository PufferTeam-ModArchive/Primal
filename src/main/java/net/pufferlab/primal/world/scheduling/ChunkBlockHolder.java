package net.pufferlab.primal.world.scheduling;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.pufferlab.primal.world.ChunkBlockStorage;

public class ChunkBlockHolder extends ChunkBlockStorage {

    public boolean updateSkylight;
    public int chunkX;
    public int chunkZ;

    public ChunkBlockHolder(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("updateSkylight", updateSkylight);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        updateSkylight = compound.getBoolean("updateSkylight");
    }

    @Override
    public void placeToChunk(Chunk chunk) {
        super.placeToChunk(chunk);
        if (updateSkylight) {
            chunk.generateSkylightMap();
            chunk.resetRelightChecks();
        }
    }
}
