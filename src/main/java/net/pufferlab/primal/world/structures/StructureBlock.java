package net.pufferlab.primal.world.structures;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.utils.BlockUtils;

public class StructureBlock {

    public Block block;
    public int metadata;
    public NBTTagCompound tag;
    public byte[] coords;

    public StructureBlock(Block block, int metadata, NBTTagCompound tag) {
        this.block = block;
        this.metadata = metadata;
        this.tag = tag;
    }

    public StructureBlock(NBTTagCompound nbt) {
        readFromNBT(nbt);
    }

    public StructureBlock(StructureBlock block) {
        this.block = block.block;
        this.metadata = block.metadata;
        this.tag = block.tag;
        this.coords = block.coords;
    }

    public void addCoord(int x, int y, int z) {
        if (coords == null) {
            coords = new byte[0];
        }
        coords = appendXYZ(coords, x, y, z);
    }

    public static byte[] appendXYZ(byte[] array, int x, int y, int z) {
        byte[] result = Arrays.copyOf(array, array.length + 3);
        result[array.length] = (byte) x;
        result[array.length + 1] = (byte) y;
        result[array.length + 2] = (byte) z;
        return result;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setString("block", BlockUtils.getNameFromBlock(this.block));
        nbt.setInteger("meta", this.metadata);
        if (tag != null) {
            nbt.setTag("nbt", tag);
        }
        nbt.setByteArray("coords", coords);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.block = BlockUtils.getBlockFromName(nbt.getString("block"));
        this.metadata = nbt.getInteger("meta");
        if (nbt.hasKey("nbt")) {
            this.tag = nbt.getCompoundTag("nbt");
        }
        this.coords = nbt.getByteArray("coords");
    }

    @Override
    public String toString() {
        return BlockUtils.getNameFromBlock(this.block, this.metadata, this.tag);
    }

}
