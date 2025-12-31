package net.pufferlab.primal.items;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;

import cpw.mods.fml.common.eventhandler.Event;

public class ItemBucketMeta extends ItemMeta {

    public ItemBucketMeta(String type) {
        super(Constants.fluids, type);
        this.maxStackSize = 1;
        this.setBlacklist(Constants.vanillaFluids);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        int meta = itemStackIn.getItemDamage();
        boolean flag = Registry.fluidsObjects[meta] == null;
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, player, flag);

        if (movingobjectposition == null) {
            return itemStackIn;
        } else {
            FillBucketEvent event = new FillBucketEvent(player, itemStackIn, worldIn, movingobjectposition);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return itemStackIn;
            }

            if (event.getResult() == Event.Result.ALLOW) {
                if (player.capabilities.isCreativeMode) {
                    return itemStackIn;
                }

                if (--itemStackIn.stackSize <= 0) {
                    return event.result;
                }

                if (!player.inventory.addItemStackToInventory(event.result)) {
                    player.dropPlayerItemWithRandomChoice(event.result, false);
                }

                return itemStackIn;
            }
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (worldIn.getTileEntity(i, j, k) instanceof IFluidHandler) {
                    return itemStackIn;
                }

                if (!worldIn.canMineBlock(player, i, j, k)) {
                    return itemStackIn;
                }

                if (flag) {
                    if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemStackIn)) {
                        return itemStackIn;
                    }

                    Material material = worldIn.getBlock(i, j, k)
                        .getMaterial();
                    int l = worldIn.getBlockMetadata(i, j, k);

                    if (material == Material.water && l == 0) {
                        worldIn.setBlockToAir(i, j, k);
                        return this.func_150910_a(itemStackIn, player, this, 1);
                    }

                    if (material == Material.lava && l == 0) {
                        worldIn.setBlockToAir(i, j, k);
                        return this.func_150910_a(itemStackIn, player, this, 2);
                    }
                } else {
                    if (Registry.fluidsObjects[meta] == null) {
                        return new ItemStack(this, 0, 1);
                    }

                    if (movingobjectposition.sideHit == 0) {
                        --j;
                    }

                    if (movingobjectposition.sideHit == 1) {
                        ++j;
                    }

                    if (movingobjectposition.sideHit == 2) {
                        --k;
                    }

                    if (movingobjectposition.sideHit == 3) {
                        ++k;
                    }

                    if (movingobjectposition.sideHit == 4) {
                        --i;
                    }

                    if (movingobjectposition.sideHit == 5) {
                        ++i;
                    }

                    if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemStackIn)) {
                        return itemStackIn;
                    }

                    if (this.tryPlaceContainedLiquid(worldIn, i, j, k, meta) && !player.capabilities.isCreativeMode) {
                        if (isBreakable(itemStackIn)) {
                            itemStackIn.stackSize = 0;
                            return itemStackIn;
                        }
                        return getEmptyBucket();
                    }
                }
            }

            return itemStackIn;
        }
    }

    public ItemStack getEmptyBucket() {
        return new ItemStack(Items.bucket, 1, 0);
    }

    private ItemStack func_150910_a(ItemStack itemStackIn, EntityPlayer player, Item item, int meta) {
        if (player.capabilities.isCreativeMode) {
            return itemStackIn;
        } else if (--itemStackIn.stackSize <= 0) {
            return new ItemStack(item, 1, meta);
        } else {
            if (itemStackIn.getItem() == this) {
                ItemStack newIS = itemStackIn.copy();
                newIS.setItemDamage(meta);
                return newIS;
            }

            if (!player.inventory.addItemStackToInventory(new ItemStack(item, 1, meta))) {
                player.dropPlayerItemWithRandomChoice(new ItemStack(item, 1, meta), false);
            }

            return itemStackIn;
        }
    }

    /**
     * Attempts to place the liquid contained inside the bucket.
     */
    public boolean tryPlaceContainedLiquid(World world, int x, int y, int z, int meta) {
        if (Registry.fluidsObjects[meta] == null) {
            return false;
        } else {
            Material material = world.getBlock(x, y, z)
                .getMaterial();
            boolean flag = !material.isSolid();

            if (!world.isAirBlock(x, y, z) && !flag) {
                return false;
            } else {
                if (world.provider.isHellWorld && Registry.fluidsObjects[meta] == FluidRegistry.WATER) {
                    world.playSoundEffect(
                        (double) ((float) x + 0.5F),
                        (double) ((float) y + 0.5F),
                        (double) ((float) z + 0.5F),
                        "random.fizz",
                        0.5F,
                        2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                    for (int l = 0; l < 8; ++l) {
                        world.spawnParticle(
                            "largesmoke",
                            (double) x + Math.random(),
                            (double) y + Math.random(),
                            (double) z + Math.random(),
                            0.0D,
                            0.0D,
                            0.0D);
                    }
                } else {
                    if (!world.isRemote && flag && !material.isLiquid()) {
                        world.func_147480_a(x, y, z, true);
                    }

                    world.setBlock(x, y, z, Registry.fluidsBlocks[meta], 0, 3);
                    return true;
                }

                return false;
            }
        }
    }

    public boolean isBreakable(ItemStack itemStack) {
        return Constants.fluidsBreak[itemStack.getItemDamage()];
    }
}
