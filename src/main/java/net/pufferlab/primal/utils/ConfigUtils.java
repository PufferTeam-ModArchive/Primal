package net.pufferlab.primal.utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.AnvilAction;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class ConfigUtils {

    public static void initConfigMap() {
        genMetalMelting();
        genMetalFluid();
        genVeinHeightMap();
        genStrataHeightMap();
        genVeinSizeMap();
        genAnvilStep();
    }

    private static final TObjectIntMap<String> meltingMetalMap = new TObjectIntHashMap<>();

    public static String[] getDefaultMetalMelting(MetalType[] metals) {
        String[] metalMelting = new String[metals.length];
        for (int i = 0; i < metalMelting.length; i++) {
            metalMelting[i] = metals[i].name + "=" + metals[i].meltingTemperature;
        }
        return metalMelting;
    }

    public static void genMetalMelting() {
        String[] priorityOverride = Config.metalMelting.getStringList();
        for (String s : priorityOverride) {
            String[] spl = s.split("=");
            if (spl.length == 2) {
                String ore = spl[0];
                try {
                    if (Integer.parseInt(spl[1]) != 0) {
                        int temp = Integer.parseInt(spl[1]);
                        meltingMetalMap.put(ore, temp);
                    }
                } catch (Exception e) {
                    throwInvalidConfig(Config.metalMelting);
                }
            }
        }
    }

    public static int getMetalMelting(MetalType metalType) {
        String metal = metalType.name;
        return meltingMetalMap.get(metal);
    }

    private static final Map<String, Fluid> liquidMetalMap = new HashMap<>();

    public static String[] getDefaultMetalFluid(MetalType[] metals) {
        String[] metalFluid = new String[metals.length];
        for (int i = 0; i < metalFluid.length; i++) {
            metalFluid[i] = metals[i].name + "=" + metals[i].fluidName;
        }
        return metalFluid;
    }

    public static void genMetalFluid() {
        String[] priorityOverride = Config.metalLiquids.getStringList();
        for (String s : priorityOverride) {
            String[] spl = s.split("=");
            try {
                if (spl.length == 2) {
                    String ore = spl[0];
                    if (ItemUtils.getFluid(spl[1], 1) != null) {
                        Fluid item = ItemUtils.getFluid(spl[1], 1)
                            .getFluid();
                        liquidMetalMap.put(ore, item);
                    }
                }
            } catch (Exception e) {
                throwInvalidConfig(Config.metalLiquids);
            }
        }
    }

    public static Fluid getMetalFluid(MetalType metalType) {
        String metal = metalType.name;
        return liquidMetalMap.get(metal);
    }

    public static final TObjectIntMap<String> veinHeightMinMap = new TObjectIntHashMap<>();
    public static final TObjectIntMap<String> veinHeightMaxMap = new TObjectIntHashMap<>();

    public static String[] getDefaultVeinHeight(VeinType[] veinTypes) {
        String[] list = new String[veinTypes.length];
        for (int i = 0; i < veinTypes.length; i++) {
            list[i] = veinTypes[i].name + "=" + veinTypes[i].minY + "-" + veinTypes[i].maxY;
        }
        return list;
    }

    public static void genVeinHeightMap() {
        String[] priorityOverride = Config.oreVeinsHeightRange.getStringList();
        for (String s : priorityOverride) {
            String[] spl = s.split("=");
            if (spl.length == 2) {
                String ore = spl[0];
                String[] hs = spl[1].split("-");
                try {
                    int min = Integer.parseInt(hs[0]);
                    int max = Integer.parseInt(hs[1]);
                    veinHeightMinMap.put(ore, min);
                    veinHeightMaxMap.put(ore, max);
                } catch (Exception e) {
                    throwInvalidConfig(Config.oreVeinsHeightRange);
                }
            }
        }
    }

    public static int getVeinHeightMin(VeinType veinType) {
        String vein = veinType.name;
        return veinHeightMinMap.get(vein);
    }

    public static int getVeinHeightMax(VeinType veinType) {
        String vein = veinType.name;
        return veinHeightMaxMap.get(vein);
    }

    public static final TObjectIntMap<String> strataHeightMinMap = new TObjectIntHashMap<>();
    public static final TObjectIntMap<String> strataHeightMaxMap = new TObjectIntHashMap<>();

    public static String[] getDefaultStrataHeight(StoneType[] veinTypes) {
        String[] list = new String[veinTypes.length];
        for (int i = 0; i < veinTypes.length; i++) {
            list[i] = veinTypes[i].name + "=" + veinTypes[i].minHeight + "-" + veinTypes[i].maxHeight;
        }
        return list;
    }

    public static void genStrataHeightMap() {
        String[] priorityOverride = Config.strataStoneHeightRange.getStringList();
        for (String s : priorityOverride) {
            String[] spl = s.split("=");
            if (spl.length == 2) {
                String ore = spl[0];
                String[] hs = spl[1].split("-");
                try {
                    int min = Integer.parseInt(hs[0]);
                    int max = Integer.parseInt(hs[1]);
                    strataHeightMinMap.put(ore, min);
                    strataHeightMaxMap.put(ore, max);
                } catch (Exception e) {
                    throwInvalidConfig(Config.strataStoneHeightRange);
                }
            }
        }
    }

    public static int getStrataHeightMin(StoneType veinType) {
        String vein = veinType.name;
        return strataHeightMinMap.get(vein);
    }

    public static int getStrataHeightMax(StoneType veinType) {
        String vein = veinType.name;
        return strataHeightMaxMap.get(vein);
    }

    public static final TObjectIntMap<String> veinSizeMinMap = new TObjectIntHashMap<>();
    public static final TObjectIntMap<String> veinSizeMaxMap = new TObjectIntHashMap<>();

    public static String[] getDefaultSize(VeinType[] veinTypes) {
        String[] list = new String[veinTypes.length];
        for (int i = 0; i < veinTypes.length; i++) {
            list[i] = veinTypes[i].name + "=" + veinTypes[i].sizeMin + "-" + veinTypes[i].sizeMax;
        }
        return list;
    }

    public static void genVeinSizeMap() {
        String[] priorityOverride = Config.oreVeinsSizeRange.getStringList();
        for (String s : priorityOverride) {
            String[] spl = s.split("=");
            if (spl.length == 2) {
                String ore = spl[0];
                String[] hs = spl[1].split("-");
                try {
                    int min = Integer.parseInt(hs[0]);
                    int max = Integer.parseInt(hs[1]);
                    veinSizeMinMap.put(ore, min);
                    veinSizeMaxMap.put(ore, max);
                } catch (Exception e) {
                    throwInvalidConfig(Config.oreVeinsSizeRange);
                }
            }
        }
    }

    public static int getVeinSizeMin(VeinType veinType) {
        String vein = veinType.name;
        return veinSizeMinMap.get(vein);
    }

    public static int getVeinSizeMax(VeinType veinType) {
        String vein = veinType.name;
        return veinSizeMaxMap.get(vein);
    }

    private static final TObjectIntMap<String> anvilStepMap = new TObjectIntHashMap<>();

    public static String[] getDefaultAnvilStep() {
        AnvilAction[] metals = AnvilAction.values();
        String[] metalMelting = new String[metals.length];
        for (int i = 0; i < metalMelting.length; i++) {
            metalMelting[i] = metals[i].name + "=" + metals[i].step;
        }
        return metalMelting;
    }

    public static void genAnvilStep() {
        String[] priorityOverride = Config.anvilActionStep.getStringList();
        for (String s : priorityOverride) {
            String[] spl = s.split("=");
            if (spl.length == 2) {
                String ore = spl[0];
                try {
                    if (Integer.parseInt(spl[1]) != 0) {
                        int temp = Integer.parseInt(spl[1]);
                        anvilStepMap.put(ore, temp);
                    }
                } catch (Exception e) {
                    throwInvalidConfig(Config.anvilActionStep);
                }
            }
        }
    }

    public static int getAnvilStep(AnvilAction metalType) {
        String metal = metalType.name;
        return anvilStepMap.get(metal);
    }

    public static void throwInvalidConfig(Config config) {
        throw new IllegalStateException(
            "Config [" + config.name
                + "] in "
                + Primal.MODID
                + ".cfg is malformed."
                + "\n Delete the entry or fix it so it correctly apply in-game.");
    }
}
