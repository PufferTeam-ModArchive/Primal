package net.pufferlab.primal.utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.biome.BiomeGenBase;
import net.pufferlab.primal.Constants;

public class SoilType {

    public String name;

    public SoilType(String name) {
        this.name = name;
    }

    public static String[] getNames(SoilType[] stones) {
        String[] names = new String[stones.length];
        for (int i = 0; i < stones.length; i++) {
            names[i] = stones[i].name;
        }
        return names;
    }

    public static Map<SoilType, Integer> metaList;

    public static int getMeta(SoilType[] stoneTypes, SoilType type) {
        if (metaList == null) {
            metaList = new HashMap<>();
            for (int i = 0; i < stoneTypes.length; i++) {
                metaList.put(stoneTypes[i], i);
            }
        }
        if (metaList.containsKey(type)) {
            return metaList.get(type);
        }
        return 0;
    }

    public static SoilType pickOneSoilType(BiomeGenBase biome, int x, int y, int z) {
        float temp = biome.getFloatTemperature(x, y, z);
        float rain = biome.rainfall;

        if (temp < 0.15F) {
            // Very cold → Peaty
            return Constants.peaty;
        } else if (temp < 0.3F) {
            // Cold → Chalky
            return Constants.sandy_loam;
        } else if (temp < 0.5F) {
            // Mild → Loamy
            return Constants.loamy;
        } else if (temp < 0.7F && rain >= 0.4F) {
            // Moderate temp + moderate rain → Silty
            return Constants.silty;
        } else if (temp >= 0.7F && rain < 0.3F) {
            // Hot + dry → Sandy
            return Constants.sandy;
        } else {
            // Hot + wet → Clay
            return Constants.silty_loam;
        }
    }
}
