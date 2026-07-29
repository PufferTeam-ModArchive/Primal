package net.pufferlab.primal.world;

import net.minecraft.nbt.NBTTagCompound;

public abstract class ChunkSavedData {

    public final String mapName;
    private boolean dirty;

    public ChunkSavedData(String p_i2141_1_) {
        this.mapName = p_i2141_1_;
    }

    public abstract void readFromNBT(NBTTagCompound nbt);

    public abstract void writeToNBT(NBTTagCompound nbt);

    public void markDirty() {
        this.setDirty(true);
    }

    public void setDirty(boolean p_76186_1_) {
        this.dirty = p_76186_1_;
    }

    public boolean isDirty() {
        return this.dirty;
    }

}
