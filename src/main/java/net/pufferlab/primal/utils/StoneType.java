package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class StoneType implements IPrimalType {

    public StoneCategory category;
    public String name;
    int minHeight;
    int maxHeight;
    int minHeightTF;
    int maxHeightTF;
    int weight;

    public StoneType(StoneCategory category, String name) {
        this.category = category;
        this.name = name;
    }

    public StoneType(StoneCategory category, String name, int minHeight, int maxHeight, int weight) {
        this(category, name);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minHeightTF = Utils.floor(minHeight * Constants.heightMultiplier);
        this.maxHeightTF = Utils.floor(maxHeight * Constants.heightMultiplier);
        this.weight = weight;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMinInt(Config.Value.Type index) {
        if (index == Config.Value.Type.height) {
            return minHeight;
        }
        if (index == Config.Value.Type.heightTF) {
            return minHeightTF;
        }
        return 0;
    }

    @Override
    public int getMaxInt(Config.Value.Type index) {
        if (index == Config.Value.Type.height) {
            return maxHeight;
        }
        if (index == Config.Value.Type.heightTF) {
            return maxHeightTF;
        }
        return 0;
    }

    @Override
    public void setMinInt(Config.Value.Type index, int min) {
        if (index == Config.Value.Type.height) {
            this.minHeight = min;
        }
        if (index == Config.Value.Type.heightTF) {
            this.minHeightTF = min;
        }
    }

    @Override
    public void setMaxInt(Config.Value.Type index, int max) {
        if (index == Config.Value.Type.height) {
            this.maxHeight = max;
        }
        if (index == Config.Value.Type.heightTF) {
            this.maxHeightTF = max;
        }
    }

    public boolean inRange(boolean isTerraFirma, int height) {
        if (isTerraFirma) {
            if (height < maxHeightTF && height > minHeightTF) {
                return true;
            }
        } else {
            if (height < maxHeight && height > minHeight) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(StoneType type) {
        if (type.name.equals(this.name)) {
            return true;
        }
        return false;
    }

    public static String[] getNames(StoneType[] stones) {
        String[] names = new String[stones.length];
        for (int i = 0; i < stones.length; i++) {
            names[i] = stones[i].name;
        }
        return names;
    }

    public static String[] getTextures(StoneType[] stones) {
        String[] names = new String[stones.length];
        for (int i = 0; i < stones.length; i++) {
            names[i] = Primal.MODID + ":" + stones[i].name + "_raw";
        }
        return names;
    }

    public static StoneType pickOneStoneType(World world, int height, int index) {
        if (WorldUtils.isTerraFirma(world)) {
            return cacheTF.pickOneStoneType(height, index);
        } else {
            return cache.pickOneStoneType(height, index);
        }
    }

    public static final LayerCache cache = new LayerCache();
    public static final LayerCache cacheTF = new LayerCache();

    public static void genLayerCache(StoneType[] stoneTypes) {
        cache.genLayerCache(false, stoneTypes);
        cacheTF.genLayerCache(true, stoneTypes);
    }

    public static class LayerCache {

        public final TIntObjectMap<StoneType[]> stoneLayerCache = new TIntObjectHashMap<>();

        public StoneType pickOneStoneType(int height, int index) {
            StoneType current = pickRaw(height, index);
            StoneType below = pickRaw(height - 1, index);
            StoneType above = pickRaw(height + 1, index);

            if (current != below && current != above) {
                return below;
            }

            return current;
        }

        private StoneType pickRaw(int height, int index) {
            StoneType[] cache = stoneLayerCache.get(height);

            if (cache == null || cache.length == 0) return Constants.dacite;

            int indexM = Math.floorMod(index, cache.length);
            return cache[indexM];
        }

        public void genLayerCache(boolean isTerraFirma, StoneType[] stoneTypes) {
            for (int i = Constants.minHeight; i < Constants.maxHeight; i++) {
                List<StoneType> cacheStone = new ArrayList<>(stoneTypes.length);

                for (StoneType stone : stoneTypes) {
                    if (stone.inRange(isTerraFirma, i)) {
                        cacheStone.add(stone);
                    }
                }

                if (!cacheStone.isEmpty()) {
                    stoneLayerCache.put(i, cacheStone.toArray(new StoneType[0]));
                }
            }
        }
    }

    public static TObjectIntMap<StoneType> metaList;

    public static int getMeta(StoneType[] stoneTypes, StoneType type) {
        if (metaList == null) {
            metaList = new TObjectIntHashMap<>();
            for (int i = 0; i < stoneTypes.length; i++) {
                metaList.put(stoneTypes[i], i);
            }
        }
        if (metaList.containsKey(type)) {
            return metaList.get(type);
        }
        return 0;
    }

    public static final TIntObjectMap<StoneType> typeMap = new TIntObjectHashMap<>();

    public static void registerStone(StoneType[] stoneTypes, Block block) {
        for (int i = 0; i < stoneTypes.length; i++) {
            typeMap.put(Utils.getBlockKey(block, i), stoneTypes[i]);
        }
    }

    public static StoneType getStoneType(Block block, int meta) {
        int id = Utils.getBlockKey(block, meta);
        if (typeMap.containsKey(id)) {
            return typeMap.get(id);
        }
        return null;
    }
}
