package net.pufferlab.primal.world.scheduling;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.pufferlab.primal.utils.HashUtils;
import net.pufferlab.primal.utils.NBTType;

public class ChunkData {

    public boolean updateSkylight;
    public Block[] blocks = new Block[65536];
    public byte[] metas = new byte[65536];
    public NBTTagCompound[] nbt;
    public int chunkX;
    public int chunkZ;

    public ChunkData(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public void setBlock(int x, int y, int z, Block block, int meta) {
        if (x < 0 || x >= 16) return;
        if (y < 0 || y >= 256) return;
        if (z < 0 || z >= 16) return;
        int index = HashUtils.packChunkBlockCoord(x, y, z);

        blocks[index] = block;
        metas[index] = (byte) meta;
    }

    public void setBlock(int x, int y, int z, Block block, int meta, NBTTagCompound tag) {
        if (x < 0 || x >= 16) return;
        if (y < 0 || y >= 256) return;
        if (z < 0 || z >= 16) return;
        int index = HashUtils.packChunkBlockCoord(x, y, z);

        blocks[index] = block;
        metas[index] = (byte) meta;
        if (nbt == null) nbt = new NBTTagCompound[65536];

        nbt[index] = tag;
    }

    public Block getBlock(int x, int y, int z) {
        int index = HashUtils.packChunkBlockCoord(x, y, z);
        Block block = blocks[index];
        if (block == null) {
            block = Blocks.air;
        }
        return block;
    }

    public int getBlockMetadata(int x, int y, int z) {
        int index = HashUtils.packChunkBlockCoord(x, y, z);
        return metas[index];
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setBoolean("updateLight", updateSkylight);
        tag.setInteger("x", chunkX);
        tag.setInteger("z", chunkZ);
        int[] blocksInt = new int[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            blocksInt[i] = Block.getIdFromBlock(blocks[i]);
        }
        tag.setIntArray("blocks", blocksInt);
        tag.setByteArray("metas", metas);
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < nbt.length; i++) {
            NBTTagCompound tagNBT = nbt[i];

            if (tagNBT != null) {
                NBTTagCompound entry = new NBTTagCompound();
                entry.setInteger("index", i);
                entry.setTag("nbt", tagNBT);

                list.appendTag(entry);
            }
        }

        tag.setTag("nbts", list);
    }

    public void readFromNBT(NBTTagCompound tag) {
        this.updateSkylight = tag.getBoolean("updateLight");
        this.chunkX = tag.getInteger("x");
        this.chunkZ = tag.getInteger("z");
        int[] array = tag.getIntArray("blocks");
        for (int i = 0; i < array.length; i++) {
            blocks[i] = Block.getBlockById(array[i]);
        }
        this.metas = tag.getByteArray("metas");
        NBTTagList list = tag.getTagList("nbts", NBTType.TagCompound);

        if (list != null && list.tagCount() > 0) {
            this.nbt = new NBTTagCompound[65536];
        }
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound entry = list.getCompoundTagAt(i);

            int index = entry.getInteger("index");
            nbt[index] = entry.getCompoundTag("nbt");
        }
    }
}
