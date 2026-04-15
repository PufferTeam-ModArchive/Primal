package net.pufferlab.primal.utils;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.world.GlobalTickingData;

import cpw.mods.fml.common.FMLCommonHandler;

public class Utils {

    public static long getWorldTime(int inTime) {
        return GlobalTickingData.getTickTime() + inTime;
    }

    public static boolean isInRange(int number, int range, int number2) {
        int max = Math.abs(number) + range;
        int min = Math.abs(number) - range;
        if (number2 <= max && number2 >= min) {
            return true;
        }
        return false;
    }

    public static boolean isClose(double number, double number2) {
        double range = 0.0001D;
        double max = Math.abs(number) + range;
        double min = Math.abs(number) - range;
        if (number2 <= max && number2 >= min) {
            return true;
        }
        return false;
    }

    public static int toInt(long value) {
        if ((int) value != value) {
            return Integer.MAX_VALUE;
        }
        return (int) value;
    }

    public static boolean isRiverBiome(BiomeGenBase biome) {
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.OCEAN)
            || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.RIVER)) {
            return true;
        }
        return false;
    }

    public static boolean isRiverBiome(World world, int x, int y, int z) {
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

        return isRiverBiome(biome);
    }

    public static boolean isBeachBiome(BiomeGenBase biome) {
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.BEACH)) {
            return true;
        }
        return false;
    }

    public static boolean isBeachBiome(World world, int x, int y, int z) {
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

        return isBeachBiome(biome);
    }

    public static Random getSeededRandom(Random random, int x, int y, int z) {
        long seed = x * 3129871L ^ z * 116129781L ^ (long) y * 42317861L;
        random.setSeed(seed);
        return random;
    }

    public static boolean equalsStack(ItemStack wild, ItemStack check) {
        if (wild == null || check == null) {
            return check == wild;
        }

        return wild.getItem() == check.getItem()
            && (wild.getItemDamage() == Constants.wildcard || check.getItemDamage() == Constants.wildcard
                || wild.getItemDamage() == check.getItemDamage());
    }

    public static boolean equalsStack(FluidStack wild, FluidStack check) {
        if (wild == null || check == null) {
            return false;
        }

        return wild.getFluid() == check.getFluid();
    }

    public static boolean equalsStack(ItemStack check, FluidStack wild) {
        if (wild == null || check == null) {
            return false;
        }

        FluidStack stack = FluidUtils.getFluidFromStack(check);
        if (stack == null) {
            return false;
        }
        return wild.getFluid() == stack.getFluid();
    }

    public static boolean contains(int[] array, int targetString) {
        if (array == null) return false;
        for (int element : array) {
            if (targetString == element) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(StoneType[] array, StoneType targetString) {
        if (targetString == null || array == null) {
            return false;
        }
        for (StoneType element : array) {
            if (targetString.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(String[] array, String targetString) {
        if (targetString == null || array == null) {
            return false;
        }
        for (String element : array) {
            if (targetString.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Block[] array, Block targetString) {
        if (targetString == null) {
            return false;
        }
        for (Block element : array) {
            if (targetString == element) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsStack(List<ItemStack> list, ItemStack b) {
        for (ItemStack item : list) {
            if (item == null) continue;
            if (Utils.equalsStack(item, b)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsStack(ItemStack[] list, ItemStack b) {
        for (ItemStack item : list) {
            if (Utils.equalsStack(item, b)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsStack(ItemStack b, ItemStack[] list) {
        for (ItemStack item : list) {
            if (Utils.equalsStack(item, b)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsStack(FluidStack b, FluidStack[] list) {
        for (FluidStack item : list) {
            if (Utils.equalsStack(item, b)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsStack(Object[] list, ItemStack b) {
        for (Object item : list) {
            if (item instanceof ItemStack stack) {
                if (Utils.equalsStack(stack, b)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsStack(ItemStack b, List<ItemStack> list) {
        return containsStack(list, b);
    }

    public static boolean containsStack(List<ItemStack> list, List<ItemStack> list2) {
        for (ItemStack item : list) {
            for (ItemStack item2 : list2) {
                if (Utils.equalsStack(item, item2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsOreDict(ItemStack b, String oreDict) {
        return containsStack(b, OreDictionary.getOres(oreDict));
    }

    public static boolean containsOreDict(ItemStack b, String[] oreDict) {
        boolean contains = false;
        for (String ore : oreDict) {
            if (containsOreDict(b, ore)) {
                contains = true;
            }
        }
        return contains;
    }

    public static boolean containsOreDict(Block block, String oreDict) {
        return containsOreDict(getOrReuseItemStack(block, 0), oreDict);
    }

    public static boolean containsOreDict(Block block, int meta, String oreDict) {
        return containsOreDict(getOrReuseItemStack(block, meta), oreDict);
    }

    public static ItemStack getOrReuseItemStack(Block block, int meta) {
        int key = getBlockKey(block, meta);
        if (ItemUtils.cacheIS.containsKey(key)) {
            return ItemUtils.cacheIS.get(key);
        }
        ItemStack b = new ItemStack(block, 1, meta);
        ItemUtils.cacheIS.put(key, b);
        return b;
    }

    public static int getBlockKey(int blockId, int metadata) {
        return (blockId << 15) | metadata;
    }

    public static int getBlockId(int key) {
        return key >>> 15;
    }

    public static int getMeta(int key) {
        return key & 0x7FFF;
    }

    public static int getBlockKey(Block block, int metadata) {
        return getBlockKey(Block.getIdFromBlock(block), metadata);
    }

    public static int getIndex(String[] array, String name) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) continue;
            if (array[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static int getIndex(Block[] array, Block name) {
        if (array == null || name == null) return -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) continue;
            if (array[i] == name) {
                return i;
            }
        }
        return -1;
    }

    public static int getIndex(int[] array, int name) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == name) {
                return i;
            }
        }
        return -1;
    }

    public static int getIndex(Fluid[] array, Fluid item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) continue;
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public static int getRGB(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

    public static float getR(int color) {
        return ((color >> 16) & 0xFF) / 255f;
    }

    public static float getG(int color) {
        return ((color >> 8) & 0xFF) / 255f;
    }

    public static float getB(int color) {
        return (color & 0xFF) / 255f;
    }

    public static float getA(int color) {
        return ((color >> 24) & 0xFF) / 255f;
    }

    public static int[] combineArrays(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static FluidStack[] combineStackArrays(FluidStack[] array, FluidStack[] array2) {
        for (FluidStack stack : array2) {
            array = Utils.appendStack(array, stack);
        }
        return array;
    }

    @SafeVarargs
    public static <T> T[] combineArrays(T[]... arrays) {
        int totalLength = 0;

        for (T[] array : arrays) {
            if (array != null) {
                totalLength += array.length;
            }
        }

        T[] result = Arrays.copyOf(arrays[0], totalLength);

        int offset = arrays[0].length;

        for (int i = 1; i < arrays.length; i++) {
            T[] array = arrays[i];
            if (array != null) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }

        return result;
    }

    public static <T> void clear(T[] array) {
        Arrays.fill(array, null);
    }

    public static ItemStack[] appendStack(ItemStack[] array, ItemStack stack) {
        for (int i = 0; i < array.length; i++) {
            ItemStack stack2 = array[i];
            if (Utils.equalsStack(stack2, stack)) {
                stack2.stackSize = stack2.stackSize + stack.stackSize;
                return array;
            }
        }
        return Utils.append(array, stack.copy());
    }

    public static FluidStack[] appendStack(FluidStack[] array, FluidStack stack) {
        for (int i = 0; i < array.length; i++) {
            FluidStack stack2 = array[i];
            if (Utils.equalsStack(stack2, stack)) {
                stack2.amount = stack2.amount + stack.amount;
                return array;
            }
        }
        return Utils.append(array, stack.copy());
    }

    public static <T> T[] append(T[] array, T element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = element;
                return array;
            }
        }
        T[] result = Arrays.copyOf(array, array.length + 1);
        result[array.length] = element;
        return result;
    }

    public static String[] removeFirst(String[] old) {
        if (old == null || old.length <= 1) {
            return new String[0];
        }

        String[] newArr = new String[old.length - 1];
        System.arraycopy(old, 1, newArr, 0, newArr.length);
        return newArr;
    }

    public static <T> T[] pushFront(T[] a, T b) {
        T[] result = Arrays.copyOf(a, a.length);
        System.arraycopy(result, 0, result, 1, a.length - 1);
        result[0] = b;
        return result;
    }

    public static int getRandomInRange(Random random, int min, int max) {
        if (min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        return random.nextInt((max - min) + 1) + min;
    }

    public static float getRandomInRange(Random random, float min, float max) {
        if (min > max) {
            float tmp = min;
            min = max;
            max = tmp;
        }
        return min + random.nextFloat() * (max - min);
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static final Map<String, ResourceLocation> resourceMap = new HashMap<>();

    public static ResourceLocation asResource(String namespace, String resourceLocation) {
        ResourceLocation resource = resourceMap.get(namespace + ":" + resourceLocation);
        if (resource == null) {
            resource = new ResourceLocation(namespace, resourceLocation);
            resourceMap.put(namespace + ":" + resourceLocation, resource);
        }
        return resource;
    }

    @SafeVarargs
    public static <T> List<T> asList(T... elements) {
        if (elements.length == 1) {
            return Collections.singletonList(elements[0]);
        }
        return new ArrayList<>(Arrays.asList(elements));
    }

    public static String getItemKey(Item item, int meta) {
        if (item != null) {
            return Item.getIdFromItem(item) + ":" + meta;
        }
        return null;
    }

    public static int floor(double value) {
        return (int) Math.floor(value);
    }

    public static int clamp(int min, int max, int value) {
        return Math.max(min, Math.min(value, max));
    }

    public static float clamp(int min, int max, float value) {
        return Math.max(min, Math.min(value, max));
    }

    public static float clamp(float min, float max, float value) {
        return Math.max(min, Math.min(value, max));
    }

    public static int pow(int value) {
        return (value * value);
    }

    public static String translate(String key) {
        return StatCollector.translateToLocal(key);
    }

    public static String translate(String key, Object... params) {
        return StatCollector.translateToLocalFormatted(key, params);
    }

    public static boolean isClient() {
        return FMLCommonHandler.instance()
            .getSide()
            .isClient();
    }

    public static boolean isDev() {
        return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

}
