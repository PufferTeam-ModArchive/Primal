package net.pufferlab.primal.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.ItemBucketCeramic;
import net.pufferlab.primal.tileentities.TileEntityFluidInventory;

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
            && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK
            && !event.entityPlayer.isSneaking()) {
            if (event.entityPlayer.getCurrentEquippedItem()
                .getItem() instanceof ItemBucketCeramic bucket) {
                TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
                if (te instanceof IFluidHandler tank) if (event.entityPlayer.getCurrentEquippedItem()
                    .getItemDamage() == 0) {
                        FluidStack stack = tank.drain(ForgeDirection.getOrientation(event.face), 1000, false);
                        if (stack != null && stack.amount == 1000) {
                            tank.drain(ForgeDirection.getOrientation(event.face), 1000, true);
                            if (!event.world.isRemote) {
                                int meta = bucket.getFluidMeta(stack);
                                ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();
                                itemStack.setItemDamage(meta);
                                event.entityPlayer.setCurrentItemOrArmor(0, itemStack);
                                updatePacket(event.entityPlayer);
                                if (event.isCancelable()) event.setCanceled(true);
                            }
                        }
                    } else {
                        ItemStack item = event.entityPlayer.getCurrentEquippedItem();
                        FluidStack fluid = bucket.getFluid(item);
                        if (fluid != null) {
                            if (tank.fill(ForgeDirection.getOrientation(event.face), fluid, false) == 1000) {
                                tank.fill(ForgeDirection.getOrientation(event.face), fluid, true);
                                if (!event.world.isRemote) {
                                    if (bucket.isBreakable(item)) {
                                        item.stackSize = 0;
                                    } else {
                                        item.setItemDamage(0);
                                    }
                                    event.entityPlayer.setCurrentItemOrArmor(0, item);
                                    updatePacket(event.entityPlayer);
                                    if (event.isCancelable()) event.setCanceled(true);
                                }
                            }
                        }
                    }
            } else {
                if (Utils.isFluidContainer(event.entityPlayer.getCurrentEquippedItem())) {
                    ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();
                    TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
                    if (te instanceof TileEntityFluidInventory tank) if (Utils.isEmptyFluidContainer(itemStack)) {
                        FluidStack stack = tank.drain(ForgeDirection.getOrientation(event.face), 1000, false);
                        if (stack != null && stack.amount == 1000) {
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
                        if (fluid != null) {
                            if (tank.fill(ForgeDirection.getOrientation(event.face), fluid, false) == 1000) {
                                tank.fill(ForgeDirection.getOrientation(event.face), fluid, true);
                                if (!event.world.isRemote) {
                                    event.entityPlayer.inventory
                                        .decrStackSize(event.entityPlayer.inventory.currentItem, 1);
                                    event.entityPlayer.inventory.addItemStackToInventory(Utils.getEmptyContainer(item));
                                    updatePacket(event.entityPlayer);
                                    if (event.isCancelable()) event.setCanceled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
