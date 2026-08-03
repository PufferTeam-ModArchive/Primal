package net.pufferlab.primal.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.blocks.IPrimalBlock;
import net.pufferlab.primal.utils.HashUtils;
import net.pufferlab.primal.utils.NBTType;
import net.pufferlab.primal.utils.WorldUtils;

public class ChunkBlockStorage {

    public Block[][] blocks = new Block[16][];
    public int[][] metas = new int[16][];
    public NBTTagCompound[][] tags = new NBTTagCompound[16][];

    public void setBlock(int x, int y, int z, Block block, int meta) {
        int yIndex = y >> 4;
        int index = HashUtils.pack3DCoord(x, y & 15, z);

        if (x < 0 || x >= 16) return;
        if (y < Constants.minHeight || y > Constants.maxHeight) return;
        if (z < 0 || z >= 16) return;

        if (blocks[yIndex] == null) {
            blocks[yIndex] = new Block[4096];
        }
        if (metas[yIndex] == null) {
            metas[yIndex] = new int[4096];
        }
        blocks[yIndex][index] = block;
        metas[yIndex][index] = meta;
    }

    public void setBlock(int x, int y, int z, Block block, int meta, NBTTagCompound tag) {
        int yIndex = y >> 4;
        int index = HashUtils.pack3DCoord(x, y & 15, z);

        if (x < 0 || x >= 16) return;
        if (y < Constants.minHeight || y > Constants.maxHeight) return;
        if (z < 0 || z >= 16) return;

        if (blocks[yIndex] == null) {
            blocks[yIndex] = new Block[4096];
        }
        if (metas[yIndex] == null) {
            metas[yIndex] = new int[4096];
        }
        if (tags[yIndex] == null) {
            tags[yIndex] = new NBTTagCompound[4096];
        }
        blocks[yIndex][index] = block;
        metas[yIndex][index] = meta;
        tags[yIndex][index] = tag;
    }

    public Block getBlock(int x, int y, int z) {
        int yIndex = y >> 4;
        int index = HashUtils.pack3DCoord(x, y & 15, z);

        if (x < 0 || x >= 16) return Blocks.air;
        if (y < Constants.minHeight || y > Constants.maxHeight) return Blocks.air;
        if (z < 0 || z >= 16) return Blocks.air;

        if (blocks[yIndex] == null) return Blocks.air;

        return blocks[yIndex][index];
    }

    public int getBlockMetadata(int x, int y, int z) {
        int yIndex = y >> 4;
        int index = HashUtils.pack3DCoord(x, y & 15, z);

        if (x < 0 || x >= 16) return 0;
        if (y < Constants.minHeight || y > Constants.maxHeight) return 0;
        if (z < 0 || z >= 16) return 0;

        if (metas[yIndex] == null) return 0;

        return metas[yIndex][index];
    }

    public void writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < blocks.length; i++) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("y", i);
            if (blocks[i] != null) {
                int[] blocksInt = new int[blocks[i].length];
                for (int j = 0; j < blocks[i].length; j++) {
                    blocksInt[j] = Block.getIdFromBlock(blocks[i][j]);
                }
                tag.setIntArray("blocks", blocksInt);
            }
            if (metas[i] != null) {
                tag.setIntArray("metas", metas[i]);
            }

            list.appendTag(tag);
        }
        compound.setTag("blockdata", list);
    }

    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("blockdata", NBTType.TagCompound);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            int yLevel = tag.getInteger("y");
            int[] blocksInt = tag.getIntArray("blocks");
            if (blocksInt != null && blocksInt.length > 0) {
                Block[] blockArray = new Block[blocksInt.length];
                for (int j = 0; j < blocksInt.length; j++) {
                    blockArray[j] = Block.getBlockById(blocksInt[j]);
                }
                blocks[yLevel] = blockArray;
            }
            int[] metasInt = tag.getIntArray("metas");
            if (metasInt != null) {
                metas[yLevel] = metasInt;
            }
        }
    }

    public void placeToChunk(Chunk chunk) {
        boolean flag = !chunk.worldObj.provider.hasNoSky;
        ExtendedBlockStorage[] storageArrays = chunk.getBlockStorageArray();

        for (int yIndex = 0; yIndex < 16; yIndex++) {
            if (blocks[yIndex] == null || metas[yIndex] == null) {
                continue;
            }

            if (storageArrays[yIndex] == null) {
                storageArrays[yIndex] = new ExtendedBlockStorage(yIndex << 4, flag);
            }

            ExtendedBlockStorage storage = storageArrays[yIndex];

            for (int index = 0; index < blocks[yIndex].length; index++) {
                Block block = blocks[yIndex][index];

                if (block == null || block == Blocks.air) {
                    continue;
                }

                int meta = metas[yIndex][index];

                int x = HashUtils.unpack3DX(index);
                int y = HashUtils.unpack3DY(index);
                int z = HashUtils.unpack3DZ(index);

                if (block instanceof IPrimalBlock primalBlock && primalBlock.shouldReplace()) {
                    Block existingBlock = storage.getBlockByExtId(x, y, z);
                    int existingMeta = storage.getExtBlockMetadata(x, y, z);

                    if (primalBlock.skipBlock(existingBlock, existingMeta)) {
                        continue;
                    }

                    meta = primalBlock.getBlockMetaToPlace(existingBlock, existingMeta);
                }

                storage.func_150818_a(x, y, z, block);
                storage.setExtBlockMetadata(x, y, z, meta);
            }
        }

        chunk.setChunkModified();
    }

    public void placeTileEntity(Chunk chunk) {
        int blockX = chunk.xPosition << 4;
        int blockZ = chunk.zPosition << 4;

        for (int yIndex = 0; yIndex < 16; yIndex++) {
            if (tags[yIndex] == null) {
                continue;
            }

            for (int index = 0; index < tags[yIndex].length; index++) {
                NBTTagCompound tag = tags[yIndex][index];
                if (tag != null) {
                    int x = HashUtils.unpack3DX(index);
                    int y = (yIndex << 4) + HashUtils.unpack3DY(index);
                    int z = HashUtils.unpack3DZ(index);

                    WorldUtils.setTileEntityNBT(chunk.worldObj, x + blockX, y, z + blockZ, tag);
                }

            }
        }
    }
}
