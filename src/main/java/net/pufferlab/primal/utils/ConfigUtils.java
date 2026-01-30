package net.pufferlab.primal.utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Utils;

public class ConfigUtils {

    private static Map<String, Integer> meltingMetalMap;

    public static String[] getDefaultMetalMelting(MetalType[] metals) {
        String[] metalMelting = new String[metals.length];
        for (int i = 0; i < metalMelting.length; i++) {
            metalMelting[i] = metals[i].name + "=" + metals[i].meltingTemperature;
        }
        return metalMelting;
    }

    public static int getMetalMelting(MetalType metalType) {
        String metal = metalType.name;
        if (meltingMetalMap == null) {
            meltingMetalMap = new HashMap<>();
            String[] priorityOverride = Config.metalMelting.getStringList();
            for (String s : priorityOverride) {
                String[] spl = s.split("=");
                if (spl.length == 2) {
                    String ore = spl[0];
                    if (Integer.parseInt(spl[1]) != 0) {
                        int temp = Integer.parseInt(spl[1]);
                        meltingMetalMap.put(ore, temp);
                    }
                }
            }
        }
        return meltingMetalMap.get(metal);
    }

    private static Map<String, Fluid> liquidMetalMap;

    public static String[] getDefaultMetalFluid(MetalType[] metals) {
        String[] metalFluid = new String[metals.length];
        for (int i = 0; i < metalFluid.length; i++) {
            metalFluid[i] = metals[i].name + "=" + metals[i].fluidName;
        }
        return metalFluid;
    }

    public static Fluid getMetalFluid(MetalType metalType) {
        String metal = metalType.name;
        if (liquidMetalMap == null) {
            liquidMetalMap = new HashMap<>();
            String[] priorityOverride = Config.metalLiquids.getStringList();
            for (String s : priorityOverride) {
                String[] spl = s.split("=");
                if (spl.length == 2) {
                    String ore = spl[0];
                    if (Utils.getFluid(spl[1], 1) != null) {
                        Fluid item = Utils.getFluid(spl[1], 1)
                            .getFluid();
                        liquidMetalMap.put(ore, item);
                    }
                }
            }
        }
        return liquidMetalMap.get(metal);
    }

    public static Map<String, Integer> veinHeightMinMap;
    public static Map<String, Integer> veinHeightMaxMap;

    public static String[] getDefaultHeight(VeinType[] veinTypes) {
        String[] list = new String[veinTypes.length];
        for (int i = 0; i < veinTypes.length; i++) {
            list[i] = veinTypes[i].name + "=" + veinTypes[i].minY + "-" + veinTypes[i].maxY;
        }
        return list;
    }

    public static void genVeinHeightMap() {
        veinHeightMinMap = new HashMap<>();
        veinHeightMaxMap = new HashMap<>();
        String[] priorityOverride = Config.oreVeinsHeight.getStringList();
        for (String s : priorityOverride) {
            String[] spl = s.split("=");
            if (spl.length == 2) {
                String ore = spl[0];
                String[] hs = spl[1].split("-");
                int min = Integer.parseInt(hs[0]);
                int max = Integer.parseInt(hs[1]);
                veinHeightMinMap.put(ore, min);
                veinHeightMaxMap.put(ore, max);
            }
        }
    }

    public static int getVeinHeightMin(VeinType veinType) {
        String vein = veinType.name;
        if (veinHeightMinMap == null) {
            genVeinHeightMap();
        }
        return veinHeightMinMap.get(vein);
    }

    public static int getVeinHeightMax(VeinType veinType) {
        String vein = veinType.name;
        if (veinHeightMaxMap == null) {
            genVeinHeightMap();
        }
        return veinHeightMaxMap.get(vein);
    }

    public static Map<String, Integer> veinSizeMinMap;
    public static Map<String, Integer> veinSizeMaxMap;

    public static String[] getDefaultSize(VeinType[] veinTypes) {
        String[] list = new String[veinTypes.length];
        for (int i = 0; i < veinTypes.length; i++) {
            list[i] = veinTypes[i].name + "=" + veinTypes[i].sizeMin + "-" + veinTypes[i].sizeMax;
        }
        return list;
    }

    public static void genVeinSizeMap() {
        veinSizeMinMap = new HashMap<>();
        veinSizeMaxMap = new HashMap<>();
        String[] priorityOverride = Config.oreVeinsSize.getStringList();
        for (String s : priorityOverride) {
            String[] spl = s.split("=");
            if (spl.length == 2) {
                String ore = spl[0];
                String[] hs = spl[1].split("-");
                int min = Integer.parseInt(hs[0]);
                int max = Integer.parseInt(hs[1]);
                veinSizeMinMap.put(ore, min);
                veinSizeMaxMap.put(ore, max);
            }
        }
    }

    public static int getVeinSizeMin(VeinType veinType) {
        String vein = veinType.name;
        if (veinSizeMinMap == null) {
            genVeinSizeMap();
        }
        return veinSizeMinMap.get(vein);
    }

    public static int getVeinSizeMax(VeinType veinType) {
        String vein = veinType.name;
        if (veinSizeMaxMap == null) {
            genVeinSizeMap();
        }
        return veinSizeMaxMap.get(vein);
    }
}
