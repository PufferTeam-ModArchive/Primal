package net.pufferlab.primitivelife;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public class Utils {

    private static final Map<Object, Boolean> cacheOre = new HashMap<>();
    private static final Map<Integer, ItemStack> cacheIS = new HashMap<>();

    public static final ForgeDirection[] sideDirections = new ForgeDirection[] { ForgeDirection.WEST,
        ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.NORTH };

    public static ItemStack getItem(String mod, String item, int meta, int number) {
        if (GameRegistry.findItem(mod, item) != null) {
            return new ItemStack(GameRegistry.findItem(mod, item), number, meta);
        } else if (GameRegistry.findBlock(mod, item) != null) {
            return new ItemStack(GameRegistry.findBlock(mod, item), number, meta);
        }
        return null;
    }

    public static ItemStack getItem(String s) {
        String[] array = s.split(":");
        String mod = array[0];
        String item = array[1];
        int meta = 0;
        if (array.length > 2) {
            if (array[2].equals("*")) {
                meta = OreDictionary.WILDCARD_VALUE;
            } else {
                meta = Integer.parseInt(array[2]);
            }
        }
        int number = 1;
        if (array.length > 3) {
            if (array[3].equals("*")) {
                number = OreDictionary.WILDCARD_VALUE;
            } else {
                number = Integer.parseInt(array[3]);
            }
        }

        return getItem(mod, item, meta, number);
    }

    public static ItemStack getModItem(String mod, String name, String wood, int number) {
        if (mod.equals("misc")) {
            if (name.equals("item")) {
                return getItem(PrimitiveLife.MODID, "item", Utils.getItemFromArray(Constants.miscItems, wood), number);
            }
        }
        return null;
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

    public static boolean hasSolidWalls(World world, int x, int y, int z) {
        for (ForgeDirection dir : Utils.sideDirections) {
            Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            if (block.getMaterial() == Material.air) {
                return false;
            }
        }
        return true;
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

    public static MovingObjectPosition getMovingObjectPositionFromPlayer(World worldIn, EntityPlayer playerIn,
        boolean useLiquids) {
        float f = 1.0F;
        float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * f;
        float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * f;
        double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * (double) f;
        double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * (double) f
            + (double) (worldIn.isRemote ? playerIn.getEyeHeight() - playerIn.getDefaultEyeHeight()
                : playerIn.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the
        // eye height clientside and player yOffset differences
        double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * (double) f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (playerIn instanceof EntityPlayerMP) {
            d3 = ((EntityPlayerMP) playerIn).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        return worldIn.func_147447_a/* rayTraceBlocks */(vec3, vec31, useLiquids, !useLiquids, false);
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

    public static boolean containsExactMatch(String[] array, String targetString) {
        for (String element : array) {
            if (element.equals(targetString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsOreDict(ItemStack b, String oreDict) {
        if (b == null) return false;
        if (b.getItem() == null) return false;
        String oreDictKey = getOreDictKey(b, oreDict);
        if (cacheOre.containsKey(oreDictKey)) {
            return cacheOre.get(oreDictKey);
        }

        for (int id1 : OreDictionary.getOreIDs(b)) {
            if (id1 == OreDictionary.getOreID(oreDict)) {
                cacheOre.put(oreDictKey, true);
                return true;
            }
        }
        return false;
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
            if (woodType[i].equals(wood)) {
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

    public static String getOreDictKey(ItemStack b, String oreDict) {
        return Utils.getItemKey(b) + ":" + oreDict;
    }
}
