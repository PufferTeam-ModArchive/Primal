package net.pufferlab.primal;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
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
import net.pufferlab.primal.blocks.*;
import net.pufferlab.primal.utils.*;
import net.pufferlab.primal.world.GlobalTickingData;

import cpw.mods.fml.common.FMLCommonHandler;

public class Utils {

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
        int percentage = Utils.floor((percentagePassed) * 100);
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
        for (ForgeDirection dir : ItemUtils.sideDirections) {
            Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            if (!block.isSideSolid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite())) {
                return false;
            }
        }
        return true;
    }

    public static Block getBlockDirection(World world, int x, int y, int z, ForgeDirection... directions) {
        int offsetX = x;
        int offsetY = y;
        int offsetZ = z;
        for (ForgeDirection direction : directions) {
            offsetX += direction.offsetX;
            offsetY += direction.offsetY;
            offsetZ += direction.offsetZ;
        }
        return world.getBlock(offsetX, offsetY, offsetZ);
    }

    public static boolean setBlockDirection(World world, int x, int y, int z, Block block, int meta,
        ForgeDirection... directions) {
        int offsetX = x;
        int offsetY = y;
        int offsetZ = z;
        for (ForgeDirection direction : directions) {
            offsetX += direction.offsetX;
            offsetY += direction.offsetY;
            offsetZ += direction.offsetZ;
        }
        return world.setBlock(offsetX, offsetY, offsetZ, block, meta, 2);
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

    public static <T> T[] pushFront(T[] a, T b) {
        T[] result = Arrays.copyOf(a, a.length);
        System.arraycopy(result, 0, result, 1, a.length - 1);
        result[0] = b;
        return result;
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

    public static int floor(double value) {
        return (int) Math.floor(value);
    }

    public static int clamp(int min, int max, int value) {
        return Math.max(min, Math.min(value, max));
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

    public static long packCoord(int x, int y, int z) {
        long lx = x & 0x3FFFFFFL; // 26 bits [-33,554,432 to 33,554,432]
        long ly = y & 0xFFFL; // 12 bits [-2048 to 2048]
        long lz = z & 0x3FFFFFFL; // 26 bits [-33,554,432 to 33,554,432]
        return (lx << 38) | (ly << 26) | lz;
    }

    public static int unpackX(long packed) {
        return (int) (packed << 0 >> 38); // signed
    }

    public static int unpackY(long packed) {
        return (int) (packed << 26 >> 52); // signed
    }

    public static int unpackZ(long packed) {
        return (int) (packed << 38 >> 38); // signed
    }
}
