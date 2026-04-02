package net.pufferlab.primal.world;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.WorldUtils;

public class BlockHolder {

    int x, y, z, meta;
    int chunkX, chunkZ;
    boolean invalid;
    Block block;

    public BlockHolder(NBTTagCompound tag) {
        readFromNBT(tag);
    }

    public BlockHolder(int x, int y, int z, Block block, int meta) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.chunkX = x >> 4;
        this.chunkZ = z >> 4;
        this.block = block;
        this.meta = meta;
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger("x", x);
        tag.setInteger("y", y);
        tag.setInteger("z", z);
        tag.setInteger("blockID", Block.getIdFromBlock(block));
        tag.setInteger("meta", meta);
    }

    public void readFromNBT(NBTTagCompound tag) {
        x = tag.getInteger("x");
        y = tag.getInteger("y");
        z = tag.getInteger("z");
        block = Block.getBlockById(tag.getInteger("blockID"));
        meta = tag.getInteger("meta");
        chunkX = x >> 4;
        chunkZ = z >> 4;
    }

    public boolean invalid() {
        return this.invalid;
    }

    public void invalidate() {
        this.invalid = true;
    }

    public boolean place(World world) {
        if (!world.getChunkProvider()
            .chunkExists(chunkX, chunkZ)) return false;
        if (this.invalid()) return false;
        WorldUtils.setBlock(world, this.x, this.y, this.z, this.block, this.meta);
        return true;
    }

    @Override
    public String toString() {
        return "BlockHolder{" + "x="
            + x
            + ", y="
            + y
            + ", z="
            + z
            + ", meta="
            + meta
            + ", chunkX="
            + chunkX
            + ", chunkZ="
            + chunkZ
            + ", invalid="
            + invalid
            + ", block="
            + block.getLocalizedName()
            + '}';
    }
}
