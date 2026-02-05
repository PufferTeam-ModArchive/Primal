package net.pufferlab.primal.utils;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.*;
import net.pufferlab.primal.blocks.*;
import net.pufferlab.primal.items.ItemFireStarter;
import net.pufferlab.primal.items.ItemHammerPrimitive;
import net.pufferlab.primal.items.ItemHandstone;
import net.pufferlab.primal.items.ItemKnifePrimitive;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemUtils {

    public static final Map<Integer, ItemStack> cacheIS = new HashMap<>();
    public static final ForgeDirection[] sideDirections = new ForgeDirection[] { ForgeDirection.WEST,
        ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.NORTH, ForgeDirection.DOWN };
    private static final Map<String, ItemStack> itemCache = new HashMap<>();
    private static final Map<String, FluidStack> fluidCache = new HashMap<>();
    private static final Map<String, ItemStack> modItemCache = new HashMap<>();
    private static final Map<String, ItemStack> oreDictCache = new HashMap<>();
    private static List<String> modItemNames;
    private static Map<String, Integer> priorityMap;
    private static Map<String, ItemStack> priorityMapOverride;

    public static ItemStack getItem(String mod, String item) {
        return getItem(mod, item, 0, 1);
    }

    public static ItemStack getItem(String mod, String item, int meta, int number) {
        String key = mod + ":" + item + ":" + meta + ":" + number;
        if (itemCache.containsKey(key)) {
            return itemCache.get(key);
        }

        if (GameRegistry.findItem(mod, item) != null) {
            ItemStack is = new ItemStack(GameRegistry.findItem(mod, item), number, meta);
            itemCache.put(key, is);
            return is;
        } else if (GameRegistry.findBlock(mod, item) != null) {
            ItemStack is = new ItemStack(GameRegistry.findBlock(mod, item), number, meta);
            itemCache.put(key, is);
            return is;
        } else {
            Primal.LOG.error("Tried to get invalid ItemStack from :{}:{}:{}:{}.", mod, item, meta, meta);
        }
        return null;
    }

    public static FluidStack getFluid(String fluid, int number) {
        String key = fluid + ":" + number;
        if (fluidCache.containsKey(key)) {
            return fluidCache.get(key);
        }

        if (FluidRegistry.getFluid(fluid) != null) {
            FluidStack fs = new FluidStack(FluidRegistry.getFluid(fluid), number);
            fluidCache.put(key, fs);
            return fs;
        } else {
            Primal.LOG.error("Tried to get invalid FluidStack from {}:{}.", fluid, number);
        }
        return null;
    }

    public static ItemStack getItem(String s) {
        if (itemCache.containsKey(s)) {
            return itemCache.get(s);
        }
        if (s == null) return null;
        String[] array = s.split(":");
        String mod = array[0];
        String item = array[1];
        int meta = 0;
        if (array.length > 2) {
            meta = array[2].equals("*") ? Constants.wildcard : Integer.parseInt(array[2]);
        }

        int number = 1;
        if (array.length > 3) {
            number = array[3].equals("*") ? 1 : Integer.parseInt(array[3]);
        }

        return getItem(mod, item, meta, number);
    }

    public static void registerModItem(String name, ItemStack stack) {
        modItemCache.put(name, stack);
    }

    public static void registerModOreDict(String name, ItemStack stack) {
        oreDictCache.put(name, stack);
    }

    public static Set<Map.Entry<String, ItemStack>> getOreDictCache() {
        return oreDictCache.entrySet();
    }

    public static List<String> getModItems() {
        if (modItemNames == null) {
            modItemNames = new ArrayList<>(modItemCache.size());
            for (Map.Entry<String, ItemStack> entry : modItemCache.entrySet()) {
                modItemNames.add(entry.getKey());
            }
        }
        return modItemNames;
    }

    public static ItemStack getModItem(String name, int number) {
        ItemStack stack = modItemCache.get(name);
        if (stack != null) {
            if (stack.stackSize == number) {
                return stack;
            }
            ItemStack tempStack = stack.copy();
            tempStack.stackSize = number;
            return tempStack;
        }
        Primal.LOG.error("Tried to get invalid Custom Mod ItemStack from {}:{}.", name, number);
        return null;
    }

    public static ItemStack getModItem(String type, String name, int number) {
        boolean hasColor = Utils.contains(Constants.colorTypes, name);
        int color = Utils.getIndex(Constants.colorTypes, name);
        if (type.equals("carpet") || type.equals("wool")) {
            if (hasColor) {
                return getItem("minecraft", type, color, number);
            }
        }
        if (type.equals("hardened_clay") || type.equals("glass") || type.equals("glass_pane")) {
            if (hasColor) {
                return getItem("minecraft", "stained_" + type, color, number);
            } else {
                return getItem("minecraft", type, 0, number);
            }
        }
        if (type.equals("bed") && name.equals("red")) {
            return getItem("minecraft", "bed", 0, 1);
        }
        if (Mods.efr.isLoaded()) {
            if (type.equals("concrete") || type.equals("concrete_powder") || type.equals("banner")) {
                if (hasColor) {
                    return getItem(Mods.efr.MODID, type, color, number);
                }
            }
            if (type.equals("glazed_terracotta") || type.equals("bed")) {
                if (hasColor) {
                    return getItem(Mods.efr.MODID, name + "_" + type, 0, number);
                }
            }
        }
        return null;
    }

    public static boolean isValidOreDict(String oreDict) {
        if (!OreDictionary.doesOreNameExist(oreDict)) return false;
        if (OreDictionary.getOres(oreDict)
            .isEmpty()) return false;
        return true;
    }

    public static String getOreDictionaryName(String prefix, String suffix) {
        String prefix2 = decapitalizeFirstLetter(getCapitalizedName(prefix));
        String suffix2 = getCapitalizedName(suffix);
        return prefix2 + suffix2;
    }

    public static String decapitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1)
            .toLowerCase() + str.substring(1);
    }

    public static String getCapitalizedName(String suffix) {
        String[] array = suffix.split("_");
        String[] arrayO = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            String[] suffixArray = array[i].split("");
            suffixArray[0] = suffixArray[0].toUpperCase();
            arrayO[i] = String.join("", suffixArray);
        }
        return String.join("", arrayO);
    }

    public static ItemStack getOreDictItem(String name, int number) {
        ItemStack stack = getOreDictItem(name);
        if (stack != null) {
            if (stack.stackSize == number) {
                return stack;
            }
            ItemStack tempStack = stack.copy();
            tempStack.stackSize = number;
            return tempStack;
        }
        Primal.LOG.error("Tried to get invalid OreDict ItemStack from {}:{}.", name, number);
        return null;
    }

    public static ItemStack getOreDictItem(String oreDict) {
        if (priorityMap == null) {
            priorityMap = new HashMap<>();
            String[] priority = Config.metalPriority.getStringList();
            for (int i = 0; i < priority.length; i++) {
                priorityMap.put(priority[i], i);
            }
        }
        if (priorityMapOverride == null) {
            priorityMapOverride = new HashMap<>();
            String[] priorityOverride = Config.metalPriorityOverride.getStringList();
            for (String s : priorityOverride) {
                String[] spl = s.split("=");
                if (spl.length == 2) {
                    String ore = spl[0];
                    if (getItem(spl[1]) != null) {
                        ItemStack item = getItem(spl[1]);
                        priorityMapOverride.put(ore, item);
                    }
                }
            }
        }
        if (priorityMapOverride.containsKey(oreDict)) {
            return priorityMapOverride.get(oreDict);
        }
        ItemStack item = null;
        int index = Integer.MAX_VALUE;
        List<ItemStack> list = OreDictionary.getOres(oreDict);
        for (ItemStack stack : list) {
            String mod = getModId(stack);
            int modIndex = priorityMap.getOrDefault(mod, 999999);
            if (modIndex < index) {
                index = modIndex;
                item = stack;
            }
        }
        return item;
    }

    public static String getModId(ItemStack stack) {
        if (stack == null || stack.getItem() == null) return "unknown";
        Item item = stack.getItem();

        String mod = GameData.getItemRegistry()
            .getNameForObject(item)
            .split(":")[0];
        return mod;
    }

    public static boolean isLogBlock(Block block) {
        if (block == null) return false;
        if (block instanceof BlockLog) return true;
        return Utils.containsOreDict(block, "logWood");
    }

    public static boolean isTerrainBlock(Block block) {
        return isNaturalStone(block) || isDirtBlock(block)
            || isGrassBlock(block)
            || isGravelBlock(block)
            || isSandBlock(block);
    }

    public static boolean isNaturalStone(Block block) {
        if (block == null) return false;
        if (block == Registry.stone) return true;
        if (block == Blocks.stone) return true;
        return false;
    }

    public static boolean isDirtBlock(Block block) {
        if (block == null) return false;
        if (block instanceof BlockMetaDirt) return true;
        if (block == Blocks.dirt) return true;
        return false;
    }

    public static boolean isFarmlandBlock(Block block) {
        if (block == null) return false;
        if (block instanceof BlockMetaFarmland) return true;
        if (block == Blocks.farmland) return true;
        return false;
    }

    public static boolean isGrassBlock(Block block) {
        if (block == null) return false;
        if (block instanceof BlockMetaGrass) return true;
        if (block == Blocks.grass) return true;
        return false;
    }

    public static boolean isSandBlock(Block block) {
        if (block == null) return false;
        if (block instanceof BlockMetaSand) return true;
        if (block == Blocks.sand) return true;
        return false;
    }

    public static boolean isGravelBlock(Block block) {
        if (block == null) return false;
        if (block instanceof BlockMetaGravel) return true;
        if (block == Blocks.gravel) return true;
        return false;
    }

    public static boolean isSoilBlock(Block block, int meta) {
        if (block == null) return false;
        return block.getHarvestTool(meta) == "shovel";
    }

    public static boolean isHoeTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemHoe) return true;
        return Utils.containsOreDict(itemStack, "toolHoe");
    }

    public static boolean isShovelTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemSpade) return true;
        return Utils.containsOreDict(itemStack, "toolShovel");
    }

    public static boolean isAxeTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemAxe) return true;
        return Utils.containsOreDict(itemStack, "toolAxe");
    }

    public static boolean isKnifeTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemKnifePrimitive) return true;
        return Utils.containsOreDict(itemStack, "toolKnife");
    }

    public static boolean isHammerTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemHammerPrimitive) return true;
        return Utils.containsOreDict(itemStack, "toolHammer");
    }

    public static boolean isHandstoneTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemHandstone) return true;
        return Utils.containsOreDict(itemStack, "toolHandstone");
    }

    public static boolean isBrokenTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        return Utils.containsOreDict(itemStack, "toolBroken");
    }

    public static boolean isLighter(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemFlintAndSteel || itemStack.getItem() instanceof ItemFireStarter)
            return true;
        return Utils.containsOreDict(itemStack, "itemLighter");
    }

    public static boolean canBeLit(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() == Item.getItemFromBlock(Registry.unlit_torch)
            || itemStack.getItem() == Item.getItemFromBlock(Registry.lit_torch)) return true;
        return false;
    }
}
