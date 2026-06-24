package net.pufferlab.primal.world.structures;

import java.io.File;
import java.io.IOException;
import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.utils.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class StructureFile {

    public String name;
    public File file;
    public Set<NBTTagCompound> list;
    public NBTTagCompound currentNBT;

    public StructureFile(String name) {
        this.name = name;
        try {
            this.file = IOUtils.createStructureFile(this.name, "nbt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void setNBT(NBTTagCompound tag) {
        currentNBT = tag;
    }

    public void saveFile() {
        if (currentNBT == null) {
            currentNBT = getNBT();
        }
        IOUtils.writeNBTFile(file, currentNBT);
    }

    public NBTTagCompound loadFile() {
        if (currentNBT == null) {
            currentNBT = IOUtils.readNBTFile(file);
        }
        return currentNBT;
    }

    public static Map<String, StructureFile> cachedStructure = new HashMap<>();

    public static void putStructureFile(String name) {
        cachedStructure.put(name, new StructureFile(name));
    }

    public static void saveStructure(String name, int x1, int y1, int z1, int x2, int y2, int z2, World world) {
        saveStructure(new StructureFile(name), x1, y1, z1, x2, y2, z2, world);
        putStructureFile(name);
    }

    public static void saveStructure(StructureFile file, int x1, int y1, int z1, int x2, int y2, int z2, World world) {
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
                    NBTTagCompound blockInfo = getBlockInfo(block, meta, nbt);
                    addBlockCoord(blockInfo, x - middleX, y - middleY, z - middleZ);
                }
            }
        }

        syncNBT(file);
        rotateStructure(file, world);
        file.saveFile();
    }

    public static StructureFile getStructureFile(String name) {
        StructureFile file = cachedStructure.get(name);
        if (file == null) {
            file = new StructureFile(name);
            cachedStructure.put(name, file);
        }
        return file;
    }

    public static void loadStructure(String name, int x, int y, int z, World world, int facing) {
        StructureFile file = getStructureFile(name);
        loadStructure(file, x, y, z, world, facing);
    }

    public static void loadStructure(StructureFile file, int x, int y, int z, World world, int facing) {
        NBTTagCompound tag = file.loadFile();
        NBTTagList blocks;
        if (facing == 0) {
            blocks = tag.getTagList("blocks", NBTType.TagCompound);
        } else {
            blocks = tag.getTagList("blocks_" + facing, NBTType.TagCompound);
        }
        for (int i = 0; i < blocks.tagCount(); i++) {
            NBTTagCompound blockInfo = blocks.getCompoundTagAt(i);
            Block block = BlockUtils.getBlockFromName(blockInfo.getString("block"));
            int meta = blockInfo.getInteger("meta");
            NBTTagCompound nbt = null;
            if (blockInfo.hasKey("nbt")) {
                nbt = blockInfo.getCompoundTag("nbt");
            }
            byte[] coords = blockInfo.getByteArray("coords");
            for (int j = 0; j < coords.length; j += 3) {
                int x0 = x + coords[j];
                int y0 = y + coords[j + 1];
                int z0 = z + coords[j + 2];
                WorldUtils.setBlockStructure(world, x0, y0, z0, block, meta, nbt);
            }
        }
    }

    public static Matrix4f matrix = new Matrix4f();

    public static void rotateStructure(StructureFile file, World world) {
        NBTTagCompound tag = file.getNBT();
        NBTTagList blocks = tag.getTagList("blocks", NBTType.TagCompound);
        NBTTagList[] blocksRotated = new NBTTagList[3];
        for (int i = 0; i < 3; i++) {
            blocksRotated[i] = new NBTTagList();
        }
        for (int i = 0; i < blocks.tagCount(); i++) {
            NBTTagCompound blockInfo = blocks.getCompoundTagAt(i);
            byte[] coords = blockInfo.getByteArray("coords");
            byte[][] coordsRotated = new byte[3][coords.length];
            for (int j = 0; j < coords.length; j += 3) {
                int x0 = coords[j];
                int y0 = coords[j + 1];
                int z0 = coords[j + 2];
                Vector3f coord = new Vector3f(x0, y0, z0);
                for (int k = 0; k < 3; k++) {
                    coord.set(x0, y0, z0);
                    matrix.identity();
                    int p = k;
                    if (k == 2) {
                        p = 1;
                    }
                    if (k == 1) {
                        p = 2;
                    }
                    for (int l = 0; l < (p + 1); l++) {
                        matrix.rotateY(-(float) Math.PI / 2);
                        matrix.transformPosition(coord);
                        int x = Utils.floor(coord.x);
                        int y = Utils.floor(coord.y);
                        int z = Utils.floor(coord.z);
                        coord.set(x, y, z);
                    }
                    coordsRotated[k][j] = (byte) Utils.floor(coord.x);
                    coordsRotated[k][j + 1] = (byte) Utils.floor(coord.y);
                    coordsRotated[k][j + 2] = (byte) Utils.floor(coord.z);
                }
            }
            for (int k = 0; k < 3; k++) {
                NBTTagCompound rotatedBlockInfo = rotateBlockInfo(world, blockInfo, coordsRotated[k], k + 1);
                blocksRotated[k].appendTag(rotatedBlockInfo);
            }
        }
        for (int k = 0; k < 3; k++) {
            tag.setTag("blocks_" + (k + 1), blocksRotated[k]);
        }
        file.setNBT(tag);
    }

    public static int coordX = 1;
    public static int coordY = 1;
    public static int coordZ = 1;

    public static NBTTagCompound rotateBlockInfo(World world, NBTTagCompound blockInfo0, byte[] newCoords,
        int rotation) {
        while (world.getBlock(coordX, coordY, coordZ)
            .hasTileEntity(world.getBlockMetadata(coordX, coordY, coordZ))) {
            coordX++;
        }
        Block blockBackup = world.getBlock(coordX, coordY, coordZ);
        int metaBackup = world.getBlockMetadata(coordX, coordY, coordZ);

        NBTTagCompound blockInfo = (NBTTagCompound) blockInfo0.copy();
        Block block = BlockUtils.getBlockFromName(blockInfo.getString("block"));
        int meta = blockInfo.getInteger("meta");
        NBTTagCompound nbt = null;
        if (blockInfo.hasKey("nbt")) {
            nbt = blockInfo.getCompoundTag("nbt");
        }
        world.setBlock(coordX, coordY, coordZ, block, meta, 2);
        TileEntity te = world.getTileEntity(coordX, coordY, coordZ);
        if (te != null) {
            if (nbt != null) {
                NBTTagCompound nbt2 = (NBTTagCompound) nbt.copy();
                nbt2.setInteger("x", coordX);
                nbt2.setInteger("y", coordY);
                nbt2.setInteger("z", coordZ);
                te.readFromNBT(nbt2);
                te.markDirty();
            }
        }

        for (int i = 0; i < rotation; i++) {
            block.rotateBlock(world, coordX, coordY, coordZ, ForgeDirection.UP);
        }

        blockInfo.setInteger("meta", world.getBlockMetadata(coordX, coordY, coordZ));
        te = world.getTileEntity(coordX, coordY, coordZ);
        if (te != null) {
            NBTTagCompound newTag = new NBTTagCompound();
            te.writeToNBT(newTag);
            newTag.removeTag("x");
            newTag.removeTag("y");
            newTag.removeTag("z");
            newTag.removeTag("xCached");
            newTag.removeTag("yCached");
            newTag.removeTag("zCached");
            blockInfo.setTag("nbt", newTag);
        }

        blockInfo.setByteArray("coords", newCoords);

        world.setBlock(coordX, coordY, coordZ, blockBackup, metaBackup, 2);

        return blockInfo;
    }

    public static final Map<String, NBTTagCompound> nbtCache = new HashMap<>();

    public static void syncNBT(StructureFile file) {
        for (NBTTagCompound nbt : nbtCache.values()) {
            file.addBlockInfo(nbt);
        }
        nbtCache.clear();
    }

    public static NBTTagCompound getBlockInfo(Block block, int meta, NBTTagCompound tag) {
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
