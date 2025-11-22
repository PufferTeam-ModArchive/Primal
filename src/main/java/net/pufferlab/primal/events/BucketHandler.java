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
import net.pufferlab.primal.items.ItemBucketCeramic;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BucketHandler {

    public void swingItemPacket(EntityPlayer player) {
        if (!player.worldObj.isRemote) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            Primal.networkWrapper.sendTo(new PacketSwingArm(player), playerMP);
        }
    }

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (!event.world.isRemote && event.entityPlayer.getCurrentEquippedItem() != null) {
            if (event.entityPlayer.getCurrentEquippedItem()
                .getItem() instanceof ItemBucketCeramic bucket) {
                TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
                if (te instanceof IFluidHandler tank) if (event.entityPlayer.getCurrentEquippedItem()
                    .getItemDamage() == 0) {
                        FluidStack stack = tank.drain(ForgeDirection.getOrientation(event.face), 1000, false);
                        if (stack != null && stack.amount == 1000) {
                            int meta = bucket.getFluidMeta(stack);
                            ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();
                            itemStack.setItemDamage(meta);
                            tank.drain(ForgeDirection.getOrientation(event.face), 1000, true);
                            event.entityPlayer.setCurrentItemOrArmor(0, itemStack);
                            swingItemPacket(event.entityPlayer);
                            if (event.isCancelable()) event.setCanceled(true);
                        }
                    } else {
                        ItemStack item = event.entityPlayer.getCurrentEquippedItem();
                        FluidStack fluid = bucket.getFluid(item);
                        if (fluid != null) {
                            if (tank.fill(ForgeDirection.getOrientation(event.face), fluid, false) == 1000) {
                                if (bucket.isBreakable(item)) {
                                    item.stackSize = 0;
                                } else {
                                    item.setItemDamage(0);
                                }
                                tank.fill(ForgeDirection.getOrientation(event.face), fluid, true);
                                event.entityPlayer.setCurrentItemOrArmor(0, item);
                                swingItemPacket(event.entityPlayer);
                                if (event.isCancelable()) event.setCanceled(true);
                            }
                        }
                    }
            }

        }
    }
}
