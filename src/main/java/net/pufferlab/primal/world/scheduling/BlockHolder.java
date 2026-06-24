package net.pufferlab.primal.world.scheduling;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.WorldUtils;

public class BlockHolder {

    int x, y, z, meta;
    int chunkX, chunkZ;
    boolean invalid, fastPlace;
    Block block;
    NBTTagCompound nbt;

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

    public BlockHolder(int x, int y, int z, Block block, int meta, NBTTagCompound nbt) {
        this(x, y, z, block, meta);
        this.nbt = nbt;
        this.fastPlace = false;
    }

    public BlockHolder setFastPlace(boolean state) {
        this.fastPlace = state;
        return this;
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger("x", x);
        tag.setInteger("y", y);
        tag.setInteger("z", z);
        tag.setInteger("blockID", Block.getIdFromBlock(block));
        tag.setInteger("meta", meta);
        if (nbt != null) {
            tag.setTag("nbt", nbt);
        }
        tag.setBoolean("fastPlace", fastPlace);
    }

    public void readFromNBT(NBTTagCompound tag) {
        x = tag.getInteger("x");
        y = tag.getInteger("y");
        z = tag.getInteger("z");
        block = Block.getBlockById(tag.getInteger("blockID"));
        meta = tag.getInteger("meta");
        chunkX = x >> 4;
        chunkZ = z >> 4;
        if (tag.hasKey("nbt")) {
            nbt = tag.getCompoundTag("nbt");
        }
        fastPlace = tag.getBoolean("fastPlace");
    }

    public boolean invalid() {
        return this.invalid;
    }

    public void invalidate() {
        this.invalid = true;
    }

    public boolean place(World world) {
        if (this.invalid()) return false;
        if (this.fastPlace) {
            WorldUtils.setBlock(world, this.x, this.y, this.z, this.block, this.meta);
        } else {
            world.setBlock(this.x, this.y, this.z, this.block, this.meta, 2);
            if (this.block.hasTileEntity(this.meta)) {
                TileEntity te = world.getTileEntity(this.x, this.y, this.z);
                if (te != null) {
                    this.nbt.setInteger("x", this.x);
                    this.nbt.setInteger("y", this.y);
                    this.nbt.setInteger("z", this.z);
                    te.readFromNBT(this.nbt);
                    te.markDirty();
                }
            }
        }
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
