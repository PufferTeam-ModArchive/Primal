package net.pufferlab.primal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.blocks.BlockPile;
import net.pufferlab.primal.items.*;
import net.pufferlab.primal.utils.FluidUtils;
import net.pufferlab.primal.utils.ItemDummy;
import net.pufferlab.primal.utils.SoundTypePrimal;
import net.pufferlab.primal.utils.StoneType;
import net.pufferlab.primal.world.GlobalTickingData;

import org.apache.commons.io.FileUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

public class Utils {

    private static final Map<Integer, ItemStack> cacheIS = new HashMap<>();
    private static final Map<String, ItemStack> itemCache = new HashMap<>();
    private static final Map<String, FluidStack> fluidCache = new HashMap<>();
    private static final Map<String, ItemStack> modItemCache = new HashMap<>();
    private static final Map<String, ItemStack> oreDictCache = new HashMap<>();
    private static List<String> modItemNames;

    public static final ForgeDirection[] sideDirections = new ForgeDirection[] { ForgeDirection.WEST,
        ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.NORTH, ForgeDirection.DOWN };

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

    public static ItemStack[] getItemStackListFromNBT(NBTTagCompound compound) {
        NBTTagList tagList = compound.getTagList("Items", 10);
        ItemStack[] inventory = new ItemStack[tagList.tagCount()];
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < inventory.length) inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
        }
        return inventory;
    }

    public static List<String> getItemStackNameListFromNBT(NBTTagCompound compound) {
        ItemStack[] inventory = getItemStackListFromNBT(compound);
        List<String> tooltip = new ArrayList<>();
        for (ItemStack item : inventory) {
            if (item != null) {
                String name = item.getDisplayName();
                tooltip.add(name + " x" + item.stackSize);
            }
        }
        return tooltip;
    }

    public static void damageItemIndex(int index, int amount, InventoryPlayer invPlayer) {
        if (!(invPlayer.player.capabilities.isCreativeMode)) {
            ItemStack stack = invPlayer.getStackInSlot(index);
            if (stack.isItemStackDamageable()) {
                if (stack.attemptDamageItem(amount, invPlayer.player.getRNG())) {
                    invPlayer.player.renderBrokenItemStack(stack);
                    --stack.stackSize;

                    if (invPlayer.player != null) {
                        EntityPlayer entityplayer = (EntityPlayer) invPlayer.player;
                        entityplayer.addStat(StatList.objectBreakStats[Item.getIdFromItem(stack.getItem())], 1);
                    }
                    if (stack.stackSize <= 0) {
                        invPlayer.setInventorySlotContents(index, (ItemStack) null);
                        MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(invPlayer.player, stack));
                    }
                }

            }
        }
    }

    public static String getDisplayName(Object... objects) {
        for (Object object : objects) {
            if (object instanceof List list) {
                if (list.get(0) instanceof ItemStack is) {
                    return is.getDisplayName();
                }
            }
            if (object instanceof ItemStack is) {
                return is.getDisplayName();
            }
            if (object instanceof FluidStack fs) {
                return fs.getLocalizedName();
            }
        }
        return "";
    }

    public static String getRecipeTooltip(String name, int timePassed, int timeToProcess, String suffix) {
        float percentagePassed = (float) timePassed / (float) timeToProcess;
        int percentage = (int) Math.floor((percentagePassed) * 100);
        return Constants.gray + name + ": " + percentage + "% " + suffix;
    }

    public static String getRecipeTooltip(String name, World world, long nextUpdate, int timeToProcess, String suffix) {
        long now = GlobalTickingData.getTickTime(world);

        long start = nextUpdate - timeToProcess;
        long elapsed = now - start;

        float pct = (float) elapsed / (float) timeToProcess;
        pct = MathHelper.clamp_float(pct, 0f, 1f);

        int percentage = (int) (pct * 100);

        return Constants.gray + name + ": " + percentage + "% " + suffix;
    }

    public static int getCurrentProgress(World world, long nextUpdate, int maxProgress) {
        long now = GlobalTickingData.getTickTime(world);

        long start = nextUpdate - maxProgress;
        long elapsed = now - start;

        if (elapsed < 0) elapsed = 0;
        if (elapsed > maxProgress) elapsed = maxProgress;

        return (int) elapsed;
    }

    public static long getWorldTime(int inTime) {
        return GlobalTickingData.getTickTime() + inTime;
    }

    public static String getStateTooltip(boolean state, String on, String off) {
        if (state) {
            return Constants.gray + "State: " + EnumChatFormatting.GREEN + on;
        } else {
            return Constants.gray + "State: " + EnumChatFormatting.RED + off;
        }
    }

    public static String getTemperatureTooltip(int temperature) {
        return Constants.gray + "Temperature: " + Constants.white + temperature + Constants.gray + " C";
    }

    public static NBTTagCompound getOrCreateTagCompound(ItemStack item) {
        NBTTagCompound tag = item.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            item.setTagCompound(tag);
        }
        return tag;
    }

    public static int toInt(long value) {
        if ((int) value != value) {
            return Integer.MAX_VALUE;
        }
        return (int) value;
    }

    public static boolean isLogBlock(Block block) {
        if (block == null) return false;
        if (block instanceof BlockLog) return true;
        return containsOreDict(block, "logWood");
    }

    public static boolean isSandBlock(Block block) {
        if (block == null) return false;
        if (block == Blocks.sand) return true;
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
        return containsOreDict(itemStack, "toolHoe");
    }

    public static boolean isShovelTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemSpade) return true;
        return containsOreDict(itemStack, "toolShovel");
    }

    public static boolean isAxeTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemAxe) return true;
        return containsOreDict(itemStack, "toolAxe");
    }

    public static boolean isKnifeTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemKnifePrimitive) return true;
        return containsOreDict(itemStack, "toolKnife");
    }

    public static boolean isHandstoneTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemHandstone) return true;
        return containsOreDict(itemStack, "toolHandstone");
    }

    public static boolean isBrokenTool(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        return containsOreDict(itemStack, "toolBroken");
    }

    public static boolean isLighter(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() instanceof ItemFlintAndSteel || itemStack.getItem() instanceof ItemFireStarter)
            return true;
        return containsOreDict(itemStack, "itemLighter");
    }

    public static boolean canBeLit(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getItem() == null) return false;
        if (itemStack.getItem() == Item.getItemFromBlock(Registry.unlit_torch)
            || itemStack.getItem() == Item.getItemFromBlock(Registry.lit_torch)) return true;
        return false;
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

    public static int getBlockX(int side, int x) {
        if (side == 4) {
            x--;
        }
        if (side == 5) {
            x++;
        }
        return x;
    }

    public static int getBlockY(int side, int y) {
        if (side == 0) {
            y--;
        }
        if (side == 1) {
            y++;
        }
        return y;
    }

    public static int getBlockZ(int side, int z) {
        if (side == 2) {
            z--;
        }
        if (side == 3) {
            z++;
        }
        return z;
    }

    public static boolean hasSolidWallsTop(World world, int x, int y, int z) {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            boolean isSolid = block
                .isSideSolid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite())
                || (block instanceof BlockPile)
                || (block.getMaterial() == Material.fire);
            if (!isSolid) {
                return false;
            }

        }
        return true;
    }

    public static boolean hasSolidWalls(World world, int x, int y, int z) {
        for (ForgeDirection dir : Utils.sideDirections) {
            Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            if (!block.isSideSolid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite())) {
                return false;
            }
        }
        return true;
    }

    public static ForgeDirection getDirectionFromFacing(int facingMeta) {
        return switch (facingMeta) {
            case 1 -> ForgeDirection.SOUTH;
            case 2 -> ForgeDirection.EAST;
            case 3 -> ForgeDirection.NORTH;
            case 4 -> ForgeDirection.WEST;
            default -> ForgeDirection.UNKNOWN;
        };
    }

    public static int getFacingMeta(int side, int axis) {
        if (axis == 0) {
            return switch (side) {
                case 3 -> 1;
                case 4 -> 4;
                case 2 -> 3;
                case 5 -> 2;
                default -> 0;
            };
        }
        if (axis == 1) {
            return switch (side) {
                case 0 -> 1;
                case 4 -> 4;
                case 1 -> 3;
                case 5 -> 2;
                default -> 0;
            };
        }
        if (axis == 2) {
            return switch (side) {
                case 3 -> 1;
                case 1 -> 4;
                case 2 -> 3;
                case 0 -> 2;
                default -> 0;
            };
        }
        return 0;
    }

    public static int getSimpleAxisFromFacing(int facingMeta) {
        return switch (facingMeta) {
            case 1, 3 -> 1;
            case 2, 4 -> 2;
            default -> 0;
        };
    }

    public static int getAxis(int side) {
        if (side == 0 || side == 1) {
            return 0;
        } else if (side == 2 || side == 3) {
            return 1;
        } else if (side == 4 || side == 5) {
            return 2;
        }
        return 0;
    }

    public static boolean isSidePositive(int side) {
        if (side == 1 || side == 3 || side == 5) {
            return true;
        }
        return false;
    }

    public static boolean isSimpleAxisConnected(int facingMeta, int facingMeta2) {
        return getSimpleAxisFromFacing(facingMeta) == getSimpleAxisFromFacing(facingMeta2);
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
            return check == wild;
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

    public static MovingObjectPosition getMovingObjectPositionFromPlayer(World worldIn, EntityPlayer playerIn,
        boolean useLiquids) {
        return ItemDummy.instance.getMovingObjectPositionFromPlayerPublic(worldIn, playerIn, useLiquids);
    }

    public static int getDirectionXZYaw(int yaw) {
        if (yaw == 0) {
            return 1;
        } else if (yaw == 1) {
            return 4;
        } else if (yaw == 2) {
            return 3;
        } else if (yaw == 3) {
            return 2;
        }

        return 0;
    }

    public static int getMetaYaw(float rotationYaw) {
        int yaw = MathHelper.floor_double((double) (rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return getDirectionXZYaw(yaw);
    }

    public static void place(ItemStack stack, World world, int x, int y, int z, Block toPlace, int metadata,
        EntityPlayer player) {
        if (world.isAirBlock(x, y, z) && world.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
            if (world.checkNoEntityCollision(toPlace.getCollisionBoundingBoxFromPool(world, x, y, z))
                && world.setBlock(x, y, z, toPlace, metadata, 3)) {
                world.setBlock(x, y, z, toPlace, metadata, 2);
                toPlace.onBlockPlacedBy(world, x, y, z, player, stack);
                stack.stackSize -= 1;
                playSound(world, x, y, z, toPlace);
                player.swingItem();
            }
        }
    }

    public static void placeNoConsume(ItemStack stack, World world, int x, int y, int z, Block toPlace, int metadata,
        EntityPlayer player) {
        if (world.isAirBlock(x, y, z) && world.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
            if (world.checkNoEntityCollision(toPlace.getCollisionBoundingBoxFromPool(world, x, y, z))
                && world.setBlock(x, y, z, toPlace, metadata, 3)) {
                world.setBlock(x, y, z, toPlace, metadata, 2);
                toPlace.onBlockPlacedBy(world, x, y, z, player, stack);
                playSound(world, x, y, z, toPlace);
                player.swingItem();
            }
        }
    }

    public static void placeSilent(ItemStack stack, World world, int x, int y, int z, Block toPlace, int metadata,
        EntityPlayer player) {
        if (world.isAirBlock(x, y, z) && world.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
            if (world.checkNoEntityCollision(toPlace.getCollisionBoundingBoxFromPool(world, x, y, z))
                && world.setBlock(x, y, z, toPlace, metadata, 3)) {
                world.setBlock(x, y, z, toPlace, metadata, 2);
                toPlace.onBlockPlacedBy(world, x, y, z, player, stack);
                player.swingItem();
            }
        }
    }

    public static void playSound(World world, int x, int y, int z, Block block) {
        world.playSoundEffect(
            x + 0.5f,
            y + 0.5f,
            z + 0.5f,
            block.stepSound.func_150496_b(),
            (block.stepSound.getVolume() + 1.0F) / 2.0F,
            block.stepSound.getPitch() * 0.8F);
    }

    public static void playSound(World world, int x, int y, int z, SoundTypePrimal stepSound) {
        world.playSoundEffect(
            x + 0.5f,
            y + 0.5f,
            z + 0.5f,
            stepSound.getPath(),
            (stepSound.getVolume() + 1.0F) / 2.0F,
            stepSound.getPitch() * 0.8F);
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

    private static Map<String, Integer> meltingMetalMap;

    public static int getMetalMelting(String metal) {
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
        if (meltingMetalMap.containsKey(metal)) {
            return meltingMetalMap.get(metal);
        }
        return 0;
    }

    private static Map<String, Fluid> liquidMetalMap;

    public static Fluid getMetalFluid(String metal) {
        if (liquidMetalMap == null) {
            liquidMetalMap = new HashMap<>();
            String[] priorityOverride = Config.metalLiquids.getStringList();
            for (String s : priorityOverride) {
                String[] spl = s.split("=");
                if (spl.length == 2) {
                    String ore = spl[0];
                    if (getFluid(spl[1], 1) != null) {
                        Fluid item = getFluid(spl[1], 1).getFluid();
                        liquidMetalMap.put(ore, item);
                    }
                }
            }
        }
        if (liquidMetalMap.containsKey(metal)) {
            return liquidMetalMap.get(metal);
        }
        return null;
    }

    private static Map<String, Integer> priorityMap;
    private static Map<String, ItemStack> priorityMapOverride;

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
        if (cacheIS.containsKey(key)) {
            return cacheIS.get(key);
        }
        ItemStack b = new ItemStack(block, 1, meta);
        cacheIS.put(key, b);
        return b;
    }

    public static int getBlockKey(int blockId, int metadata) {
        if (metadata >= 16) {
            metadata = 0;
        }
        return (blockId * 16) + metadata;
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

    public static FluidStack[] combineArrays(FluidStack[] a, FluidStack b) {
        FluidStack[] result = new FluidStack[a.length + 1];
        System.arraycopy(a, 0, result, 0, a.length);
        result[result.length - 1] = b;
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

    public static Integer[] toIntegerArray(int[] src) {
        Integer[] dst = new Integer[src.length];

        for (int i = 0; i < src.length; i++) {
            dst[i] = src[i];
        }
        return dst;
    }

    public static String getItemKey(Item item, int meta) {
        if (item != null) {
            return Item.getIdFromItem(item) + ":" + meta;
        }
        return null;
    }

    public static Map<String, String> metalTypes = new HashMap<>();

    public static String getMetalType(ItemStack item) {
        if (item == null) return null;
        Item itemObj = item.getItem();
        int itemMeta = item.getItemDamage();
        String key = getItemKey(itemObj, itemMeta);
        if (metalTypes.containsKey(key)) {
            return metalTypes.get(key);
        }
        int[] oreIDS = OreDictionary.getOreIDs(item);
        for (int oreID : oreIDS) {
            String name = OreDictionary.getOreName(oreID);
            if (name.contains("ingot")) {
                String[] names = name.split("ingot");
                if (names.length > 1) {
                    String metalName = names[1].toLowerCase();
                    metalTypes.put(key, metalName);
                    return metalName;
                }
            }
        }
        return null;
    }

    public static boolean isValidMetal(ItemStack item) {
        String metal = getMetalType(item);
        if (metal == null) return false;
        return true;
    }

    public static String readFile(File file) throws IOException {
        return file.exists() ? FileUtils.readFileToString(file, "UTF-8")
            .trim() : null;
    }

    public static void writeFile(File file, String content) throws IOException {
        FileUtils.writeStringToFile(file, content.trim(), "UTF-8");
    }

    public static void downloadFile(String urlTxt, File out) {
        try {
            URL url = new URL(urlTxt);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            try (InputStream in = connection.getInputStream()) {
                FileUtils.copyInputStreamToFile(in, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sha256(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        try (InputStream is = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
        }

        byte[] hash = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String translate(String key) {
        return StatCollector.translateToLocal(key);
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
