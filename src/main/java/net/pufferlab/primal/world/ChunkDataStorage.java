package net.pufferlab.primal.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.NBTType;
import net.pufferlab.primal.world.terrafirma.ChunkDataTF;

public class ChunkDataStorage {

    public static final String chunkDataName = Primal.MODID + "ChunkData";

    public List<ChunkSavedData> loadedDataList = new ArrayList<>();
    public Map<String, ChunkSavedData> loadedDataMap = new HashMap<>();

    public static final Map<String, Class<? extends ChunkSavedData>> worldClassMap = new HashMap<>();

    public int x;
    public int z;

    static {
        worldClassMap.put(ChunkDataTF.name, ChunkDataTF.class);
    }

    public ChunkDataStorage(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList tagList = nbt.getTagList(chunkDataName, NBTType.TagCompound);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            if (tag != null) {
                String name = tag.getString("name");
                NBTTagCompound chunkNBT = tag.getCompoundTag("nbt");
                try {
                    Class cls = worldClassMap.get(name);

                    ChunkSavedData savedData = (ChunkSavedData) cls.getConstructor(new Class[] { String.class })
                        .newInstance(new Object[] { name });

                    savedData.readFromNBT(chunkNBT);

                    loadedDataMap.put(name, savedData);
                    loadedDataList.add(savedData);
                } catch (Exception exception) {
                    throw new RuntimeException("Failed to instantiate " + name.toString(), exception);
                }
            }
        }
    };

    public ChunkSavedData loadData(String str) {
        return loadedDataMap.get(str);
    }

    public void setData(String p_75745_1_, ChunkSavedData p_75745_2_) {
        if (p_75745_2_ == null) {
            throw new RuntimeException("Can\'t set null data");
        } else {
            if (this.loadedDataMap.containsKey(p_75745_1_)) {
                this.loadedDataList.remove(this.loadedDataMap.remove(p_75745_1_));
            }

            this.loadedDataMap.put(p_75745_1_, p_75745_2_);
            this.loadedDataList.add(p_75745_2_);
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList tagList = new NBTTagList();
        for (ChunkSavedData chunkData : loadedDataList) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("name", chunkData.mapName);

            NBTTagCompound chunkNBT = new NBTTagCompound();
            chunkData.writeToNBT(chunkNBT);
            tag.setTag("nbt", chunkNBT);
            tagList.appendTag(tag);
        }
        nbt.setTag(chunkDataName, tagList);
    };
}
