package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.ItemBucketCeramic;
import net.pufferlab.primal.items.ItemBucketMeta;
import net.pufferlab.primal.utils.FluidUtils;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BucketHandler implements IEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void fillBucketEventHandler(FillBucketEvent event) {
        if (event.entityPlayer.getCurrentEquippedItem() != null) {
            ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();
            if (itemStack.getItem() != null) {
                if ((itemStack.getItem() == Registry.ceramic_bucket && itemStack.getItemDamage() == 0)
                    || (itemStack.getItem() == Items.bucket)) {
                    int x = event.target.blockX;
                    int y = event.target.blockY;
                    int z = event.target.blockZ;
                    Block block = event.world.getBlock(x, y, z);
                    int metaBlock = event.world.getBlockMetadata(x, y, z);
                    if (metaBlock == 0) {
                        int bucketMeta = ((ItemBucketMeta) Registry.bucket).getMetaFromFluidBlock(block);
                        if (bucketMeta > 2) {
                            if (itemStack.getItem() == Items.bucket) {
                                event.world.setBlockToAir(x, y, z);

                                event.result = new ItemStack(Registry.bucket, 1, bucketMeta);
                                event.setResult(Event.Result.ALLOW);
                            }
                            if (itemStack.getItem() == Registry.ceramic_bucket && itemStack.getItemDamage() == 0) {
                                event.world.setBlockToAir(x, y, z);

                                event.result = new ItemStack(Registry.ceramic_bucket, 1, bucketMeta);
                                event.setResult(Event.Result.ALLOW);
                            }
                        }
                        int bucketModdedMeta = ((ItemBucketMeta) Registry.ceramic_bucket_modded)
                            .getMetaFromFluidBlock(block);
                        if (bucketModdedMeta >= 0) {
                            if (itemStack.getItem() == Registry.ceramic_bucket && itemStack.getItemDamage() == 0) {
                                event.world.setBlockToAir(x, y, z);

                                event.result = new ItemStack(Registry.ceramic_bucket_modded, 1, bucketModdedMeta);
                                event.setResult(Event.Result.ALLOW);
                            }
                        }
                    }

                }
            }
        }
    }

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.entityPlayer.getCurrentEquippedItem() != null
            && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK
            && !event.entityPlayer.isSneaking()
            && event.useItem != Event.Result.DENY) {
            if (FluidUtils.isFluidContainer(event.entityPlayer.getCurrentEquippedItem())) {
                ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();
                TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
                if (te instanceof IFluidHandler tank) if (FluidUtils.isEmptyFluidContainer(itemStack)) {
                    FluidStack stack = tank.drain(ForgeDirection.getOrientation(event.face), 1000, false);
                    if (stack != null && stack.amount == 1000
                        && tank.canDrain(ForgeDirection.getOrientation(event.face), stack.getFluid())) {
                        tank.drain(ForgeDirection.getOrientation(event.face), 1000, true);
                        if (!event.world.isRemote) {
                            event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
                            event.entityPlayer.inventory
                                .addItemStackToInventory(FluidUtils.getStackFromFluid(itemStack, stack));
                            Primal.proxy.packet.sendInventoryUpdate(event.entityPlayer);
                            if (event.isCancelable()) event.setCanceled(true);
                        }
                    }
                } else {
                    ItemStack item = event.entityPlayer.getCurrentEquippedItem();
                    FluidStack fluid = FluidUtils.getFluidFromStack(item);
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
                                    event.entityPlayer.inventory
                                        .addItemStackToInventory(FluidUtils.getEmptyContainer(item));
                                }
                                Primal.proxy.packet.sendInventoryUpdate(event.entityPlayer);
                                if (event.isCancelable()) event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
