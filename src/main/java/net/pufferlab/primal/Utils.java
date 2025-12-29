package net.pufferlab.primal;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;
import net.pufferlab.primal.blocks.BlockPile;
import net.pufferlab.primal.blocks.SoundTypePrimal;
import net.pufferlab.primal.items.ItemDummy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class Utils {

    private static final Map<Integer, ItemStack> cacheIS = new HashMap<>();
    private static final Map<String, ItemStack> itemCache = new HashMap<>();
    private static final Map<String, FluidStack> fluidCache = new HashMap<>();

    public static final ForgeDirection[] sideDirections = new ForgeDirection[] { ForgeDirection.WEST,
        ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.NORTH, ForgeDirection.DOWN };

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
        String[] array = s.split(":");
        String mod = array[0];
        String item = array[1];
        int meta = 0;
        if (array.length > 2) {
            meta = array[2].equals("*") ? OreDictionary.WILDCARD_VALUE : Integer.parseInt(array[2]);
        }

        int number = 1;
        if (array.length > 3) {
            number = array[3].equals("*") ? OreDictionary.WILDCARD_VALUE : Integer.parseInt(array[3]);
        }

        return getItem(mod, item, meta, number);
    }

    public static ItemStack getModItem(String wood, int number) {
        if (Utils.containsExactMatch(Constants.strawItems, wood)) {
            return getItem(Primal.MODID, "straw", Utils.getItemFromArray(Constants.strawItems, wood), number);
        }
        if (Utils.containsExactMatch(Constants.hideItems, wood)) {
            return getItem(Primal.MODID, "hide", Utils.getItemFromArray(Constants.hideItems, wood), number);
        }
        if (Utils.containsExactMatch(Constants.woodItems, wood)) {
            return getItem(Primal.MODID, "wood", Utils.getItemFromArray(Constants.woodItems, wood), number);
        }
        if (Utils.containsExactMatch(Constants.flintItems, wood)) {
            return getItem(Primal.MODID, "flint", Utils.getItemFromArray(Constants.flintItems, wood), number);
        }
        if (Utils.containsExactMatch(Constants.powderItems, wood)) {
            return getItem(Primal.MODID, "powder", Utils.getItemFromArray(Constants.powderItems, wood), number);
        }
        if (Utils.containsExactMatch(Constants.clayItems, wood)) {
            return getItem(Primal.MODID, "clay", Utils.getItemFromArray(Constants.clayItems, wood), number);
        }
        if (Utils.containsExactMatch(Constants.moldItems, wood)) {
            return getItem(Primal.MODID, "mold", Utils.getItemFromArray(Constants.moldItems, wood), number);
        }
        if (Utils.containsExactMatch(Constants.icons, wood)) {
            return getItem(Primal.MODID, "icon", Utils.getItemFromArray(Constants.icons, wood), number);
        }

        return null;
    }

    public static ItemStack getModItem(String type, String wood, int number) {
        boolean hasColor = Utils.containsExactMatch(Constants.colorTypes, wood);
        int color = Utils.getItemFromArray(Constants.colorTypes, wood);
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
        if (type.equals("bed") && wood.equals("red")) {
            return getItem("minecraft", "bed", 0, 1);
        }
        if (Primal.EFRLoaded) {
            if (type.equals("concrete") || type.equals("concrete_powder") || type.equals("banner")) {
                if (hasColor) {
                    return getItem("etfuturum", type, color, number);
                }
            }
            if (type.equals("glazed_terracotta") || type.equals("bed")) {
                if (hasColor) {
                    return getItem("etfuturum", wood + "_" + type, 0, number);
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

    public static int getHeatingLevel(int temperature) {
        if (temperature > 1 && temperature < 200) {
            return 1;
        } else if (temperature >= 200 && temperature < 400) {
            return 2;
        } else if (temperature >= 400 && temperature < 600) {
            return 3;
        } else if (temperature >= 600 && temperature < 800) {
            return 4;
        } else if (temperature >= 800 && temperature < 1000) {
            return 5;
        } else if (temperature >= 1000 && temperature < 1200) {
            return 6;
        } else if (temperature >= 1200) {
            return 7;
        } else {
            return 0;
        }
    }

    public static int getTemperatureFromNBT(NBTTagCompound nbt) {
        if (nbt == null) return 0;
        if (nbt.hasKey("temperature")) {
            return nbt.getInteger("temperature");
        }
        return 0;
    }

    public static void setTemperatureToNBT(NBTTagCompound nbt, int temperature) {
        if (nbt == null) return;
        if (nbt.hasKey("temperature")) {
            nbt.setInteger("temperature", temperature);
        }
    }

    public static String getFluidInfoFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("Tank")) {
            NBTTagCompound tank = nbt.getCompoundTag("Tank");
            if (!tank.hasKey("Empty")) {
                FluidStack fluid = FluidStack.loadFluidStackFromNBT(tank);
                return fluid.getLocalizedName() + " " + fluid.amount + " mB";
            }
        }
        return null;
    }

    public static String getFluidInfoOutputFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("TankOutput")) {
            NBTTagCompound tank = nbt.getCompoundTag("TankOutput");
            if (!tank.hasKey("Empty")) {
                FluidStack fluid = FluidStack.loadFluidStackFromNBT(tank);
                return fluid.getLocalizedName() + " " + fluid.amount + " mB";
            }
        }
        return null;
    }

    public static FluidStack getFluidFromStack(ItemStack stack) {
        if (stack == null) return null;
        ItemStack stack2 = stack.copy();
        stack2.stackSize = 1;

        if (stack2.getItem() instanceof IFluidContainerItem item) {
            return item.getFluid(stack2);
        }

        return FluidContainerRegistry.getFluidForFilledItem(stack2);
    }

    public static ItemStack getStackFromFluid(ItemStack emptyContainer, FluidStack fluid) {
        if (emptyContainer == null || fluid == null) return null;

        if (emptyContainer.getItem() instanceof IFluidContainerItem) {
            ItemStack filled = emptyContainer.copy();
            IFluidContainerItem item = (IFluidContainerItem) filled.getItem();

            int filledAmount = item.fill(filled, fluid, true);
            if (filledAmount > 0) {
                filled.stackSize = 1;
                return filled;
            }

            return null;
        }

        ItemStack stack = FluidContainerRegistry.fillFluidContainer(fluid, emptyContainer);
        stack.stackSize = 1;
        return stack;
    }

    public static ItemStack getEmptyContainer(ItemStack filled) {
        if (filled == null) return null;

        if (Primal.BOPLoaded) {
            ItemStack bopEmptyBucket = getItem("BiomesOPlenty:bopBucket:*:1");
            ItemStack emptyBucket = getItem("minecraft:bucket:0:1");
            if (Utils.containsStack(filled, bopEmptyBucket)) {
                return emptyBucket.copy();
            }
        }

        if (Primal.WGLoaded) {
            ItemStack capsule = getItem("WitchingGadgets:item.WG_CrystalFlask:*:1");
            if (Utils.containsStack(filled, capsule)) {
                capsule.stackTagCompound = null;
                capsule.setItemDamage(0);
                capsule.stackSize = 1;
                return capsule;
            }
        }

        if (filled.getItem() instanceof IFluidContainerItem) {
            ItemStack copy = filled.copy();
            IFluidContainerItem item = (IFluidContainerItem) copy.getItem();
            item.drain(copy, Integer.MAX_VALUE, true);
            copy.stackSize = 1;
            return copy;
        }

        ItemStack drained = FluidContainerRegistry.drainFluidContainer(filled);
        if (drained != null) {
            drained.stackSize = 1;
            return drained;
        }

        return null;
    }

    public static boolean isFluidContainer(ItemStack stack) {
        if (stack == null) return false;

        if (stack.getItem() instanceof IFluidContainerItem) {
            return true;
        }

        if (FluidContainerRegistry.isFilledContainer(stack)) {
            return true;
        }

        if (FluidContainerRegistry.isEmptyContainer(stack)) {
            return true;
        }

        return false;
    }

    public static boolean isEmptyFluidContainer(ItemStack stack) {
        if (stack == null) return false;

        if (stack.getItem() instanceof IFluidContainerItem) {
            IFluidContainerItem item = (IFluidContainerItem) stack.getItem();
            FluidStack fluid = item.getFluid(stack);
            return fluid == null || fluid.amount <= 0;
        }

        return FluidContainerRegistry.isEmptyContainer(stack);
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

    public static int getSimpleAxisFromFacing(int facingMeta) {
        return switch (facingMeta) {
            case 1, 3 -> 1;
            case 2, 4 -> 2;
            default -> 0;
        };
    }

    public static boolean isSimpleAxisConnected(int facingMeta, int facingMeta2) {
        return getSimpleAxisFromFacing(facingMeta) == getSimpleAxisFromFacing(facingMeta2);
    }

    public static boolean containsStack(ItemStack wild, ItemStack check) {
        if (wild == null || check == null) {
            return check == wild;
        }

        if (wild.getItem() == check.getItem() && (wild.getItemDamage() == OreDictionary.WILDCARD_VALUE
            || check.getItemDamage() == OreDictionary.WILDCARD_VALUE
            || wild.getItemDamage() == check.getItemDamage())) {
            return true;
        }
        return false;
    }

    public static boolean containsStack(FluidStack wild, FluidStack check) {
        if (wild == null || check == null) {
            return check == wild;
        }

        return wild.getFluid() == check.getFluid();
    }

    public static boolean containsStack(ItemStack check, FluidStack wild) {
        if (wild == null || check == null) {
            return false;
        }

        FluidStack stack = getFluidFromStack(check);
        if (stack == null) {
            return false;
        }
        return wild.getFluid() == stack.getFluid();
    }

    public static ItemStack nullableStack(ItemStack stack) {
        if (stack != null) {
            return stack;
        }
        return new ItemStack(Blocks.air, 1, 0);
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

    public static void playSoundOther(World world, int x, int y, int z, SoundTypePrimal stepSound) {
        world.playSoundEffect(
            x + 0.5f,
            y + 0.5f,
            z + 0.5f,
            stepSound.getOtherPath(),
            (stepSound.getVolume() + 1.0F) / 2.0F,
            stepSound.getPitch() * 0.8F);
    }

    public static String getOreDictionaryName(String prefix, String suffix) {
        String[] array = suffix.split("_");
        String[] arrayO = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            String[] suffixArray = array[i].split("");
            suffixArray[0] = suffixArray[0].toUpperCase();
            arrayO[i] = String.join("", suffixArray);
        }
        String output = String.join("", arrayO);

        return prefix + output;
    }

    public static boolean containsExactMatch(int[] array, int targetString) {
        for (int element : array) {
            if (targetString == element) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsExactMatch(String[] array, String targetString) {
        if (targetString == null) {
            return true;
        }
        for (String element : array) {
            if (targetString.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsExactMatch(Block[] array, Block targetString) {
        if (targetString == null) {
            return true;
        }
        for (Block element : array) {
            if (targetString == element) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsList(List<ItemStack> list, ItemStack b) {
        for (ItemStack item : list) {
            if (Utils.containsStack(item, b)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsList(ItemStack b, List<ItemStack> list) {
        for (ItemStack item : list) {
            if (Utils.containsStack(item, b)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsList(List<ItemStack> list, List<ItemStack> list2) {
        for (ItemStack item : list) {
            for (ItemStack item2 : list2) {
                if (Utils.containsStack(item, item2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsOreDict(ItemStack b, String oreDict) {
        return containsList(b, OreDictionary.getOres(oreDict));
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

    public static int getItemFromArray(String[] woodType, String wood) {
        for (int i = 0; i < woodType.length; i++) {
            if (woodType[i] == null) continue;
            if (woodType[i].equals(wood)) {
                return i;
            }
        }
        return 0;
    }

    public static int getItemFromArray(Block[] woodType, Block wood) {
        for (int i = 0; i < woodType.length; i++) {
            if (woodType[i] == null) continue;
            if (woodType[i] == wood) {
                return i;
            }
        }
        return 0;
    }

    public static int getItemFromArray(int[] woodType, int wood) {
        for (int i = 0; i < woodType.length; i++) {
            if (woodType[i] == wood) {
                return i;
            }
        }
        return 0;
    }

    public static int getItemFromArray(Fluid[] array, Fluid item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) continue;
            if (array[i].equals(item)) {
                return i;
            }
        }
        return 0;
    }

    public static String getItemKey(ItemStack itemstack) {
        Item item = itemstack.getItem();
        int meta = itemstack.getItemDamage();
        return getItemKey(item, meta);
    }

    public static String getItemKey(Item item, int meta) {
        if (item != null) {
            return Item.getIdFromItem(item) + ":" + meta;
        }
        return null;
    }

    public static boolean isClient() {
        return FMLCommonHandler.instance()
            .getSide()
            .isClient();
    }

    public static String getOreDictKey(ItemStack b, String oreDict) {
        return Utils.getItemKey(b) + ":" + oreDict;
    }
}
