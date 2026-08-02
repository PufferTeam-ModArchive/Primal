package net.pufferlab.primal.utils;

import java.util.*;

import net.minecraft.world.World;
import net.pufferlab.primal.Config;

import gnu.trove.map.TFloatObjectMap;
import gnu.trove.map.hash.TFloatObjectHashMap;

public class SoilType implements IPrimalType {

    public String name;
    public float humidity;
    public float suitability;
    public float humidityTF;

    public SoilType(String name, float humidity, float suitability) {
        this.name = name;
        this.humidity = humidity;
        this.suitability = suitability;
        this.humidityTF = humidity;
    }

    @Override
    public String getName() {
        return name;
    }

    public static String[] getNames(SoilType[] stones) {
        String[] names = new String[stones.length];
        for (int i = 0; i < stones.length; i++) {
            names[i] = stones[i].name;
        }
        return names;
    }

    @Override
    public float getFloat(Config.Value.Type index) {
        if (index == Config.Value.Type.humidity) {
            return humidity;
        }
        if (index == Config.Value.Type.humidityTF) {
            return humidityTF;
        }
        return 0.0F;
    }

    @Override
    public void setFloat(Config.Value.Type index, float primary) {
        if (index == Config.Value.Type.humidity) {
            this.humidity = primary;
        }
        if (index == Config.Value.Type.humidityTF) {
            this.humidityTF = primary;
        }
    }

    public float getHumidity(boolean isTerraFirma) {
        if (isTerraFirma) {
            return humidityTF;
        } else {
            return humidity;
        }
    }

    public int blockMeta;

    public static int getMeta(SoilType type) {
        return type.blockMeta;
    }

    public static SoilType pickOneSoilType(World world, float humidity) {
        if (WorldUtils.isTerraFirma(world)) {
            return cacheTF.pickOneSoilType(humidity);
        } else {
            return cache.pickOneSoilType(humidity);
        }
    }

    public static final HumidityCache cache = new HumidityCache();
    public static final HumidityCache cacheTF = new HumidityCache();

    public static void genHumidityCache(SoilType[] soilTypes) {
        cache.genHumidityCache(false, soilTypes);
        cacheTF.genHumidityCache(true, soilTypes);

        for (int i = 0; i < soilTypes.length; i++) {
            soilTypes[i].blockMeta = i;
        }
    }

    public static class HumidityCache {

        public float[] sortedFloats;
        public final TFloatObjectMap<SoilType> humidityRangeCache = new TFloatObjectHashMap<>();

        public SoilType pickOneSoilType(float humidity) {
            SoilType lastSoil = null;
            for (int i = 0; i < sortedFloats.length; i++) {
                float humidityFloat = sortedFloats[i];
                if (humidity >= humidityFloat) {
                    lastSoil = humidityRangeCache.get(humidityFloat);
                }
            }
            return lastSoil != null ? lastSoil : humidityRangeCache.get(sortedFloats[0]);
        }

        public void genHumidityCache(boolean isTerraFirma, SoilType[] soilTypes) {
            List<Float> list = new ArrayList<>();
            for (SoilType soilType : soilTypes) {
                float humidity = soilType.getHumidity(isTerraFirma);
                list.add(humidity);
                humidityRangeCache.put(humidity, soilType);
            }
            Collections.sort(list);
            sortedFloats = new float[list.size()];
            for (int i = 0; i < list.size(); i++) {
                sortedFloats[i] = list.get(i);
            }
        }
    }
}
