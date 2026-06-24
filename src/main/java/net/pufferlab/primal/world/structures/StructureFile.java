package net.pufferlab.primal.world.structures;

import java.io.IOException;
import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.*;

public class StructureFile {

    public String name;
    public Set<NBTTagCompound> list;
    public NBTTagCompound currentNBT;

    public StructureFile(String name) {
        this.name = name;
        this.list = new HashSet<>();
    }

    public void addBlockInfo(NBTTagCompound tag) {
        this.list.add(tag);
    }

    public NBTTagCompound getNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("name", this.name);
        NBTTagList tagList = new NBTTagList();
        for (NBTTagCompound tag : this.list) {
            tagList.appendTag(tag);
        }
        nbt.setTag("blocks", tagList);
        return nbt;
    }

    public void saveFile() {
        if (currentNBT == null) {
            currentNBT = getNBT();
            try {
                IOUtils.writeNBTFile(IOUtils.createStructureFile(this.name, "nbt"), currentNBT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public NBTTagCompound loadFile() {
        if (currentNBT == null) {
            try {
                currentNBT = IOUtils.readNBTFile(IOUtils.createStructureFile(this.name, "nbt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return currentNBT;
    }

    public static void saveStructure(String name, int x1, int y1, int z1, int x2, int y2, int z2, World world) {
        StructureFile file = new StructureFile(name);

        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);
        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);
        int maxZ = Math.max(z1, z2);

        int middleX = (minX + maxX) / 2;
        int middleY = (minY + maxY) / 2;
        int middleZ = (minZ + maxZ) / 2;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlock(x, y, z);
                    int meta = world.getBlockMetadata(x, y, z);
                    TileEntity te = world.getTileEntity(x, y, z);
                    NBTTagCompound nbt = null;
                    if (te != null) {
                        nbt = new NBTTagCompound();
                        te.writeToNBT(nbt);
                    }
                    NBTTagCompound blockInfo = getBlockHash(block, meta, nbt);
                    addBlockCoord(blockInfo, x - middleX, y - middleY, z - middleZ);
                }
            }
        }

        syncNBT(file);
        file.saveFile();
    }

    public static void loadStructure(String name, int x, int y, int z, World world) {
        StructureFile file = new StructureFile(name);
        NBTTagCompound tag = file.loadFile();
        NBTTagList blocks = tag.getTagList("blocks", NBTType.TagCompound);
        for (int i = 0; i < blocks.tagCount(); i++) {
            NBTTagCompound blockInfo = blocks.getCompoundTagAt(i);
            Block block = BlockUtils.getBlockFromName(blockInfo.getString("block"));
            int meta = blockInfo.getInteger("meta");
            NBTTagCompound nbt = blockInfo.getCompoundTag("nbt");
            byte[] coords = blockInfo.getByteArray("coords");
            for (int j = 0; j < coords.length; j += 3) {
                int x0 = x + coords[j];
                int y0 = y + coords[j + 1];
                int z0 = z + coords[j + 2];
                world.setBlock(x0, y0, z0, block, meta, 2);
                TileEntity te = world.getTileEntity(x0, y0, z0);
                if (te != null) {
                    nbt.setInteger("x", x0);
                    nbt.setInteger("y", y0);
                    nbt.setInteger("z", z0);
                    te.readFromNBT(nbt);
                    te.markDirty();
                }
            }
        }
    }

    public static final Map<String, NBTTagCompound> nbtCache = new HashMap<>();

    public static void syncNBT(StructureFile file) {
        for (NBTTagCompound nbt : nbtCache.values()) {
            file.addBlockInfo(nbt);
        }
    }

    public static NBTTagCompound getBlockHash(Block block, int meta, NBTTagCompound tag) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("block", BlockUtils.getName(block));
        nbt.setInteger("meta", meta);
        if (tag != null) {
            tag.removeTag("x");
            tag.removeTag("y");
            tag.removeTag("z");
            tag.removeTag("xCached");
            tag.removeTag("yCached");
            tag.removeTag("zCached");
            nbt.setTag("nbt", tag);
        }
        String string = nbt.toString();
        NBTTagCompound nbtOld = nbtCache.get(string);
        if (nbtOld != null) {
            return nbtOld;
        }
        nbtCache.put(string, nbt);
        return nbt;
    }

    public static void addBlockCoord(NBTTagCompound tag, int x, int y, int z) {
        byte[] tagList;
        if (tag.hasKey("coords")) {
            tagList = tag.getByteArray("coords");
        } else {
            tagList = new byte[0];
        }
        tagList = appendXYZ(tagList, x, y, z);
        tag.setByteArray("coords", tagList);
    }

    public static byte[] appendXYZ(byte[] array, int x, int y, int z) {
        byte[] result = Arrays.copyOf(array, array.length + 3);
        result[array.length] = (byte) x;
        result[array.length + 1] = (byte) y;
        result[array.length + 2] = (byte) z;
        return result;
    }
}
