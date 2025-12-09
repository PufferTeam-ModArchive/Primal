package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.ItemBucketCeramic;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BucketHandler {

    public void updatePacket(EntityPlayer player) {
        if (!player.worldObj.isRemote) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            Primal.networkWrapper.sendTo(new PacketSwingArm(player), playerMP);
            ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
        }
    }

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.entityPlayer.getCurrentEquippedItem() != null
            && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();
            if (itemStack.getItem() != null) {
                if ((itemStack.getItem() == Registry.ceramic_bucket && itemStack.getItemDamage() == 0)
                    || (itemStack.getItem() == Items.bucket)) {
                    int x = Utils.getBlockX(event.face, event.x);
                    int y = Utils.getBlockY(event.face, event.y);
                    int z = Utils.getBlockZ(event.face, event.z);
                    Block block = event.world.getBlock(x, y, z);
                    int metaBlock = event.world.getBlockMetadata(x, y, z);
                    if (metaBlock == 0) {
                        if (Utils.containsExactMatch(Registry.fluidsBlocks, block)) {
                            int meta = Utils.getItemFromArray(Registry.fluidsBlocks, block);
                            if (meta > 2) {
                                if (itemStack.getItem() == Items.bucket) {
                                    event.world.setBlockToAir(x, y, z);

                                    event.entityPlayer.inventory.setInventorySlotContents(
                                        event.entityPlayer.inventory.currentItem,
                                        func_150910_a(itemStack, event.entityPlayer, Registry.bucket, meta));
                                    if (!event.world.isRemote) {
                                        ((EntityPlayerMP) event.entityPlayer)
                                            .sendContainerToPlayer(event.entityPlayer.inventoryContainer);
                                    }
                                }
                                if (itemStack.getItem() == Registry.ceramic_bucket && itemStack.getItemDamage() == 0) {
                                    event.world.setBlockToAir(x, y, z);

                                    event.entityPlayer.inventory.setInventorySlotContents(
                                        event.entityPlayer.inventory.currentItem,
                                        func_150910_a(itemStack, event.entityPlayer, Registry.ceramic_bucket, meta));
                                    if (!event.world.isRemote) {
                                        ((EntityPlayerMP) event.entityPlayer)
                                            .sendContainerToPlayer(event.entityPlayer.inventoryContainer);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        if (event.entityPlayer.getCurrentEquippedItem() != null
            && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK
            && !event.entityPlayer.isSneaking()
            && event.useItem != Event.Result.DENY) {
            if (Utils.isFluidContainer(event.entityPlayer.getCurrentEquippedItem())) {
                ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();
                TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
                if (te instanceof IFluidHandler tank) if (Utils.isEmptyFluidContainer(itemStack)) {
                    FluidStack stack = tank.drain(ForgeDirection.getOrientation(event.face), 1000, false);
                    if (stack != null && stack.amount == 1000
                        && tank.canDrain(ForgeDirection.getOrientation(event.face), stack.getFluid())) {
                        tank.drain(ForgeDirection.getOrientation(event.face), 1000, true);
                        if (!event.world.isRemote) {
                            event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
                            event.entityPlayer.inventory
                                .addItemStackToInventory(Utils.getStackFromFluid(itemStack, stack));
                            updatePacket(event.entityPlayer);
                            if (event.isCancelable()) event.setCanceled(true);
                        }
                    }
                } else {
                    ItemStack item = event.entityPlayer.getCurrentEquippedItem();
                    FluidStack fluid = Utils.getFluidFromStack(item);
                    if (fluid != null && tank.canFill(ForgeDirection.getOrientation(event.face), fluid.getFluid())) {
                        if (tank.fill(ForgeDirection.getOrientation(event.face), fluid, false) == 1000) {
                            tank.fill(ForgeDirection.getOrientation(event.face), fluid, true);
                            if (!event.world.isRemote) {
                                event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
                                boolean addEmpty = true;
                                if (itemStack.getItem() instanceof ItemBucketCeramic itemo) {
                                    if (itemo.isBreakable(itemStack)) {
                                        addEmpty = false;
                                    }
                                }
                                if (addEmpty) {
                                    event.entityPlayer.inventory.addItemStackToInventory(Utils.getEmptyContainer(item));
                                }
                                updatePacket(event.entityPlayer);
                                if (event.isCancelable()) event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private ItemStack func_150910_a(ItemStack itemStackIn, EntityPlayer player, Item item, int meta) {
        if (player.capabilities.isCreativeMode) {
            return itemStackIn;
        } else if (--itemStackIn.stackSize <= 0) {
            return new ItemStack(item, 1, meta);
        } else {
            if (!player.inventory.addItemStackToInventory(new ItemStack(item, 1, meta))) {
                player.dropPlayerItemWithRandomChoice(new ItemStack(item, 1, meta), false);
            }

            return itemStackIn;
        }
    }
}
